package nl.praegus.fitnesse.slim.tables;

import fitnesse.slim.instructions.Instruction;
import fitnesse.testsystems.slim.SlimTestContext;
import fitnesse.testsystems.slim.Table;
import fitnesse.testsystems.slim.results.SlimTestResult;
import fitnesse.testsystems.slim.tables.ScriptTable;
import fitnesse.testsystems.slim.tables.SlimAssertion;
import fitnesse.testsystems.slim.tables.SlimExpectation;
import fitnesse.testsystems.slim.tables.SyntaxError;

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
            String cellContent = table.getCellContents(c, row).replaceAll("\\<[^>]*>","");
            result.append(String.format(" | %s", cellContent));
        }
        result.append(" |\r\n");
        return result.toString();
    }

}
