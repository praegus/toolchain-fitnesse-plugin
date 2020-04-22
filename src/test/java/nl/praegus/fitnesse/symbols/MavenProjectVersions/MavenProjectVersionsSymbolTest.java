package nl.praegus.fitnesse.symbols.MavenProjectVersions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MavenProjectVersionsSymbolTest {
    @Test
    public void compare_If_Current_SemanticVersion_Is_Outdated() {
        MavenProjectVersionsSymbol MavenVersionSymbol = new MavenProjectVersionsSymbol();
        String currentVersion = "1.1.0";
        String latestVersion = "1.2.0";

        String receivedValue = MavenVersionSymbol.getStatus(currentVersion, latestVersion);

        assertThat(receivedValue).isEqualTo("Outdated");
    }

    @Test
    public void compare_If_Current_SemanticVersion_Is_Ahead() {
        MavenProjectVersionsSymbol MavenVersionSymbol = new MavenProjectVersionsSymbol();
        String currentVersion = "4.1.0";
        String latestVersion = "2.3.0";

        String receivedValue = MavenVersionSymbol.getStatus(currentVersion, latestVersion);

        assertThat(receivedValue).isEqualTo("Ahead");
    }

    @Test
    public void compare_If_Current_SemanticVersion_Is_UpToDate() {
        MavenProjectVersionsSymbol MavenVersionSymbol = new MavenProjectVersionsSymbol();
        String currentVersion = "12.4.2";
        String latestVersion = "12.4.2";

        String receivedValue = MavenVersionSymbol.getStatus(currentVersion, latestVersion);

        assertThat(receivedValue).isEqualTo("Up-to-date");
    }

    @Test
    public void compare_If_Current_SemanticVersion_With_SNAPSHOT_Is_Outdated() {
        MavenProjectVersionsSymbol MavenVersionSymbol = new MavenProjectVersionsSymbol();
        String currentVersion = "1.1.0-SNAPSHOT";
        String latestVersion = "1.2.0";

        String receivedValue = MavenVersionSymbol.getStatus(currentVersion, latestVersion);

        assertThat(receivedValue).isEqualTo("Outdated");
    }

    @Test
    public void compare_If_Current_SemanticVersion_With_SNAPSHOT_Is_Ahead() {
        MavenProjectVersionsSymbol MavenVersionSymbol = new MavenProjectVersionsSymbol();
        String currentVersion = "4.1.0";
        String latestVersion = "2.3.0-SNAPSHOT";

        String receivedValue = MavenVersionSymbol.getStatus(currentVersion, latestVersion);

        assertThat(receivedValue).isEqualTo("Ahead");
    }

    @Test
    public void compare_If_Current_SemanticVersion_With_SNAPSHOT_Is_UpToDate() {
        MavenProjectVersionsSymbol MavenVersionSymbol = new MavenProjectVersionsSymbol();
        String currentVersion = "12.4.2-SNAPSHOT";
        String latestVersion = "12.4.2-SNAPSHOT";

        String receivedValue = MavenVersionSymbol.getStatus(currentVersion, latestVersion);

        assertThat(receivedValue).isEqualTo("Up-to-date");
    }

    @Test
    public void compare_If_Current_DateVersion_Is_Outdated() {
        MavenProjectVersionsSymbol MavenVersionSymbol = new MavenProjectVersionsSymbol();
        String currentVersion = "20190420";
        String latestVersion = "20200918";

        String receivedValue = MavenVersionSymbol.getStatus(currentVersion, latestVersion);

        assertThat(receivedValue).isEqualTo("Outdated");
    }

    @Test
    public void compare_If_Current_DateVersion_Is_Ahead() {
        MavenProjectVersionsSymbol MavenVersionSymbol = new MavenProjectVersionsSymbol();
        String currentVersion = "20200804";
        String latestVersion = "20190609";

        String receivedValue = MavenVersionSymbol.getStatus(currentVersion, latestVersion);

        assertThat(receivedValue).isEqualTo("Ahead");
    }

    @Test
    public void compare_If_Current_DateVersion_Is_UpToDate() {
        MavenProjectVersionsSymbol MavenVersionSymbol = new MavenProjectVersionsSymbol();
        String currentVersion = "20200422";
        String latestVersion = "20200422";

        String receivedValue = MavenVersionSymbol.getStatus(currentVersion, latestVersion);

        assertThat(receivedValue).isEqualTo("Up-to-date");
    }
}
