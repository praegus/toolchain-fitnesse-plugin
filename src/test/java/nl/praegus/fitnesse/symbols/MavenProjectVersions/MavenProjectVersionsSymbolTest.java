package nl.praegus.fitnesse.symbols.MavenProjectVersions;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

public class MavenProjectVersionsSymbolTest {
    @Test
    public void compare_If_Current_Version_Is_UpToDate() {
        MavenProjectVersionsSymbol MavenVersionSymbol = new MavenProjectVersionsSymbol();
        String currentVersion = "2.2.0";
        String latestVersion = "2.2.0";

        String receivedValue = MavenVersionSymbol.getStatus(currentVersion, latestVersion);

        assertThat(receivedValue).isEqualTo("Up-to-date");
    }

    @Test
    public void check_If_Correct_Title_Is_Used() {
        MavenProjectVersionsSymbol MavenVersionSymbol = new MavenProjectVersionsSymbol();
        String receivedValue = MavenVersionSymbol.toTarget(null, null);
        assertThat(receivedValue).contains("<h3>Maven Version Checker</h3>");
    }

    @Test
    public void check_If_Pom_Is_Not_Found() {
        MavenProjectVersionsSymbol MavenVersionSymbol = new MavenProjectVersionsSymbol();
        String receivedValue = MavenVersionSymbol.toTarget(null, null);
        assertThat(receivedValue).contains("POM not found!");
    }

//    @Test
//    public void check_If_Html_Contains_FitNesse_Information() throws FileNotFoundException {
//        MavenProjectVersionsSymbol MavenVersionSymbol = new MavenProjectVersionsSymbol();
//        ProjectDependencyInfo projectDependencyInfo = new ProjectDependencyInfo("/src/test/resources/MavenVersions/pom.xml");
//        String receivedValue = MavenVersionSymbol.getVersionTableHelper(projectDependencyInfo.getDependencyInfo());
//
////        String expectedValue = "<tr><td>fitnesse</td>\n" +
////                "<td>20200404</td>\n" +
////                "<td>20200501</td>\n" +
////                "<td class=\"Outdated\">Outdated</td>\n";
//
//        String expectedValue = "<table id=\"versioncheck\">";
//
////        String expectedValue = "<tr><td>fitnesse</td>\n" +
////                "<td>20200404</td>\n" +
////                "<td>20200501</td>\n";
//
//        assertThat(receivedValue).contains(expectedValue);
//    }

}
