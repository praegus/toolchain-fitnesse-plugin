package nl.praegus.fitnesse.responders.testHistory;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TestHistoryTest {

    @Test
    public void when_directory_is_null_the_history_lines_are_empty() {
        TestHistory testHistory = new TestHistory(new File(""));

        List<TestHistoryLine> receivedResult = testHistory.getHistoryLines();

        assertThat(receivedResult).isEmpty();
    }

    @Test
    public void When_directory_is_not_null_return_historylines_sorted() {
        TestHistory testHistory = new TestHistory(getMockDir("TestResultDirectory"));
        List<String> expectedResult = Arrays.asList("TestSuiteDemo.BackEndTests.T002RetrieveDataFromXas", "TestSuiteDemo.FrontEndTests.T003CreateCourse", "FitNesse.UserGuide.TwoMinuteExample");

        List<TestHistoryLine> receivedResult = testHistory.getHistoryLines();

        assertThat(receivedResult).extracting("pageName").containsSequence(expectedResult);
    }

    @Test
    public void When_directory_is_not_null_return_historylines_not_sorted() {
        TestHistory testHistory = new TestHistory(getMockDir("TestResultDirectory"));
        List<String> expectedResult = Arrays.asList("TestSuiteDemo.FrontEndTests.T003CreateCourse", "TestSuiteDemo.BackEndTests.T002RetrieveDataFromXas", "FitNesse.UserGuide.TwoMinuteExample");

        Set<String> receivedResult = testHistory.getPageNames();

        assertThat(receivedResult).containsSequence(expectedResult);
    }

    @Test
    public void When_dateTimeFormatter_is_working_correctly() {
        TestHistory testHistory = new TestHistory(getMockDir("TestResultDirectory"));
        String expectedResult = "Wed Jan 22 11:43:40 CET 2020";

        String receivedResult = testHistory.getHistoryLines().get(0).getMostRecentRunDate().toString();

        assertThat(receivedResult).isEqualTo(expectedResult);
    }

    @Test
    public void When_dateTimeFormatter_is_not_working_correctly(){
        TestHistory testHistory = new TestHistory(getMockDir("TestResultDirectory"));
        String expectedNotEqual = "Wed Jan 22 11:43:40 CET 2020";
        String expectedContains = "nl.praegus.fitnesse.responders.testHistory.TestHistoryLine@";

        String receivedResult = testHistory.getHistoryLines().toString();

        assertThat(receivedResult).isNotEqualTo(expectedNotEqual);
        assertThat(receivedResult).contains(expectedContains);
    }

    @Test
    public void Checks_number_of_times_failed(){
        TestHistory testHistory = new TestHistory(getMockDir("TestResultDirectory"));

        int receivedResult = testHistory.getHistoryLines().get(0).getNumberOfTimesFailed();

        assertThat(receivedResult).isEqualTo(1);
    }

    @Test
    public void Checks_number_of_times_passed(){
        TestHistory testHistory = new TestHistory(getMockDir("TestResultDirectory"));

        int receivedResult = testHistory.getHistoryLines().get(0).getNumberOfTimesPassed();

        assertThat(receivedResult).isEqualTo(0);
    }


    // Setup method
    private File getMockDir(String DirName){
            File mockDir = new File(getClass().getClassLoader().getResource(DirName).getFile());
            return mockDir;

    }

}