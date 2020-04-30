package nl.praegus.fitnesse.symbols.MavenProjectVersions;

import org.apache.maven.model.Model;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.net.URL;
import java.util.regex.Pattern;

public class DependencyInfo {
    private final static String latestversionXpath = "/metadata/versioning/latest";
    private String artifactid;
    private String version;
    private String latest;

    // Plugin
    public DependencyInfo(String pluginGroup, String pluginArtifact) {
        this.artifactid = pluginArtifact;
        this.version = getClass().getPackage().getImplementationVersion();
        this.latest = getLatestVersion(pluginGroup, pluginArtifact);
    }

    // Other Dependencies
    public DependencyInfo(String group, String artifact, String version, Model model) {
        this.artifactid = artifact;
        this.version = getCurrentVersion(version, model);
        this.latest = getLatestVersion(group, artifact);
    }

    public String getArtifactid() { return this.artifactid; }
    public String getVersion() { return this.version; }
    public String getLatest() { return this.latest; }

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

    private String getCurrentVersion(String version, Model model) {
        // Check if version is variable
        if(version.startsWith("$")) {
            String property = null;
            // Create pattern of finding ${toolchain.fixtures.version}
            Pattern pattern = Pattern.compile("\\$\\{([^{}]+)}");
            java.util.regex.Matcher matcher = pattern.matcher(version);
            if (matcher.find()) {
                // Set property as found version number
                property = matcher.group(1);
            }
            // Return property of version number
            return model.getProperties().getProperty(property);
        } else {
            return version;
        }
    }
}
