package nl.praegus.fitnesse.responders.testHistory;

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
        assert (testHistory.getHistoryLines()).isEmpty();
    }

    @Test
    public void When_directory_is_not_null_return_historylines_sorted() {

        TestHistory testHistory = new TestHistory(getMockDir("TestResultDirectory"));

        assertThat(testHistory.getHistoryLines()).extracting("page").containsExactly(
                "TestSuiteDemo.BackEndTests.T002RetrieveDataFromXas",
                "TestSuiteDemo.FrontEndTests.T003CreateCourse",
                "FitNesse.UserGuide.TwoMinuteExample");
    }
}