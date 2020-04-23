package nl.praegus.fitnesse.symbols.MavenProjectVersions;

import fitnesse.wikitext.parser.*;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

public class MavenProjectVersionsSymbol extends SymbolType implements Rule, Translation {

    private final static Set<String> IGNORE_DEPENDENCIES = new HashSet<>(Arrays.asList(
            "jaxb-core",
            "jaxb-impl",
            "jaxb-api",
            "activation",
            "junit"));
    private final static String latestversionXpath = "/metadata/versioning/latest";

    public MavenProjectVersionsSymbol() {
        super("VersionCheckerSymbol");
        wikiMatcher(new Matcher().string("!VersionChecker"));
        wikiRule(this);
        htmlTranslation(this);
    }

    @Override
    public Maybe<Symbol> parse(Symbol current, Parser parser) {
        return new Maybe<>(current);
    }

    @Override
    public String toTarget(Translator translator, Symbol symbol) {
        HtmlWriter writer = new HtmlWriter();

        // Generate the html
        writer.startTag("div");
            writer.putAttribute("id", "mavenVersions");

            writer.startTag("h3");
                writer.putText("Maven Version Checker");
            writer.endTag();

            writer.putText(GenerateTable());

        writer.endTag();
        return writer.toHtml();
    }

    private String GenerateTable() {
        HtmlWriter writer = new HtmlWriter();
        // Get all the version information
        JSONArray versionInformation = getAllDependencyVersionInformation();
        versionInformation.put(getPluginVersionInformation());

        // Start of the tabele
        writer.startTag("table");
            writer.putAttribute("id", "versioncheck");

            // Table title
            writer.startTag("tr");
                writer.startTag("th");
                    writer.putAttribute("colspan", "4");
                    writer.putText("Versioncheck");
                writer.endTag();
            writer.endTag();

            // Table headers
            List<String> tableHeaders = Arrays.asList("Name", "Current version", "Newest Version", "Status");
            writer.startTag("tr");
                for (String tableHeader : tableHeaders) {
                    writer.putText(GenerateTableRowTd(tableHeader, null));
                }
            writer.endTag();

            // Table row
            for (int i = 0; i < versionInformation.length(); ++i) {
                JSONObject object = versionInformation.getJSONObject(i);
                writer.putText(GenerateTableRows(object));
            }

        writer.endTag();

        return writer.toHtml();
    }

    private String GenerateTableRows(JSONObject object) {
        HtmlWriter writer = new HtmlWriter();
        String current = object.has("version") ? object.getString("version") : object.getString("currentVersion");
        String status = getStatus(current, object.getString("latest"));

        writer.startTag("tr");
            writer.putText(GenerateTableRowTd(object.getString("artifactid"), null));
            writer.putText(GenerateTableRowTd(current, null));
            writer.putText(GenerateTableRowTd(object.getString("latest"), null));
            writer.putText(GenerateTableRowTd(status, status));
        writer.endTag();

        return writer.toHtml();
    }

    private String GenerateTableRowTd(String text, String className) {
        HtmlWriter writer = new HtmlWriter();
        writer.startTag("td");
            if (className != null) {
                writer.putAttribute("class", className);
            }
            writer.putText(text);
        writer.endTag();

        return writer.toHtml();
    }

    public String getStatus(String current, String latest) { return getStatusHelper(current, latest);}

    private String getStatusHelper(String current, String latest) {
        VersionNumber currentVersion = new VersionNumber(current);
        VersionNumber latestVersion = new VersionNumber(latest);
        return currentVersion.compareTo(latestVersion).toString().replace("_", "-");
    }

    private JSONObject getPluginVersionInformation () {
        String pluginGroup = "nl.praegus";
        String pluginArtifact = "toolchain-fitnesse-plugin";
        JSONObject pluginInfo = new JSONObject();

        pluginInfo.put("groupid", pluginGroup);
        pluginInfo.put("artifactid", pluginArtifact);
        pluginInfo.put("version", getClass().getPackage().getImplementationVersion());
        pluginInfo.put("latest", getLatestVersion(pluginGroup, pluginArtifact));
        return pluginInfo;
    }

    private JSONArray getAllDependencyVersionInformation() {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        try {
            Model model = reader.read(new FileReader(System.getProperty("user.dir") + "/../pom.xml"));
            List<Dependency> dependencies = model.getDependencies();
            JSONArray dependancyArray = new JSONArray();
            for (Dependency dep : dependencies) {
                String group = dep.getGroupId();
                String artifact = dep.getArtifactId();
                String version = dep.getVersion();
                if(!IGNORE_DEPENDENCIES.contains(artifact)) {
                    JSONObject dependencyInfo = new JSONObject();
                    dependencyInfo.put("groupid", group);
                    dependencyInfo.put("artifactid", artifact);
                    dependencyInfo.put("currentVersion", translateVersion(version, model));
                    dependencyInfo.put("latest", getLatestVersion(group, artifact));
                    dependancyArray.put(dependencyInfo);
                }
            }
            return dependancyArray;
        } catch (IOException | XmlPullParserException e) {
            JSONObject error = new JSONObject();
            error.put("error", "No valid pom.xml was found in your project's root folder (are you using maven?)");
            error.put("message", e.getMessage());
            error.put("stacktrace", e.getStackTrace());
            return new JSONArray(error);
        }
    }

    private String translateVersion(String version, Model model) {
        if(version.startsWith("$")) {
            String property = "";
            Pattern pattern = Pattern.compile("\\$\\{([^{}]+)}");
            java.util.regex.Matcher matcher = pattern.matcher(version);
            if (matcher.find()) {
                property = matcher.group(1);
            }
            return model.getProperties().getProperty(property);
        } else {
            return version;
        }
    }

    private String getLatestVersion(String groupId, String artifactId) {
        groupId = groupId.replace(".", "/");
        String url = String.format("https://repo.maven.apache.org/maven2/%s/%s/maven-metadata.xml", groupId, artifactId);
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(url).openStream());
            XPathExpression expr = XPathFactory.newInstance().newXPath().compile(latestversionXpath);
            return expr.evaluate(doc);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "N/A";
        }
    }
}
