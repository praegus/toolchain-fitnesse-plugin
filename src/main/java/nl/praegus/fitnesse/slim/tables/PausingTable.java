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
import nl.praegus.fitnesse.slim.tables.util.PauseUtil;

import java.util.ArrayList;
import java.util.List;

public class PausingTable extends ScriptTable {
    private boolean cancelled = false;

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
        PauseUtil pause = new PauseUtil();
        String nextInstruction = "Next: " + getRowContents(row);
        if (!cancelled && !pause.pause(nextInstruction)) {
            cancelled = true;
            abortTest(assertions, row);
        } else if (cancelled) {
            assertions.add(createSkipAssertion(row));
        } else {
            assertions.addAll(super.invokeAction(startingCol, endingCol, row, expectation));
        }
        return assertions;
    }

    private void abortTest(List<SlimAssertion> assertions, int row) {
        assertions.addAll(createStopTestLibraryTable().getAssertions());
        SlimAssertion abortAssertion = createAbortAssertion(table.getColumnCountInRow(row) - 1, row);
        assertions.add(abortAssertion);
    }

    private SlimAssertion createAbortAssertion(int lastCol, int row) {
        String stopMsg = "Debugging aborted";
        Instruction instruction = callFunction(getTableType() + "Actor", "stop test if is", stopMsg, stopMsg);
        return (makeAssertion(instruction, new RowExpectation(lastCol, row) {
            @Override
            protected SlimTestResult createEvaluationMessage(String actual, String expected) {
                return SlimTestResult.plain();
            }
        }));
    }

    private SlimAssertion createSkipAssertion(int row) {
        Instruction instruction = Instruction.NOOP_INSTRUCTION;
        return makeAssertion(instruction, new SkipExpectation(0, row));
    }

    protected class SkipExpectation extends RowExpectation {
        SkipExpectation(int col, int row) {
            super(col, row);
        }

        @Override
        protected SlimTestResult createEvaluationMessage(String actual, String expected) {
            return SlimTestResult.testNotRun();
        }
    }

    private String getRowContents(int row) {
        StringBuilder result = new StringBuilder();
        for (int c = 0; c < table.getColumnCountInRow(row); c++) {
            String cellContent = table.getCellContents(c, row).replaceAll("<[^>]*>", "");
            result.append(String.format(" | %s", cellContent));
        }
        result.append(" |\r\n");
        return result.toString();
    }

    private LibraryTable createStopTestLibraryTable() {
        WikiPage root = InMemoryPage.makeRoot("root");
        WikiPageUtil.setPageContents(root, "|Library|\n|stop test fixture|\n");
        String html = root.getHtml();
        TableScanner<HtmlTable> ts = new HtmlTableScanner(html);
        Table t = ts.getTable(0);
        SlimTestContextImpl testContext = new SlimTestContextImpl(new WikiTestPage(new WikiPageDummy()));
        SlimTableFactory tableFactory = new SlimTableFactory();
        return (LibraryTable) tableFactory.makeSlimTable(t, "id", testContext);
    }
}
