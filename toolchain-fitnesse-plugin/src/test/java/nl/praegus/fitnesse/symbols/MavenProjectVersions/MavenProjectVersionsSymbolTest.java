package nl.praegus.fitnesse.symbols.MavenProjectVersions;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

public class MavenProjectVersionsSymbolTest {
    @Test
    public void check_If_Correct_Title_Is_Used() {
        MavenProjectVersionsSymbol mavenVersionSymbol = new MavenProjectVersionsSymbol();
        String receivedValue = mavenVersionSymbol.toTarget(null, null);
        assertThat(receivedValue).contains("<h3>Maven Version Checker</h3>");
    }

    @Test
    public void check_If_Html_Contains_FitNesse_Information() throws FileNotFoundException {
        ProjectDependencyInfo projectDependencyInfo = new ProjectDependencyInfo("/src/test/resources/MavenVersions/pom.xml");
        String receivedValue = MavenProjectVersionsSymbol.getVersionTableHtmlAsString(projectDependencyInfo.getDependencyInfo());

        String expectedValue = "<tr><td>fitnesse</td>" + System.lineSeparator() +
                "<td>20200404</td>" + System.lineSeparator() +
                "<td>20210606</td>" + System.lineSeparator() +
                "<td class=\"Outdated\">Outdated</td>";

        String expectedValue2 = "<a href=\"http://fitnesse.org/FitNesse.ReleaseNotes\" target=\"_blank\">Fitnesse</a>";

        assertThat(receivedValue).contains(expectedValue);
        assertThat(receivedValue).contains(expectedValue2);
    }
}
