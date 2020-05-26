package nl.praegus.fitnesse.symbols.MavenProjectVersions;

public enum VersionStatus {
    OUTDATED("Outdated"),
    UP_TO_DATE("Up-to-date"),
    AHEAD("Ahead");

    private final String stringValue;

    VersionStatus(String envUrl) {
        this.stringValue = envUrl;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
