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
//    @Test
//    public void check_If_DependencyInfo_Values_Of_Dependency_Are_Correct() throws IOException, XmlPullParserException {
//        MavenXpp3Reader reader = new MavenXpp3Reader();
//        Model model = reader.read(new FileReader(System.getProperty("user.dir") + "/src/test/resources/MavenVersions/pom.xml"));
//        List<Dependency> dependencies = model.getDependencies();
//        String artifact = dependencies.get(0).getArtifactId();
//        String group = dependencies.get(0).getGroupId();
//        String version = dependencies.get(0).getVersion();
//
//        DependencyInfo receivedValue = new DependencyInfo(artifact, version, model);
//        receivedValue.setLatestVersionUrl("");
//
//        assertThat(receivedValue.getArtifactId()).isEqualTo("jaxb-api");
//        assertThat(receivedValue.getVersionInPom()).isEqualTo("2.3.1");
//    }
//
//    @Test
//    public void check_If_DependencyInfo_Values_Of_Dependency_2_Are_Correct() throws IOException, XmlPullParserException {
//        MavenXpp3Reader reader = new MavenXpp3Reader();
//        Model model = reader.read(new FileReader(System.getProperty("user.dir") + "/src/test/resources/MavenVersions/pom.xml"));
//        List<Dependency> dependencies = model.getDependencies();
//        String artifact = dependencies.get(1).getArtifactId();
//        String group = dependencies.get(1).getGroupId();
//        String version = dependencies.get(1).getVersion();
//
//        DependencyInfo receivedValue = new DependencyInfo(group, artifact, version, model);
//
//        assertThat(receivedValue.getArtifactId()).isEqualTo("jaxb-core");
//        assertThat(receivedValue.getVersionInPom()).isEqualTo("2.3.0.1");
//    }

//    @Test
//    public void check_If_DependencyInfo_Values_Of_Plugin_Are_Correct() {
//        DependencyInfo receivedValue = new DependencyInfo("toolchain-fitnesse-plugin");
////        receivedValue.setLatestVersionUrl("file:\\toolchain-fitnesse-plugin\\src\\test\\resources\\MavenVersions\\latestVersion\\nl\\%s\\%s\\maven-metadata.xml");
//        receivedValue.setLatestVersionUrl("file:C:\\Users\\Alex\\source\\repos\\toolchain-fitnesse-plugin\\src\\test\\resources\\MavenVersions\\latestVersion\\nl\\praegus\\toolchain-fitnesse-plugin\\maven-metadata.xml");
//        receivedValue.setVersionOnMvnCentral("praegus", "toolchain-fitnesse-plugin");
//
//        assertThat(receivedValue.getArtifactId()).isEqualTo("toolchain-fitnesse-plugin");
//        assertThat(receivedValue.getVersionOnMvnCentral()).isEqualTo("2.0.3");
//    }
}
