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

    private final static Set<String> IGNORE_DEPENDENCIES;
    private final static String latestversionXpath = "/metadata/versioning/latest";

    static {
        IGNORE_DEPENDENCIES = new HashSet<>(Arrays.asList(
                "jaxb-core",
                "jaxb-impl",
                "jaxb-api",
                "activation",
                "junit"));
    }

    private JSONArray dependenciesInfo = new JSONArray();
    private int status = 200;

    public MavenProjectVersionsSymbol() {
        super("TestHistorySymbol");
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
        dependenciesInfo = new JSONArray();
        getDependencyInformation();
        getPluginVersionInformation();
        HtmlWriter writer = new HtmlWriter();
        writer.startTag("div");writer.putAttribute("id", "mavenVersions");
        writer.startTag("h3");writer.putText("Maven Version Checker");writer.endTag();
        writer.startTag("table");writer.putAttribute("id", "versioncheck");
        writer.startTag("tr");
            writer.startTag("th");
            writer.putAttribute("colspan", "4");
            writer.putText("Versioncheck");
            writer.endTag();
        writer.endTag();

        ArrayList<String> tableHeaders = new ArrayList<>();
        tableHeaders.add("Name");
        tableHeaders.add("Current Version");
        tableHeaders.add("Newest Version");
        tableHeaders.add("Status");

        writer.startTag("tr");
            for (String tableHeader : tableHeaders) {
                writer.startTag("td");
                writer.putText(tableHeader);
                writer.endTag();
            }
        writer.endTag();

        for (int i = 0; i < dependenciesInfo.length(); ++i) {
            JSONObject object = dependenciesInfo.getJSONObject(i);
            String current = object.has("version") ? object.getString("version") : object.getString("currentVersion");
            String latest = object.getString("latest");
            String status = getStatus(current, latest);

            writer.startTag("tr");
                writer.startTag("td");writer.putText(object.getString("artifactid"));writer.endTag();
                writer.startTag("td");writer.putText(current);writer.endTag();
                writer.startTag("td");writer.putText(object.getString("latest"));writer.endTag();
                writer.startTag("td");writer.putAttribute("class", status);writer.putText(status);writer.endTag();
            writer.endTag();
        }

        writer.endTag();
        writer.endTag();
        return writer.toHtml();
    }

    public String getStatus(String current, String latest) { return getStatusHelper(current, latest);}

    private String getStatusHelper(String current, String latest) {
        Version currentVersion = new Version(current);
        Version latestVersion = new Version(latest);

        if (currentVersion.compareTo(latestVersion) < 0) {
            return "Outdated";
        }
        else if (currentVersion.compareTo(latestVersion) > 0) {
            return "Ahead";
        }
        else if (currentVersion.compareTo(latestVersion) == 0) {
            return "Up-to-date";
        }
        return null;
    }

    private void getPluginVersionInformation () {
        String pluginGroup = "nl.praegus";
        String pluginArtifact = "toolchain-fitnesse-plugin";
        JSONObject pluginInfo = new JSONObject();

        pluginInfo.put("groupid", pluginGroup);
        pluginInfo.put("artifactid", pluginArtifact);
        pluginInfo.put("version", getClass().getPackage().getImplementationVersion());
        pluginInfo.put("latest", getLatestVersion(pluginGroup, pluginArtifact));
        dependenciesInfo.put(pluginInfo);
    }

    private void getDependencyInformation () {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        try {
            Model model = reader.read(new FileReader(System.getProperty("user.dir") + "/../pom.xml"));
            List<Dependency> dependencies = model.getDependencies();
            for (Dependency dep : dependencies) {
                if (status == 200) {
                    String group = dep.getGroupId();
                    String artifact = dep.getArtifactId();
                    String version = dep.getVersion();
                    if(!IGNORE_DEPENDENCIES.contains(artifact)) {
                        JSONObject dependencyInfo = new JSONObject();
                        dependencyInfo.put("groupid", group);
                        dependencyInfo.put("artifactid", artifact);
                        dependencyInfo.put("currentVersion", translateVersion(version, model));
                        dependencyInfo.put("latest", getLatestVersion(group, artifact));
                        dependenciesInfo.put(dependencyInfo);
                    }
                }
            }

        } catch (IOException | XmlPullParserException e) {
            status = 500;
            JSONObject error = new JSONObject();
            error.put("error", "No valid pom.xml was found in your project's root folder (are you using maven?)");
            error.put("message", e.getMessage());
            error.put("stacktrace", e.getStackTrace());
            dependenciesInfo.put(error);
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
