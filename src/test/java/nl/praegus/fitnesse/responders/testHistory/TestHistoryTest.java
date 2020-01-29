package nl.praegus.fitnesse.responders.testHistory;

import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
public class TestHistoryTest {


    @Test
    public void when_directory_is_null_the_history_lines_are_empty() {
        File mockDir = new File("");
        TestHistory testHistory = new TestHistory(mockDir);
        assert (testHistory.getHistoryLines()).isEmpty();
    }

    @Test
    public void When_directory_is_not_null_return_historylines_sorted() {
        File mockDir = new File(getClass().getClassLoader().getResource("TestResultDirectory").getFile());

        TestHistory testHistory = new TestHistory(mockDir);

        assertThat(testHistory.getHistoryLines()).extracting("page").containsExactly(
                "TestSuiteDemo.BackEndTests.T002RetrieveDataFromXas",
                "TestSuiteDemo.FrontEndTests.T003CreateCourse",
                "FitNesse.UserGuide.TwoMinuteExample");
    }

    @Test
    public void When_directory_is_not_null_return_historylines_unsorted() {
        ClassLoader classLoader = getClass().getClassLoader();
        File mockDir = new File(classLoader.getResource("TestResultDirectory").getFile());
        TestHistory testHistory = new TestHistory(mockDir);

        System.out.println();
    }
}