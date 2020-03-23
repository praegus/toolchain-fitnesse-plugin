package nl.praegus.fitnesse.decorators;

import fitnesse.wikitext.parser.Symbol;
import fitnesse.wikitext.parser.SymbolType;
import fitnesse.wikitext.parser.VariableSource;
import fitnesse.wikitext.parser.decorator.ParsedSymbolDecorator;

import static fitnesse.wikitext.parser.decorator.SymbolClassPropertyAppender.classPropertyAppender;
import static fitnesse.wikitext.parser.decorator.SymbolInspector.inspect;

public class TableSymbolDecorator implements ParsedSymbolDecorator {
    public static void install() {
        fitnesse.wikitext.parser.Table.symbolType.addDecorator(new TableSymbolDecorator());
    }

    @Override
    public void handleParsedSymbol(Symbol table, VariableSource variableSource) {
        setTableTypeClass(table);
    }

    private void setTableTypeClass(Symbol table) {
        Symbol firstCell = table.getChildren()
                .get(0)
                .getChildren()
                .get(0);
        String firstCellContent = inspect(firstCell).getRawContent().toLowerCase();
        String className = null;
        switch (firstCellContent) {
            case "script":
            case "conditional script":
                className = "scriptTable";
                break;
            case "scenario":
            case "conditional scenario":
            case "looping scenario":
                className = "scenarioTable";
                break;
            case "debug script":
                className = "debugScriptTable";
                break;
            case "table template":
                className = "tableTemplate";
                break;
            case "storyboard":
                className = "storyboardTable";
                break;
            case "import":
                className = "importTable";
                break;
            case "library":
                className = "libraryTable";
                break;
            case "comment":
                className = "commentTable";
                break;
            default:
                className = "decisionTable";
        }

        classPropertyAppender().addPropertyValue(table, "toolchainTable");
        classPropertyAppender().addPropertyValue(table, className);
    }
}
