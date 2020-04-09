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
        writer.startTag("table");
        if (symbol.hasProperty("class")) {
            writer.putAttribute("class", symbol.getProperty("class"));
        }
        ArrayList<String> tableHeaders = new ArrayList<>();
        tableHeaders.add("Page");
        tableHeaders.add("Pass");
        tableHeaders.add("Fail");
        tableHeaders.add("Latest");
        tableHeaders.add("Last 5 results");

        writer.startTag("tr");
            for (String tableHeader : tableHeaders) {
                writer.startTag("th");
                writer.putText(tableHeader);
                writer.endTag();
            }
        writer.endTag();

        writer.startTag("tr");
            for (String tableHeader : tableHeaders) {
                writer.startTag("td");
                writer.putText("asdlijlajwljdilas");
                writer.endTag();
            }
        writer.endTag();
        return writer.toHtml();
    }
}
