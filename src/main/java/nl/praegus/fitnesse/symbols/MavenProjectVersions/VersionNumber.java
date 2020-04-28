package nl.praegus.fitnesse.symbols.MavenProjectVersions;

public class VersionNumber{
    private final int[] versionNumberArray;

    public VersionNumber(String version) {
        String[] split = version.split("[-.]");
        versionNumberArray = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            String newInt = split[i].replaceAll("[^\\d.]", "");
            if (newInt.length() > 0) {
                versionNumberArray[i] = Integer.parseInt(newInt);
            } else {
                versionNumberArray[i] = 0;
            }
        }
    }

    public String compareTo(VersionNumber latestVersion) {
        // Calculate max length of version number
        int maxLength = Math.max(versionNumberArray.length, latestVersion.versionNumberArray.length);
        for (int i = 0; i < maxLength; i++) {
            int left = i < versionNumberArray.length ? versionNumberArray[i] : 0;
            int right = i < latestVersion.versionNumberArray.length ? latestVersion.versionNumberArray[i] : 0;
            // Compare current version & latest version
            if (left != right) {
                return left < right ? VersionStatus.OUTDATED.toString() : VersionStatus.AHEAD.toString();
            }
        }
        return VersionStatus.UP_TO_DATE.toString();
    }
}
