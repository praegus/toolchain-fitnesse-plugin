package nl.praegus.fitnesse.responders.testHistory;

import fitnesse.reporting.history.PageHistory;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
public class TestHistoryTest {

    private File getMockDir(String DirName){
        if (DirName == ""){
            File mockDir = new File("");
            return mockDir;
        }else {
            File mockDir = new File(getClass().getClassLoader().getResource(DirName).getFile());
            return mockDir;
        }
    }

    @Test
    public void when_directory_is_null_the_history_lines_are_empty() {
        TestHistory testHistory = new TestHistory(getMockDir(""));
        //make sure gethistorylines return empty
        assert (testHistory.getHistoryLines()).isEmpty();
    }

    @Test
    public void When_directory_is_not_null_return_historylines_sorted() {
        TestHistory testHistory = new TestHistory(getMockDir("TestResultDirectory"));
        // compare sorted lists to make sure lists are sorted correctly
        assertThat(testHistory.getHistoryLines()).extracting("page").containsExactly(
                "TestSuiteDemo.BackEndTests.T002RetrieveDataFromXas",
                "TestSuiteDemo.FrontEndTests.T003CreateCourse",
                "FitNesse.UserGuide.TwoMinuteExample");
    }

    @Test
    public void When_dateTimeFormatter_is_working_correctly() {
        TestHistory testHistory = new TestHistory(getMockDir("TestResultDirectory"));

        assertThat(testHistory.getHistoryLines().get(0).getFormattedDate()).isEqualTo("22 Jan 2020, 11:43");
    }

    @Test
    public void Test_results_passfails_in_right_order() {
        PageHistory pageHistory = new PageHistory((getMockDir("TestResultDirectory/FitNesse.UserGuide.TwoMinuteExample")));
        PageHistory.PassFailReport[] passfails = pageHistory.getBarGraph().passFailArray();

        assertThat(passfails).extracting("date").containsExactly(
                "20200121091855",
                "20200121091824"
        );
    }

}