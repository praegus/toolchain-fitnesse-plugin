package nl.praegus.fitnesse.slim.tables;

import fitnesse.slim.instructions.Instruction;
import fitnesse.testrunner.WikiTestPage;
import fitnesse.testsystems.slim.*;
import fitnesse.testsystems.slim.results.SlimTestResult;
import fitnesse.testsystems.slim.tables.*;
import fitnesse.wiki.WikiPage;
import fitnesse.wiki.WikiPageDummy;
import fitnesse.wiki.WikiPageUtil;
import fitnesse.wiki.fs.InMemoryPage;

import java.util.ArrayList;
import java.util.List;

public class PausingTable extends ScriptTable {

    public PausingTable(Table table, String tableId, SlimTestContext context) {
        super(table, tableId, context);
    }

    @Override
    protected String getTableKeyword() {
        return "debug script";
    }

    @Override
    protected List<SlimAssertion> invokeAction(int startingCol, int endingCol, int row, SlimExpectation expectation) throws SyntaxError {
        List<SlimAssertion> assertions = new ArrayList<>();
        pauseOnRow(assertions, row);
        assertions.addAll(super.invokeAction(startingCol, endingCol, row, expectation));
        return assertions;
    }

    private void pauseOnRow(List<SlimAssertion> assertions, int row) {
        try {
            assertions.addAll(createPauseTestImportTable().getAssertions());
            assertions.addAll(createPauseTestLibraryTable().getAssertions());
        } catch (SyntaxError e) {
            System.err.println("ERROR: exception creating import for pause test");
        }

        SlimAssertion pauseAssertion = createPauseAssertion( table.getColumnCountInRow(row) - 1, row);
        assertions.add(pauseAssertion);
    }

    private SlimAssertion createPauseAssertion(int lastCol, int row) {
        String nextInstruction = "Next: " + getRowContents(row);
        Instruction instruction = callFunction(getTableType() + "Actor", "pause", nextInstruction);
        return makeAssertion(instruction, new RowExpectation(lastCol, row) {
            @Override
            protected SlimTestResult createEvaluationMessage(String actual, String expected) {
                return SlimTestResult.plain();
            }
        });
    }

    private String getRowContents(int row) {
        StringBuilder result = new StringBuilder();

        for(int c = 0; c < table.getColumnCountInRow(row); c++) {
            String cellContent = table.getCellContents(c, row).replaceAll("<[^>]*>","");
            result.append(String.format(" | %s", cellContent));
        }
        result.append(" |\r\n");
        return result.toString();
    }

    private ImportTable createPauseTestImportTable() {
        WikiPage page = InMemoryPage.makeRoot("root");
        WikiPageUtil.setPageContents(page, "|Import|\n|nl.praegus.fitnesse.slim.fixtures.util|\n");
        String html = page.getHtml();
        TableScanner<HtmlTable> ts = new HtmlTableScanner(html);
        Table t = ts.getTable(0);
        SlimTestContextImpl testContext = new SlimTestContextImpl(new WikiTestPage(new WikiPageDummy()));
        SlimTableFactory tableFactory = new SlimTableFactory();
        return (ImportTable) tableFactory.makeSlimTable(t, "id", testContext);
    }

    private LibraryTable createPauseTestLibraryTable() {
        WikiPage page = InMemoryPage.makeRoot("root");
        WikiPageUtil.setPageContents(page, "|Library|\n|pause test fixture|\n");
        String html = page.getHtml();
        TableScanner<HtmlTable> ts = new HtmlTableScanner(html);
        Table t = ts.getTable(0);
        SlimTestContextImpl testContext = new SlimTestContextImpl(new WikiTestPage(new WikiPageDummy()));
        SlimTableFactory tableFactory = new SlimTableFactory();
        return (LibraryTable) tableFactory.makeSlimTable(t, "id", testContext);
    }

}



