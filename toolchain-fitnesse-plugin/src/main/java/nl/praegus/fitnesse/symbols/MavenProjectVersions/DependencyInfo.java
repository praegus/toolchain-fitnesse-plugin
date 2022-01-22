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
    private final static String LATEST_VERSION_XPATH = "/metadata/versioning/latest";
    private final static String HOMEPAGE_URL_XPATH = "/project/url";
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

    public List<String> getReleaseNoteUrls() {
        List<String> releaseNotesUrls = new ArrayList<>();
        switch (artifactId) {
            case "fitnesse":
                releaseNotesUrls.add(ReleaseNotesUrl.fitnesseUrl);
                return releaseNotesUrls;
            case "hsac-fitnesse-fixtures":
                releaseNotesUrls.add(ReleaseNotesUrl.hsacUrl);
                return releaseNotesUrls;
            case "toolchain-fitnesse-plugin":
                releaseNotesUrls.add(ReleaseNotesUrl.pluginUrl);
                releaseNotesUrls.add(ReleaseNotesUrl.bootstrapUrl);
                return releaseNotesUrls;
            default:
                String homepageUrl = getHomePageFromPom();
                if (homepageUrl != null && !homepageUrl.isEmpty()) {
                    releaseNotesUrls.add("Homepage," + homepageUrl);

                }

                if(homepageUrl == null || homepageUrl.isEmpty()){
                    releaseNotesUrls.add("Homepage not found, ");
                }

                return releaseNotesUrls;
        }
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