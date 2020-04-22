package nl.praegus.fitnesse.symbols.MavenProjectVersions;

public class Version implements Comparable<Version>  {
    public final int[] numbers;

    public Version(String version) {
        String[] split = version.split("[-.]");
        numbers = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            String newInt = split[i].replaceAll("[^\\d.]", "");
            if (newInt.length() > 0) {
                numbers[i] = Integer.parseInt(newInt);
            } else {
                numbers[i] = 0;
            }
        }
    }

    @Override
    public int compareTo(Version another) {
        final int maxLength = Math.max(numbers.length, another.numbers.length);
        for (int i = 0; i < maxLength; i++) {
            final int left = i < numbers.length ? numbers[i] : 0;
            final int right = i < another.numbers.length ? another.numbers[i] : 0;
            if (left != right) {
                return left < right ? -1 : 1;
            }
        }
        return 0;
    }
}
