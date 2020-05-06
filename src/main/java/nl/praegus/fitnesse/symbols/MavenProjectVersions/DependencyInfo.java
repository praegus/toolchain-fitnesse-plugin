package nl.praegus.fitnesse.symbols.MavenProjectVersions;

import org.apache.maven.model.Model;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DependencyInfo {
    private final static String latestversionXpath = "/metadata/versioning/latest";
    private String artifactId;
    private String versionInPom;
    private String versionOnMvnCentral;
    private List<String> releaseNotesUrl;

    public String getArtifactId() { return this.artifactId; }
    public String getVersionInPom() { return this.versionInPom; }
    public String getVersionOnMvnCentral() { return this.versionOnMvnCentral; }
    public List<String> getReleaseNotesUrl() { return releaseNotesUrl; }

    // Plugin
    public DependencyInfo(String pluginGroup, String pluginArtifact) {
        this.artifactId = pluginArtifact;
        this.versionInPom = getClass().getPackage().getImplementationVersion();
        this.versionOnMvnCentral = getLatestVersion(pluginGroup, pluginArtifact);
        this.releaseNotesUrl = getReleaseNotesUrl(pluginArtifact);
    }

    // Other Dependencies
    public DependencyInfo(String group, String artifact, String versionInPom, Model model) {
        this.artifactId = artifact;
        this.versionInPom = convertMavenVariableToVersion(versionInPom, model);
        this.versionOnMvnCentral = getLatestVersion(group, artifact);
        this.releaseNotesUrl = getReleaseNotesUrl(artifact);
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

    private String convertMavenVariableToVersion(String versionOrMavenVariable, Model model) {
        // Check if version is variable
        if(versionOrMavenVariable.startsWith("$")) {
            String property = null;
            // Create pattern of finding ${toolchain.fixtures.version}
            Pattern pattern = Pattern.compile("\\$\\{([^{}]+)}");
            java.util.regex.Matcher matcher = pattern.matcher(versionOrMavenVariable);
            if (matcher.find()) {
                // Set property as found version number
                property = matcher.group(1);
            }
            // Return property of version number
            return model.getProperties().getProperty(property);
        } else {
            return versionOrMavenVariable;
        }
    }

    private List<String> getReleaseNotesUrl(String pluginArtifact) {
        List<String> ReleaseNotesUrls = new ArrayList<>();
        switch (pluginArtifact) {
            case "fitnesse":
                ReleaseNotesUrls.add("Fitnesse," + ReleaseNotesUrl.fitnesseUrl);
                return ReleaseNotesUrls;
            case "hsac-fitnesse-fixtures":
                ReleaseNotesUrls.add("Hsac," + ReleaseNotesUrl.hsacUrl);
                return ReleaseNotesUrls;
            case "toolchain-fitnesse-plugin":
                ReleaseNotesUrls.add("Plugin," + ReleaseNotesUrl.pluginUrl);
                ReleaseNotesUrls.add("Bootstrap+," + ReleaseNotesUrl.bootstrapUrl);
                return ReleaseNotesUrls;
            default:
                return ReleaseNotesUrls;
        }
    }
}