package nl.praegus.fitnesse.symbols.MavenProjectVersions;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DependencyInfoTest {
    @Test
    public void check_If_DependencyInfo_Values_Of_Dependency_Are_Correct() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader(System.getProperty("user.dir") + "/src/test/resources/MavenVersions/pom.xml"));
        List<Dependency> dependencies = model.getDependencies();
        String artifact = dependencies.get(0).getArtifactId();
        String group = dependencies.get(0).getGroupId();
        String version = dependencies.get(0).getVersion();

        DependencyInfo receivedValue = new DependencyInfo(group, artifact, version, model);

        assertThat(receivedValue.getArtifactid()).isEqualTo("jaxb-api");
        assertThat(receivedValue.getVersion()).isEqualTo("2.3.1");
    }

    @Test
    public void check_If_DependencyInfo_Values_Of_Dependency_2_Are_Correct() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader(System.getProperty("user.dir") + "/src/test/resources/MavenVersions/pom.xml"));
        List<Dependency> dependencies = model.getDependencies();
        String artifact = dependencies.get(1).getArtifactId();
        String group = dependencies.get(1).getGroupId();
        String version = dependencies.get(1).getVersion();

        DependencyInfo receivedValue = new DependencyInfo(group, artifact, version, model);

        assertThat(receivedValue.getArtifactid()).isEqualTo("jaxb-core");
        assertThat(receivedValue.getVersion()).isEqualTo("2.3.0.1");
    }

    @Test
    public void check_If_DependencyInfo_Values_Of_Plugin_Are_Correct() {
        DependencyInfo receivedValue = new DependencyInfo("nl.praegus", "toolchain-fitnesse-plugin");

        assertThat(receivedValue.getArtifactid()).isEqualTo("toolchain-fitnesse-plugin");
        assertThat(receivedValue.getLatest()).isEqualTo("2.0.3");
    }
}
