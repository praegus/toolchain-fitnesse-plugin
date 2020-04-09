package nl.praegus.fitnesse.symbols;

import fitnesse.wikitext.parser.*;

import java.util.ArrayList;

public class TestHistorySymbol extends SymbolType implements Rule, Translation {

    public TestHistorySymbol() {
        super("TestHistorySymbol");
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
        writer.startTag("h3");
        writer.putText("Maven Version Checker");
        writer.endTag();

        writer.startTag("table");
        writer.putAttribute("class", "versioncheck");

        writer.startTag("tr");
            writer.startTag("th");
            writer.putAttribute("colspan", "4");
            writer.putText("Versioncheck");
            writer.endTag();
        writer.endTag();

        ArrayList<String> tableHeaders = new ArrayList<>();
        tableHeaders.add("Name");
        tableHeaders.add("Current Version");
        tableHeaders.add("Newest Version");
        tableHeaders.add("Status");

        writer.startTag("tr");
            for (String tableHeader : tableHeaders) {
                writer.startTag("td");
                writer.putText(tableHeader);
                writer.endTag();
            }
        writer.endTag();

        writer.startTag("tr");
            for (String tableHeader : tableHeaders) {
                writer.startTag("td");
                writer.putText("TEST");
                writer.endTag();
            }
        writer.endTag();
        return writer.toHtml();
    }
}
