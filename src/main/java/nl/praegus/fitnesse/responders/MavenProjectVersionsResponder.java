package nl.praegus.fitnesse.responders;

import fitnesse.FitNesseContext;
import fitnesse.authentication.SecureOperation;
import fitnesse.authentication.SecureReadOperation;
import fitnesse.authentication.SecureResponder;
import fitnesse.http.Request;
import fitnesse.http.Response;
import fitnesse.http.SimpleResponse;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MavenProjectVersionsResponder implements SecureResponder {

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
    private SimpleResponse response = new SimpleResponse();
    private int status = 200;

    @Override
    public SecureOperation getSecureOperation() {
        return new SecureReadOperation();
    }

    @Override
    public Response makeResponse(FitNesseContext context, Request request) throws Exception {
        getDependencyInformation();
        getPluginVersionInformation();

        response.setMaxAge(0);
        response.setStatus(status);
        response.setContentType("application/json");
        response.setContent(dependenciesInfo.toString(3));

        return response;
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
            Matcher matcher = pattern.matcher(version);
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