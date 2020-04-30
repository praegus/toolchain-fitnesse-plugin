package nl.praegus.fitnesse.symbols.MavenProjectVersions;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MavenProjectVersionsSymbolTest {
    @Test
    public void compare_If_Current_Version_Is_Outdated() {
        MavenProjectVersionsSymbol MavenVersionSymbol = new MavenProjectVersionsSymbol();
        String currentVersion = "1.1.0";
        String latestVersion = "1.2.0";

        String receivedValue = MavenVersionSymbol.getStatus(currentVersion, latestVersion);

        assertThat(receivedValue).isEqualTo("Outdated");
    }

    @Test
    public void compare_If_Current_Version_Is_Ahead() {
        MavenProjectVersionsSymbol MavenVersionSymbol = new MavenProjectVersionsSymbol();
        String currentVersion = "1.6.0";
        String latestVersion = "1.2.0";

        String receivedValue = MavenVersionSymbol.getStatus(currentVersion, latestVersion);

        assertThat(receivedValue).isEqualTo("Ahead");
    }

    @Test
    public void compare_If_Current_Version_Is_UpToDate() {
        MavenProjectVersionsSymbol MavenVersionSymbol = new MavenProjectVersionsSymbol();
        String currentVersion = "2.2.0";
        String latestVersion = "2.2.0";

        String receivedValue = MavenVersionSymbol.getStatus(currentVersion, latestVersion);

        assertThat(receivedValue).isEqualTo("Up-to-date");
    }

    @Test
    public void check_if_pom_is_not_found() {
        MavenProjectVersionsSymbol MavenVersionSymbol = new MavenProjectVersionsSymbol();
        String receivedValue = MavenVersionSymbol.toTarget(null, null);
        assertThat(receivedValue).contains("POM not found!");
    }

}
