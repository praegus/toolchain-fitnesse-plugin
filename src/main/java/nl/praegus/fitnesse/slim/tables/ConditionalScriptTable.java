package nl.praegus.fitnesse.slim.tables;

import fitnesse.slim.SlimExpressionEvaluator;
import fitnesse.slim.instructions.Instruction;
import fitnesse.testsystems.TestExecutionException;
import fitnesse.testsystems.slim.SlimTestContext;
import fitnesse.testsystems.slim.Table;
import fitnesse.testsystems.slim.results.SlimTestResult;
import fitnesse.testsystems.slim.tables.ScriptTable;
import fitnesse.testsystems.slim.tables.SlimAssertion;
import fitnesse.testsystems.slim.tables.SlimExpectation;
import fitnesse.testsystems.slim.tables.SyntaxError;

import java.util.ArrayList;
import java.util.List;

public class ConditionalScriptTable extends ScriptTable {

    private boolean conditionResult;

    public ConditionalScriptTable(Table table, String tableId, SlimTestContext context) {
        super(table, tableId, context);
    }

    @Override
    protected String getTableKeyword() {
        return "script if";
    }

    @Override
    // returns a list of statements
    protected List<SlimAssertion> instructionsForRow(int row) throws TestExecutionException {
        List<SlimAssertion> assertions;
        if (row > 1 && !conditionResult) {
            assertions = skip(row);
        } else {
            assertions = super.instructionsForRow(row);
        }
        return assertions;
    }

    @Override
    protected List<SlimAssertion> invokeAction(int startingCol, int endingCol, int row, SlimExpectation expectation) throws SyntaxError {
        List<SlimAssertion> assertions = new ArrayList<>();
        if (row == 1) {
            if (table.getColumnCountInRow(row) > 1) {
                throw new SyntaxError("Conditional script tables can contain only the condition in the first row");
            }
            String expr = this.table.getCellContents(0, row);
            try {
                conditionResult = Boolean.valueOf(new SlimExpressionEvaluator().evaluate(replaceSymbols(expr)).toString());
            } catch (IllegalArgumentException e) {
                throw new SyntaxError(e.getMessage());
            }
        } else {
            assertions.addAll(super.invokeAction(startingCol, endingCol, row, expectation));
        }
        return assertions;
    }

    private List<SlimAssertion> skip(int row) {
        List<SlimAssertion> assertions = new ArrayList<>();
        SlimAssertion skipAssertion = createSkipAssertion(row);
        assertions.add(skipAssertion);
        return assertions;
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
}
