package nl.praegus.fitnesse.slim.tables;

import fitnesse.slim.instructions.AssignInstruction;
import fitnesse.slim.instructions.Instruction;
import fitnesse.testsystems.*;
import fitnesse.testsystems.slim.SlimTestContext;
import fitnesse.testsystems.slim.Table;
import fitnesse.testsystems.slim.results.SlimTestResult;
import fitnesse.testsystems.slim.tables.*;
import org.apache.commons.lang3.StringUtils;

import java.util.*;


public class LoopingScenarioTable extends ScenarioTable {

    public LoopingScenarioTable(Table table, String tableId, SlimTestContext testContext) {
        super(table, tableId, testContext);
    }

    @Override
    protected String getTableType() {
        return "loopingScenarioTable";
    }

    public List<SlimAssertion> call(final Map<String, String> scenarioArguments,
                                    SlimTable parentTable, int row) throws TestExecutionException {

        List<SlimAssertion> assertions = new ArrayList<>();
        if (scenarioArguments.size() > 1) {
            throw new SyntaxError("In order to loop a scenario, you have to provide exactly one argument");
        } else {
            for (Map.Entry<String, String> scenarioArgument : scenarioArguments.entrySet()) {
                String arg = scenarioArgument.getKey();
                String replacedSymbolsArg = replaceSymbols(scenarioArguments.get(arg));
                String[] argList = replacedSymbolsArg.split(",");
                int count = 0;

                for (String argEntry : argList) {
                    Table newTable = getTable().asTemplate(content -> {
                        if (getInputs().contains(arg)) {
                            content = StringUtils.replace(content, "@" + arg, argEntry);
                            content = StringUtils.replace(content, "@{" + arg + "}", argEntry);
                        } else {
                            throw new SyntaxError(String.format("The argument %s is not an input to the scenario.", arg));
                        }
                        return content;
                    });

                    updateLoopCounter(assertions, row, count);
                    LoopingScenarioTestContext testContext = new LoopingScenarioTestContext(parentTable.getTestContext());
                    ScriptTable t = createChild(testContext, parentTable, newTable);
                    parentTable.addChildTable(t, row);
                    assertions.addAll(t.getAssertions());
                    assertions.add(makeAssertion(Instruction.NOOP_INSTRUCTION, new LoopingScenarioTable.ScenarioExpectation(t, row)));
                    count++;
                }

            }
        }
        updateLoopCounter(assertions, row, null);
        return assertions;
    }

    private void updateLoopCounter(List<SlimAssertion> assertions, int row, Integer count) {
        SlimAssertion updateLoopCountAssertion = createUpdateLoopCountAssertion(table.getColumnCountInRow(row) - 1, row, count);
        assertions.add(updateLoopCountAssertion);
    }

    private SlimAssertion createUpdateLoopCountAssertion(int lastCol, int row, Integer count) {
        Instruction instruction = new AssignInstruction(null, "currentLoopCount", count);
        return makeAssertion(instruction, new RowExpectation(lastCol, row) {
            @Override
            protected SlimTestResult createEvaluationMessage(String actual, String expected) {
                return SlimTestResult.plain();
            }
        });
    }

    private ScriptTable createChild(LoopingScenarioTestContext testContext, SlimTable parentTable, Table newTable) throws TableCreationException {
        ScriptTable scriptTable;
        if (parentTable instanceof ScriptTable) {
            scriptTable = createChild((ScriptTable) parentTable, newTable, testContext);
        } else {
            scriptTable = createChild(getTestContext().getCurrentScriptClass(), newTable, testContext);
        }
        scriptTable.setCustomComparatorRegistry(customComparatorRegistry);
        return scriptTable;
    }

    protected ScriptTable createChild(ScriptTable parentScriptTable, Table newTable, SlimTestContext testContext) throws TableCreationException {
        return createChild(parentScriptTable.getClass(), newTable, testContext);
    }

    protected ScriptTable createChild(Class<? extends ScriptTable> parentTableClass, Table newTable, SlimTestContext testContext) throws TableCreationException {
        return SlimTableFactory.createTable(parentTableClass, newTable, id, testContext);
    }


    private final class ScenarioExpectation extends RowExpectation {
        private ScriptTable scriptTable;

        private ScenarioExpectation(ScriptTable scriptTable, int row) {
            super(-1, row); // We don't care about anything but the row.
            this.scriptTable = scriptTable;
        }

        @Override
        public TestResult evaluateExpectation(Object returnValue) {
            SlimTable parent = scriptTable.getParent();
            ExecutionResult testStatus = ((LoopingScenarioTestContext) scriptTable.getTestContext()).getExecutionResult();
            if (getOutputs().isEmpty() || testStatus != ExecutionResult.PASS) {
                // if the scenario has no output parameters
                // or the scenario failed
                // then the whole line should be flagged
                parent.getTable().updateContent(getRow(), new SlimTestResult(testStatus));
            }
            return null;
        }

        @Override
        protected SlimTestResult createEvaluationMessage(String actual, String expected) {
            return null;
        }
    }


    // This context is mainly used to determine if the scenario table evaluated successfully
    // This determines the execution result for the "calling" table row.
    public static final class LoopingScenarioTestContext implements SlimTestContext {

        private final SlimTestContext testContext;
        private final TestSummary testSummary = new TestSummary();

        LoopingScenarioTestContext(SlimTestContext testContext) {
            this.testContext = testContext;
        }

        @Override
        public String getSymbol(String symbolName) {
            return testContext.getSymbol(symbolName);
        }

        @Override
        public Map<String, String> getSymbols() {
            return testContext.getSymbols();
        }

        @Override
        public void setSymbol(String symbolName, String value) {
            testContext.setSymbol(symbolName, value);
        }

        @Override
        public void addScenario(String scenarioName, ScenarioTable scenarioTable) {
            testContext.addScenario(scenarioName, scenarioTable);
        }

        @Override
        public ScenarioTable getScenario(String scenarioName) {
            return testContext.getScenario(scenarioName);
        }

        @Override
        public ScenarioTable getScenarioByPattern(String invokingString) {
            return testContext.getScenarioByPattern(invokingString);
        }

        @Override
        public Collection<ScenarioTable> getScenarios() {
            return testContext.getScenarios();
        }

        @Override
        public void incrementPassedTestsCount() {
            increment(ExecutionResult.PASS);
        }

        @Override
        public void incrementFailedTestsCount() {
            increment(ExecutionResult.FAIL);
        }

        @Override
        public void incrementErroredTestsCount() {
            increment(ExecutionResult.ERROR);
        }

        @Override
        public void incrementIgnoredTestsCount() {
            increment(ExecutionResult.IGNORE);
        }

        @Override
        public void increment(ExecutionResult result) {
            testContext.increment(result);
            testSummary.add(result);
        }

        @Override
        public void increment(TestSummary summary) {
            testContext.increment(summary);
            testSummary.add(summary);
        }

        ExecutionResult getExecutionResult() {
            return ExecutionResult.getExecutionResult(testSummary);
        }

        @Override
        public TestPage getPageToTest() {
            return testContext.getPageToTest();
        }

        @Override
        public void setCurrentScriptClass(Class<? extends fitnesse.testsystems.slim.tables.ScriptTable> currentScriptClass) {
            testContext.setCurrentScriptClass(currentScriptClass);
        }

        @Override
        public Class<? extends fitnesse.testsystems.slim.tables.ScriptTable> getCurrentScriptClass() {
            return testContext.getCurrentScriptClass();
        }

        @Override
        public void setCurrentScriptActor(String currentScriptActor) {
            testContext.setCurrentScriptActor(currentScriptActor);
        }

        @Override
        public String getCurrentScriptActor() {
            return testContext.getCurrentScriptActor();
        }
    }
}
