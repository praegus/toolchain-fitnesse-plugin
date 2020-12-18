package nl.praegus.fitnesse.symbols;

import com.github.javafaker.Faker;
import fitnesse.wikitext.parser.Matcher;
import fitnesse.wikitext.parser.Maybe;
import fitnesse.wikitext.parser.Parser;
import fitnesse.wikitext.parser.Rule;
import fitnesse.wikitext.parser.Symbol;
import fitnesse.wikitext.parser.SymbolType;
import fitnesse.wikitext.parser.Translation;
import fitnesse.wikitext.parser.Translator;

import java.util.List;
import java.util.Locale;

public class Fake extends SymbolType implements Rule, Translation {
    private static final String CATEGORY = "Category";
    private static final String ITEM = "Item";
    private static final String LOCALE = "Locale";


    public Fake() {
        super("Fake");
        wikiMatcher(new Matcher().string("!Fake"));
        wikiRule(this);
        htmlTranslation(this);
    }


    @Override
    public Maybe<Symbol> parse(Symbol current, Parser parser) {
        Maybe<Symbol> result = storeParenthesisContent(current, parser, CATEGORY);
        if (!result.isNothing()) {
            result = storeParenthesisContent(current, parser, ITEM);
            if (!result.isNothing()) {
                result = storeParenthesisContent(current, parser, LOCALE);
            }
        }
        return result;
    }

    @Override
    public String toTarget(Translator translator, Symbol symbol) {
        Faker faker = new Faker(new Locale(symbol.findProperty(LOCALE, "en")));
        String result;
        try {
            Object category = Faker.class.getDeclaredMethod(symbol.findProperty(CATEGORY, "")).invoke(faker);
            result = category.getClass().getDeclaredMethod(symbol.findProperty(ITEM, "")).invoke(category).toString();
        } catch (Exception e) {
            result = "ERROR_FAKING_DATA";
        }
        return result;
    }

    protected Maybe<Symbol> storeParenthesisContent(Symbol current, Parser parser, String key) {
        Maybe<Symbol> result = new Maybe<>(current);
        List<Symbol> lookAhead = parser.peek(new SymbolType[]{SymbolType.Whitespace, SymbolType.OpenParenthesis});
        if (lookAhead.size() != 0) {
            parser.moveNext(2);
            Maybe<String> format = parser.parseToAsString(SymbolType.CloseParenthesis);
            if (format.isNothing()) {
                result = Symbol.nothing;
            }
            current.putProperty(key, format.getValue());
        }
        return result;
    }
}
