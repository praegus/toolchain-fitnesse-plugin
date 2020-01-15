package nl.praegus.fitnesse.slim.tables;

import fitnesse.slim.instructions.Instruction;
import fitnesse.testsystems.*;
import fitnesse.testsystems.slim.SlimTestContext;
import fitnesse.testsystems.slim.Table;
import fitnesse.testsystems.slim.results.SlimTestResult;
import fitnesse.testsystems.slim.tables.*;
import org.apache.commons.lang3.StringUtils;

import java.util.*;


public class ConditionalScenarioTable extends ScenarioTable {

    public ConditionalScenarioTable(Table table, String tableId, SlimTestContext testContext) {
        super(table, tableId, testContext);
    }

    @Override
    protected String getTableType() {
        return "conditionalScenarioTable";
    }

    public List<SlimAssertion> call(final Map<String, String> scenarioArguments,
                                    SlimTable parentTable, int row) throws TestExecutionException {
        Table newTable = getTable().asTemplate(content -> {
            for (Map.Entry<String, String> scenarioArgument : scenarioArguments.entrySet()) {
                String arg = scenarioArgument.getKey();
                if (getInputs().contains(arg)) {
                    String argument = scenarioArguments.get(arg);
                    content = StringUtils.replace(content, "@" + arg, argument);
                    content = StringUtils.replace(content, "@{" + arg + "}", argument);
                } else {
                    throw new SyntaxError(String.format("The argument %s is not an input to the scenario.", arg));
                }
            }
            return content;
        });

        ConditionalScenarioTestContext testContext = new ConditionalScenarioTestContext(parentTable.getTestContext());
        ScriptTable t = createChild(testContext, newTable);
        parentTable.addChildTable(t, row);
        List<SlimAssertion> assertions = t.getAssertions();
        assertions.add(makeAssertion(Instruction.NOOP_INSTRUCTION, new ScenarioExpectation(t, row)));
        return assertions;
    }

    private ConditionalScriptTable createChild(ConditionalScenarioTestContext testContext, Table newTable) {
        ConditionalScriptTable scriptTable;
        scriptTable = createChild(newTable, testContext);
        scriptTable.setCustomComparatorRegistry(customComparatorRegistry);
        return scriptTable;
    }

    private ConditionalScriptTable createChild(Table newTable, SlimTestContext testContext) {
        return new ConditionalScriptTable(newTable, id, testContext, true);
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
            ExecutionResult testStatus = ((ConditionalScenarioTestContext) scriptTable.getTestContext()).getExecutionResult();
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
    public static final class ConditionalScenarioTestContext implements SlimTestContext {

        private final SlimTestContext testContext;
        private final TestSummary testSummary = new TestSummary();

        ConditionalScenarioTestContext(SlimTestContext testContext) {
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
