package nl.praegus.fitnesse.symbols.MavenProjectVersions;

import fitnesse.wikitext.parser.*;

import java.io.FileNotFoundException;
import java.util.*;

public class MavenProjectVersionsSymbol extends SymbolType implements Rule, Translation {

    public MavenProjectVersionsSymbol() {
        super("VersionCheckerSymbol");
        wikiMatcher(new Matcher().string("!VersionChecker"));
        wikiRule(this);
        htmlTranslation(this);
    }

    @Override
    public Maybe<Symbol> parse(Symbol current, Parser parser) {
        return new Maybe<>(current);
    }

    @Override
    public String toTarget(Translator translator, Symbol symbol) {
        HtmlWriter writer = new HtmlWriter();
        // Generate the html
        writer.startTag("div");
        writer.putAttribute("id", "mavenVersions");

        writer.startTag("h3");
        writer.putText("Maven Version Checker");
        writer.endTag();

        try {
            ProjectDependencyInfo projectDependencyInfo = new ProjectDependencyInfo();
            writer.putText(getVersionTableHtmlAsString(projectDependencyInfo.getDependencyInfo()));
        } catch (FileNotFoundException e) {
            writer.startTag("h4");
            writer.putText("POM not found!");
            writer.endTag();
        }
        writer.endTag();

        return writer.toHtml();
    }

    public static String getVersionTableHtmlAsString(List<DependencyInfo> dependencyList) {
        HtmlWriter writer = new HtmlWriter();

        // Start of the table
        writer.startTag("table");
        writer.putAttribute("id", "versioncheck");

        // Table title
        writer.startTag("tr");
        writer.startTag("th");
        writer.putAttribute("colspan", "5");
        writer.putText("Versioncheck");
        writer.endTag();
        writer.endTag();

        // Table headers
        List<String> tableHeaders = Arrays.asList("Name", "Current version", "Newest Version", "Status", "Release notes");
        writer.startTag("tr");
        for (String tableHeader : tableHeaders) {
            writer.putText(generateTableRowTdHtmlAsString(tableHeader, null));
        }
        writer.endTag();

        // Table row
        for (DependencyInfo dependency : dependencyList) {
            writer.putText(generateTableRowHtmlAsString(dependency));
        }

        writer.endTag();

        return writer.toHtml();
    }

    private static String generateTableRowHtmlAsString(DependencyInfo tableRowData) {
        HtmlWriter writer = new HtmlWriter();
        String current = tableRowData.getVersionInPom();
        String status = getStatus(current, tableRowData.getVersionOnMvnCentral());

        writer.startTag("tr");
        writer.putText(generateTableRowTdHtmlAsString(tableRowData.getArtifactId(), null));
        writer.putText(generateTableRowTdHtmlAsString(current, null));
        writer.putText(generateTableRowTdHtmlAsString(tableRowData.getVersionOnMvnCentral(), null));
        writer.putText(generateTableRowTdHtmlAsString(status, status));
        writer.putText(generateReleaseNotesHtmlAsString(tableRowData));
        writer.endTag();

        return writer.toHtml();
    }

    private static String generateTableRowTdHtmlAsString(String text, String className) {
        final HtmlWriter writer = new HtmlWriter();
        writer.startTag("td");
        if (className != null) {
            writer.putAttribute("class", className);
        }
        writer.putText(text);
        writer.endTag();

        return writer.toHtml();
    }

    private static String generateReleaseNotesHtmlAsString(DependencyInfo tableRowData) {
        HtmlWriter writer = new HtmlWriter();
        return writeReleaseNotes(writer, tableRowData.getReleaseNoteUrls()).toHtml().replaceAll(System.lineSeparator(), "");
    }

    private static void writeReleaseNotesNotFound(HtmlWriter writer) {
        writer.putText("Release notes not found");
    }

    private static void writeReleaseNotesLink(HtmlWriter writer, Map<String, String> info) {
        writer.startTag("a");
        writer.putAttribute("href", info.get("url"));
        writer.putAttribute("target", "_blank");
        writer.putText(info.get("label"));
        writer.endTag();
    }

    private static HtmlWriter writeReleaseNotes(HtmlWriter writer, List<Map<String, String>> releaseNotesInfo) {
        writer.startTag("td");
        releaseNotesInfo.forEach(info -> {
            writer.startTag("div");

            if (info.get("url")!= null && !info.get("url").isEmpty()) {
                writeReleaseNotesLink(writer, info);
            }
            else {
                writeReleaseNotesNotFound(writer);
            }
            writer.endTag();
        });
        writer.endTag();
        return writer;
    }

    private static String getStatus(String current, String latest) {
        VersionNumber currentVersion = new VersionNumber(current);
        VersionNumber latestVersion = new VersionNumber(latest);
        return currentVersion.compareTo(latestVersion);
    }
}
