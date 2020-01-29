package nl.praegus.fitnesse.responders.testHistory;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.*;
public class TestHistoryTest {


    @Test
    public void when_directory_is_null_the_history_lines_are_empty() {
        File mockDir = new File("");
        TestHistory testHistory = new TestHistory(mockDir);
        assert (testHistory.getHistoryLines()).isEmpty();
    }
    @Test
    public void When_directory_is_not_null_return_history_linessorted(){
        // get mock test result directory
        ClassLoader classLoader = getClass().getClassLoader();
        File mockDir = new File(classLoader.getResource("TestResultDirectory").getFile());
        //get testhistory lines from mock directory
        TestHistory testHistory = new TestHistory(mockDir);
        List<TestHistoryLine> historyLineList = testHistory.getHistoryLines();
        // assert that
        assertThat(historyLineList.stream().map(TestHistoryLine::getPage).collect(Collectors.toList()))
                .isEqualTo(Arrays.asList("TestSuiteDemo.BackEndTests.T002RetrieveDataFromXas","TestSuiteDemo.FrontEndTests.T003CreateCourse","FitNesse.UserGuide.TwoMinuteExample"));
    }
}