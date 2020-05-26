package nl.praegus.fitnesse.symbols.MavenProjectVersions;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VersionNumberTest {
    @Test
    public void compare_If_Current_SemanticVersion_Is_Outdated() {
        String current = "1.1.0";
        String latest = "1.2.0";
        VersionNumber currentVersion = new VersionNumber(current);
        VersionNumber latestVersion = new VersionNumber(latest);

        String receivedValue = currentVersion.compareTo(latestVersion);

        assertThat(receivedValue).isEqualTo("Outdated");
    }

    @Test
    public void compare_If_Current_SemanticVersion_Is_Ahead() {
        String current = "4.1.0";
        String latest = "2.3.0";

        VersionNumber currentVersion = new VersionNumber(current);
        VersionNumber latestVersion = new VersionNumber(latest);

        String receivedValue = currentVersion.compareTo(latestVersion);

        assertThat(receivedValue).isEqualTo("Ahead");
    }

    @Test
    public void compare_If_Current_SemanticVersion_Is_UpToDate() {
        String current = "12.4.2";
        String latest = "12.4.2";

        VersionNumber currentVersion = new VersionNumber(current);
        VersionNumber latestVersion = new VersionNumber(latest);

        String receivedValue = currentVersion.compareTo(latestVersion);

        assertThat(receivedValue).isEqualTo("Up-to-date");
    }

    @Test
    public void compare_If_Current_SemanticVersion_With_SNAPSHOT_Is_Outdated() {
        String current = "1.1.0-SNAPSHOT";
        String latest = "1.2.0";

        VersionNumber currentVersion = new VersionNumber(current);
        VersionNumber latestVersion = new VersionNumber(latest);

        String receivedValue = currentVersion.compareTo(latestVersion);

        assertThat(receivedValue).isEqualTo("Outdated");
    }

    @Test
    public void compare_If_Current_SemanticVersion_With_SNAPSHOT_Is_Ahead() {
        String current = "4.1.0";
        String latest = "2.3.0-SNAPSHOT";

        VersionNumber currentVersion = new VersionNumber(current);
        VersionNumber latestVersion = new VersionNumber(latest);

        String receivedValue = currentVersion.compareTo(latestVersion);

        assertThat(receivedValue).isEqualTo("Ahead");
    }

    @Test
    public void compare_If_Current_SemanticVersion_With_SNAPSHOT_Is_UpToDate() {
        String current = "12.4.2-SNAPSHOT";
        String latest = "12.4.2-SNAPSHOT";

        VersionNumber currentVersion = new VersionNumber(current);
        VersionNumber latestVersion = new VersionNumber(latest);

        String receivedValue = currentVersion.compareTo(latestVersion);

        assertThat(receivedValue).isEqualTo("Up-to-date");
    }

    @Test
    public void compare_If_Current_DateVersion_Is_Outdated() {
        String current = "20190420";
        String latest = "20200918";

        VersionNumber currentVersion = new VersionNumber(current);
        VersionNumber latestVersion = new VersionNumber(latest);

        String receivedValue = currentVersion.compareTo(latestVersion);

        assertThat(receivedValue).isEqualTo("Outdated");
    }

    @Test
    public void compare_If_Current_DateVersion_Is_Ahead() {
        String current = "20200804";
        String latest = "20190609";

        VersionNumber currentVersion = new VersionNumber(current);
        VersionNumber latestVersion = new VersionNumber(latest);

        String receivedValue = currentVersion.compareTo(latestVersion);

        assertThat(receivedValue).isEqualTo("Ahead");
    }

    @Test
    public void compare_If_Current_DateVersion_Is_UpToDate() {
        String current = "20200422";
        String latest = "20200422";

        VersionNumber currentVersion = new VersionNumber(current);
        VersionNumber latestVersion = new VersionNumber(latest);

        String receivedValue = currentVersion.compareTo(latestVersion);

        assertThat(receivedValue).isEqualTo("Up-to-date");
    }

    @Test
    public void compare_If_Beta_Version_Is_Handled_Correctly_With_FitNesse_Versions() {
        String current = "BETA-SNAPSHOT";
        String latest = "20200422";

        VersionNumber currentVersion = new VersionNumber(current);
        VersionNumber latestVersion = new VersionNumber(latest);

        String receivedValue = currentVersion.compareTo(latestVersion);

        assertThat(receivedValue).isEqualTo("Outdated");
    }

    @Test
    public void compare_If_Beta_Version_Is_Handled_Correctly_With_Semantic_Versions() {
        String current = "BETA-SNAPSHOT";
        String latest = "1.2.3.4";

        VersionNumber currentVersion = new VersionNumber(current);
        VersionNumber latestVersion = new VersionNumber(latest);

        String receivedValue = currentVersion.compareTo(latestVersion);

        assertThat(receivedValue).isEqualTo("Outdated");
    }

    @Test
    public void compare_If_Beta_Version_Is_Handled_Correctly_With_Complicated_Versions() {
        String current = "BETA-SNAPSHOT";
        String latest = "v1-rev110-1.25.0";

        VersionNumber currentVersion = new VersionNumber(current);
        VersionNumber latestVersion = new VersionNumber(latest);

        String receivedValue = currentVersion.compareTo(latestVersion);

        assertThat(receivedValue).isEqualTo("Outdated");
    }

    @Test
    public void compare_If_Complicated_Version_Is_UpToDate() {
        String current = "v1-rev110-1.25.0";
        String latest = "v1-rev110-1.25.0";

        VersionNumber currentVersion = new VersionNumber(current);
        VersionNumber latestVersion = new VersionNumber(latest);

        String receivedValue = currentVersion.compareTo(latestVersion);

        assertThat(receivedValue).isEqualTo("Up-to-date");
    }

    @Test
    public void compare_If_Complicated_Version_Is_Ahead() {
        String current = "v2-rev110-1.25.0";
        String latest = "v1-rev110-1.25.0";

        VersionNumber currentVersion = new VersionNumber(current);
        VersionNumber latestVersion = new VersionNumber(latest);

        String receivedValue = currentVersion.compareTo(latestVersion);

        assertThat(receivedValue).isEqualTo("Ahead");
    }

    @Test
    public void compare_If_Complicated_Version_Is_Outdated() {
        String current = "v1-rev110-1.24.0";
        String latest = "v1-rev110-1.25.0";

        VersionNumber currentVersion = new VersionNumber(current);
        VersionNumber latestVersion = new VersionNumber(latest);

        String receivedValue = currentVersion.compareTo(latestVersion);

        assertThat(receivedValue).isEqualTo("Outdated");
    }
}
