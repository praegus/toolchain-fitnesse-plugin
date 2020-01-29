package nl.praegus.fitnesse.responders.testHistory;

import org.junit.Test;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.*;
import java.io.File;
import java.util.*;

import java.util.stream.Collectors;

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

    @Test
    public void When_dateTimeFormatter_is_working_correctly (){
        TestHistory testHistory = new TestHistory(getMockDir("TestResultDirectory"));
        List<TestHistoryLine> testHistoryList = testHistory.getHistoryLines();

        List<LocalDateTime> unformattedDates = testHistoryList.stream().map(TestHistoryLine::getLastRun).collect(Collectors.toList());
        LocalDateTime unformattedDate = unformattedDates.get(0);

        List<String> formattedDatesMock = testHistoryList.stream().map(TestHistoryLine::getFormattedDate).collect(Collectors.toList());
        String formattedDateTest = unformattedDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"));

        assertThat(formattedDateTest).isEqualTo(formattedDatesMock.get(0));
        System.out.println();
    }

}