package nl.praegus.fitnesse.responders.testHistory;
import org.junit.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static org.assertj.core.api.Assertions.assertThat;

public class RecentTestHistoryResponderTest {

    @Test
    public void when_directory_is_null_the_history_lines_are_empty() {
        RecentTestHistory recentTestHistory = new RecentTestHistory(new File(""));

        List<TestHistoryLine> receivedResult = recentTestHistory.getHistoryLines();

        assertThat(receivedResult).isEmpty();
    }

    @Test
    public void When_directory_is_not_null_return_historylines_sorted() {
        RecentTestHistory recentTestHistory = new RecentTestHistory(getMockDir("TestResultDirectory"));
        List<String> expectedResult = Arrays.asList("ExampleTest.Mocker2.thisIsMockData", "Example.Mocker3.dataForTesting", "ExampleTest.Mocker1.thisIsATest");

        List<TestHistoryLine> receivedResult = recentTestHistory.getHistoryLines();

        assertThat(receivedResult).extracting("pageName").containsSequence(expectedResult);
    }

    @Test
    public void When_directory_is_not_null_return_historylines_not_sorted() {
        RecentTestHistory recentTestHistory = new RecentTestHistory(getMockDir("TestResultDirectory"));
        List<String> expectedResult = Arrays.asList("ExampleTest.Mocker1.thisIsATest", "Example.Mocker3.dataForTesting", "ExampleTest.Mocker2.thisIsMockData");

        Set<String> receivedResult = recentTestHistory.getPageNames();

        assertThat(receivedResult).containsSequence(expectedResult);
    }

    @Test
    public void checks_if_correct_date_is_shown() {
        RecentTestHistory recentTestHistory = new RecentTestHistory(getMockDir("TestResultDirectory"));

        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        isoFormat.setTimeZone(TimeZone.getDefault());

        LocalDateTime expectedResult = LocalDateTime.parse("2020-01-22T11:43:40");

        LocalDateTime receivedResult = recentTestHistory.getHistoryLines().get(0).getMostRecentRunDate();
        assertThat(receivedResult).isEqualTo(expectedResult);
    }

    @Test
    public void Checks_number_of_times_failed(){
        RecentTestHistory recentTestHistory = new RecentTestHistory(getMockDir("TestResultDirectory"));

        int receivedResult = recentTestHistory.getHistoryLines().get(0).getNumberOfTimesFailed();

        assertThat(receivedResult).isEqualTo(1);
    }

    @Test
    public void Checks_number_of_times_passed(){
        RecentTestHistory recentTestHistory = new RecentTestHistory(getMockDir("TestResultDirectory"));

        int receivedResult = recentTestHistory.getHistoryLines().get(0).getNumberOfTimesPassed();

        assertThat(receivedResult).isEqualTo(0);
    }

    // Setup method
    private File getMockDir(String DirName){
            File mockDir = new File(getClass().getClassLoader().getResource(DirName).getFile());
            return mockDir;
    }

}