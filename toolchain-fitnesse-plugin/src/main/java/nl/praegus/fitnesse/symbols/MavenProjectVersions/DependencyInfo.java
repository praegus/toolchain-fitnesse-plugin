package nl.praegus.fitnesse.symbols.MavenProjectVersions;

import org.apache.maven.model.Model;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class DependencyInfo {

    private static final String HOMEPAGE_URL_XPATH = "/project/url";
    private static final String LATEST_VERSION_XPATH = "/metadata/versioning/latest";
    private final String groupId;
    private final String artifactId;
    private final String versionInPom;
    private final String versionOnMvnCentral;

    // Plugin
    public DependencyInfo(String pluginGroup, String pluginArtifact) {
        this.groupId = pluginGroup;
        this.artifactId = pluginArtifact;
        this.versionInPom = getClass().getPackage().getImplementationVersion();
        this.versionOnMvnCentral = getLatestVersion(DocumentBuilderFactory.newInstance());
    }

    // Other Dependencies
    public DependencyInfo(String groupId, String artifactId, String versionInPom, Model mavenModel) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.versionInPom = convertMavenVariableToVersion(versionInPom, mavenModel);
        this.versionOnMvnCentral = getLatestVersion(DocumentBuilderFactory.newInstance());
    }

    // with factory for test
    public DependencyInfo(String groupId, String artifactId, String versionInPom, Model mavenModel, DocumentBuilderFactory factory) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.versionInPom = convertMavenVariableToVersion(versionInPom, mavenModel);
        this.versionOnMvnCentral = getLatestVersion(factory);
    }

    public String getArtifactId() {
        return this.artifactId;
    }

    public String getVersionInPom() {
        return this.versionInPom;
    }

    public String getVersionOnMvnCentral() {
        return this.versionOnMvnCentral;
    }

    public List<Map<String, String>> getReleaseNoteUrls() {
        List<Map<String, String>> releaseNotesData = new ArrayList<>();

        switch (artifactId) {
            case "fitnesse":
                releaseNotesData.add(addReleaseNoteInfo(artifactId, "http://fitnesse.org/FitNesse.ReleaseNotes"));
                break;
            case "hsac-fitnesse-fixtures":
                releaseNotesData.add(addReleaseNoteInfo(artifactId, "https://github.com/fhoeben/hsac-fitnesse-fixtures/releases"));
                break;
            case "toolchain-fitnesse-plugin":
                releaseNotesData.add(addReleaseNoteInfo("Plugin", "https://github.com/praegus/toolchain-fitnesse-plugin/releases"));
                releaseNotesData.add(addReleaseNoteInfo("Bootstrap<sup>+</sup>", "https://github.com/praegus/fitnesse-bootstrap-plus-theme/releases"));
                break;
            default:
                releaseNotesData.add(addReleaseNoteInfo("Homepage", getHomePageFromPom()));
                break;
        }
        return releaseNotesData;
    }

    private Map<String, String> addReleaseNoteInfo(String dependency, String url) {
        Map<String, String> releaseNoteInfo = new HashMap<>();
        releaseNoteInfo.put("label", dependency);
        releaseNoteInfo.put("url", url);
        return releaseNoteInfo;
    }


    private String getHomePageFromPom() {
        String url = String.format("https://repo.maven.apache.org/maven2/%s/%s/%s/%s-%s.pom",
                groupId.replace(".", "/"),
                artifactId,
                versionOnMvnCentral,
                artifactId,
                versionOnMvnCentral
        );
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(url).openStream());
            XPathExpression expr = XPathFactory.newInstance().newXPath().compile(HOMEPAGE_URL_XPATH);
            return expr.evaluate(doc);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private String getLatestVersion(DocumentBuilderFactory factory) {
        String latestVersionUrl = "https://repo.maven.apache.org/maven2/%s/%s/maven-metadata.xml";
        String url = String.format(latestVersionUrl, groupId.replace(".", "/"), artifactId);
        try {
            Document doc = factory.newDocumentBuilder().parse(new URL(url).openStream());
            XPathExpression expr = XPathFactory.newInstance().newXPath().compile(LATEST_VERSION_XPATH);
            return expr.evaluate(doc);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "N/A";
        }
    }

    private String convertMavenVariableToVersion(String versionOrMavenVariable, Model mavenModel) {
        // Check if version is variable
        if (versionOrMavenVariable.startsWith("$")) {
            String property = null;
            // Create pattern of finding ${toolchain.fixtures.version}
            Pattern pattern = Pattern.compile("\\$\\{([^{}]+)}");
            java.util.regex.Matcher matcher = pattern.matcher(versionOrMavenVariable);
            if (matcher.find()) {
                // Set property as found version number
                property = matcher.group(1);
            }
            // Return property of version number
            return mavenModel.getProperties().getProperty(property);
        } else {
            return versionOrMavenVariable;
        }
    }
}