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
        //get testhistory lines from mock directory
        TestHistory testHistory = new TestHistory(mockDir);

        System.out.println();
    }
    @Test
    public void When_dateTimeFormatter_is_working_correctly (){
        ClassLoader loader = Test.class.getClassLoader();
        File mockDir = new File(loader.getResource("TestResultDirectory").getFile());
        TestHistory testHistory = new TestHistory(mockDir);
        List<TestHistoryLine> testHistoryList = testHistory.getHistoryLines();

        List<LocalDateTime> unformattedDates = testHistoryList.stream().map(TestHistoryLine::getLastRun).collect(Collectors.toList());
        LocalDateTime unformattedDate = unformattedDates.get(0);

        List<String> formattedDatesMock = testHistoryList.stream().map(TestHistoryLine::getFormattedDate).collect(Collectors.toList());
        String formattedDateTest = unformattedDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"));

        assertThat(formattedDateTest).isEqualTo(formattedDatesMock.get(0));
        System.out.println();
    }

}