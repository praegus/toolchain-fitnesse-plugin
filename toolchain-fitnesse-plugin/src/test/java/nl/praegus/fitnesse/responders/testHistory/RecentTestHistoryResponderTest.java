package nl.praegus.fitnesse.responders.testHistory;
import org.junit.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RecentTestHistoryResponderTest {

    @Test
    public void when_directory_is_null_the_history_lines_are_empty() {
        RecentTestHistory recentTestHistory = new RecentTestHistory(new File(""),"false");

        List<TestHistoryLine> receivedResult = recentTestHistory.getHistoryLines();

        assertThat(receivedResult).isEmpty();
    }

    @Test
    public void When_directory_is_not_null_return_historylines_sorted() {
        RecentTestHistory recentTestHistory = new RecentTestHistory(getMockDir("TestResultDirectory"),"false");
        List<String> expectedResult = Arrays.asList("Example.Mocker3.SuiteSetUp", "ExampleTest.Mocker1.thisIsATest");

        List<TestHistoryLine> receivedResult = recentTestHistory.getHistoryLines();

        assertThat(receivedResult).extracting("pageName").containsSequence(expectedResult);
    }

    @Test
    public void When_directory_is_not_null_return_historylines_not_sorted() {
        RecentTestHistory recentTestHistory = new RecentTestHistory(getMockDir("TestResultDirectory"),"false");
        List<String> expectedResult = Arrays.asList("Example.Mocker3.SuiteSetUp","ExampleTest.Mocker1.thisIsATest","Example.Mocker3.SuiteTearDown","Example.Mocker3.SetUp","Example.Mocker3.dataForTesting","Example.Mocker3.TearDown","ExampleTest.Mocker2.thisIsMockData");

        Set<String> receivedResult = recentTestHistory.getPageNames();

        assertThat(receivedResult).containsSequence(expectedResult);
    }

    @Test
    public void when_the_recent_test_history_is_retrieved_the_recent_run_date_is_parsed_correctly() {
        RecentTestHistory recentTestHistory = new RecentTestHistory(getMockDir("TestResultDirectory"),"false");

        LocalDateTime expectedResult = LocalDateTime.parse("2020-01-22T11:08:56");

        assertThat(recentTestHistory.getHistoryLines().get(0).getMostRecentRunDate()).isEqualTo(expectedResult);
    }

    @Test
    public void Checks_number_of_times_failed(){
        RecentTestHistory recentTestHistory = new RecentTestHistory(getMockDir("TestResultDirectory"),"false");

        assertThat(recentTestHistory.getHistoryLines().get(0).getNumberOfTimesFailed()).isEqualTo(1);
    }

    @Test
    public void Checks_number_of_times_passed(){
        RecentTestHistory recentTestHistory = new RecentTestHistory(getMockDir("TestResultDirectory"),"false");

        assertThat(recentTestHistory.getHistoryLines().get(0).getNumberOfTimesPassed()).isEqualTo(0);
    }
    @Test
    public void Checks_if_filter_is_off(){
        RecentTestHistory recentTestHistory = new RecentTestHistory(getMockDir("TestResultDirectory"),"false");
        List<String> expectedResult = Arrays.asList("Example.Mocker3.SuiteSetUp","Example.Mocker3.SuiteTearDown","Example.Mocker3.SetUp","Example.Mocker3.TearDown");

        Set<String> receivedResult = recentTestHistory.getPageNames();

        assertThat(receivedResult).containsAnyElementsOf(expectedResult);
    }
    @Test
    public void Checks_if_filter_is_on(){
/*
        RecentTestHistory recentTestHistory = new RecentTestHistory(getMockDir("TestResultDirectory"),"true");
        List<String> expectedResult = Arrays.asList("Example.Mocker3.SuiteSetUp","Example.Mocker3.SuiteTearDown","Example.Mocker3.SetUp","Example.Mocker3.TearDown");

        Set<String> receivedResult = recentTestHistory.getPageNames();

        assertThat(receivedResult).doesNotContainAnyElementsOf(expectedResult);

*/
    }

    // Setup method
    private File getMockDir(String DirName){
            File mockDir = new File(getClass().getClassLoader().getResource(DirName).getFile());
            return mockDir;
    }

}