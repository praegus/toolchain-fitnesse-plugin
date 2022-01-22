package nl.praegus.fitnesse.symbols.MavenProjectVersions;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DependencyInfoTest {
    @Test
    public void check_Plugin_Dependency_Values() throws Exception {
        DocumentBuilderFactory factory = mock(DocumentBuilderFactory.class);
        DocumentBuilder documentBuilder = mock(DocumentBuilder.class);
        when(factory.newDocumentBuilder()).thenReturn(documentBuilder);
        when(documentBuilder.parse(any(InputStream.class))).thenReturn(getDocument(versionInfo));
        DependencyInfo dependencyInfo = new DependencyInfo(
                "nl.praegus",
                "toolchain-fitnesse-plugin",
                "1.0.0",
                getModel(),
                factory);

        assertThat(dependencyInfo.getArtifactId()).isEqualTo("toolchain-fitnesse-plugin");
        assertThat(dependencyInfo.getVersionInPom()).isEqualTo("1.0.0");
        assertThat(dependencyInfo.getVersionOnMvnCentral()).isEqualTo("2.0.3");
    }

    @Test
    public void check_Plugin_Dependency_ReleaseNotes() throws Exception {
        DocumentBuilderFactory factory = mock(DocumentBuilderFactory.class);
        DocumentBuilder documentBuilder = mock(DocumentBuilder.class);
        when(factory.newDocumentBuilder()).thenReturn(documentBuilder);
        when(documentBuilder.parse(any(InputStream.class))).thenReturn(getDocument(versionInfo));
        DependencyInfo dependencyInfo = new DependencyInfo(
                "nl.praegus",
                "toolchain-fitnesse-plugin",
                "1.0.0",
                getModel(),
                factory);

        assertThat(dependencyInfo.getReleaseNoteUrls().get(0))
                .containsEntry("label", "Plugin")
                .containsEntry("url", "https://github.com/praegus/toolchain-fitnesse-plugin/releases");
    }

    @Test
    public void check_HSAC_Dependency_Values_With_Variable_Version() throws Exception {
        DocumentBuilderFactory factory = mock(DocumentBuilderFactory.class);
        DocumentBuilder documentBuilder = mock(DocumentBuilder.class);
        when(factory.newDocumentBuilder()).thenReturn(documentBuilder);
        when(documentBuilder.parse(any(InputStream.class))).thenReturn(getDocument(versionInfo));
        DependencyInfo dependencyInfo = new DependencyInfo(
                "nl.hsac",
                "hsac-fitnesse-fixtures",
                "${hsac.fixtures.version}",
                getModel(),
                factory);

        assertThat(dependencyInfo.getArtifactId()).isEqualTo("hsac-fitnesse-fixtures");
        assertThat(dependencyInfo.getVersionInPom()).isEqualTo("4.14.0");
        assertThat(dependencyInfo.getVersionOnMvnCentral()).isEqualTo("2.0.3");
    }

    //language=xml
    private final String versionInfo =
            "<metadata>\n" +
                    "<versioning>\n" +
                    "<latest>2.0.3</latest>\n" +
                    "</versioning>\n" +
                    "</metadata>";

    private Document getDocument(String document) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(document.getBytes()));
    }

    private Model getModel() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        return reader.read(new FileReader(System.getProperty("user.dir") + "/src/test/resources/MavenVersions/pom.xml"));
    }
}