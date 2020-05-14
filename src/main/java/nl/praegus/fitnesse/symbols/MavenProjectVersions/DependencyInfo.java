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
    private final String artifactId;
    private final String versionInPom;
    private final String versionOnMvnCentral;
    // Plugin
    public DependencyInfo(String pluginGroup, String pluginArtifact) {
        this.artifactId = pluginArtifact;
        this.versionInPom = getClass().getPackage().getImplementationVersion();
        this.versionOnMvnCentral = getLatestVersion(pluginGroup, DocumentBuilderFactory.newInstance());
    }
    // Other Dependencies
    public DependencyInfo(String groupId, String artifactId, String versionInPom, Model mavenModel) {
        this.artifactId = artifactId;
        this.versionInPom = convertMavenVariableToVersion(versionInPom, mavenModel);
        this.versionOnMvnCentral = getLatestVersion(groupId, DocumentBuilderFactory.newInstance());
    }
    // with factory for test
    public DependencyInfo(String groupId, String artifactId, String versionInPom, Model mavenModel, DocumentBuilderFactory factory) {
        this.artifactId = artifactId;
        this.versionInPom = convertMavenVariableToVersion(versionInPom, mavenModel);
        this.versionOnMvnCentral = getLatestVersion(groupId, factory);
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
    public List<String> getReleaseNoteUrls() {
        List<String> ReleaseNotesUrls = new ArrayList<>();
        switch (artifactId) {
            case "fitnesse":
                ReleaseNotesUrls.add(ReleaseNotesUrl.fitnesseUrl);
                return ReleaseNotesUrls;
            case "hsac-fitnesse-fixtures":
                ReleaseNotesUrls.add(ReleaseNotesUrl.hsacUrl);
                return ReleaseNotesUrls;
            case "toolchain-fitnesse-plugin":
                ReleaseNotesUrls.add(ReleaseNotesUrl.pluginUrl);
                ReleaseNotesUrls.add(ReleaseNotesUrl.bootstrapUrl);
                return ReleaseNotesUrls;
            default:
                return ReleaseNotesUrls;
        }
    }
    private String getLatestVersion(String groupId, DocumentBuilderFactory factory) {
        String latestVersionUrl = "https://repo.maven.apache.org/maven2/%s/%s/maven-metadata.xml";
        String url = String.format(latestVersionUrl, groupId.replace(".", "/"), artifactId);
        try {
            Document doc = factory.newDocumentBuilder().parse(new URL(url).openStream());
            XPathExpression expr = XPathFactory.newInstance().newXPath().compile(latestversionXpath);
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