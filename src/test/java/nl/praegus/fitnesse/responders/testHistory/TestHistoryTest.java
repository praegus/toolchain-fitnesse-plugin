package nl.praegus.fitnesse.responders.testHistory;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class TestHistoryTest {

    @Test
    public void when_directory_is_null_the_history_lines_are_empty() {
        TestHistory testHistory = new TestHistory(new File(""));
        assertThat(testHistory.getHistoryLines()).isEmpty();
    }

    @Test
    public void When_directory_is_not_null_return_historylines_sorted() {
        TestHistory testHistory = new TestHistory(getMockDir("TestResultDirectory"));
        // Compare sorted lists to make sure lists are sorted correctly
        assertThat(testHistory.getHistoryLines()).extracting("pageName").containsExactly(
                "TestSuiteDemo.BackEndTests.T002RetrieveDataFromXas",
                "TestSuiteDemo.FrontEndTests.T003CreateCourse",
                "FitNesse.UserGuide.TwoMinuteExample");
    }

    @Test
    public void When_dateTimeFormatter_is_working_correctly() {
        TestHistory testHistory = new TestHistory(getMockDir("TestResultDirectory"));
        assertThat(testHistory.getHistoryLines().get(0).getMostRecentRunDate().toString()).isEqualTo("Wed Jan 22 11:43:40 CET 2020");
    }

    @Test
    public void Checks_number_of_times_failed(){
        TestHistory testHistory = new TestHistory(getMockDir("TestResultDirectory"));
        assertThat(testHistory.getHistoryLines().get(0).getNumberOfTimesFailed()).isEqualTo(1);
    }

    @Test
    public void Checks_number_of_times_passed(){
        TestHistory testHistory = new TestHistory(getMockDir("TestResultDirectory"));
        assertThat(testHistory.getHistoryLines().get(0).getNumberOfTimesPassed()).isEqualTo(0);
    }

    private File getMockDir(String DirName){
        if (DirName == "") {
            File mockDir = new File("");
            return mockDir;
        } else {
            File mockDir = new File(getClass().getClassLoader().getResource(DirName).getFile());
            return mockDir;
        }
    }

}