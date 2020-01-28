package nl.praegus.fitnesse.responders.testHistory;
import org.junit.Test;

import java.io.File;


public class TestHistoryTest {


    @Test
    public void when_directory_is_null_the_history_lines_are_empty() {
        File mockDir = new File("");
        TestHistory testHistory = new TestHistory(mockDir);
        assert (testHistory.getHistoryLines()).isEmpty();
    }
    @Test
    public void When_directory_is_not_null_return_history_lines_unsorted(){
        File mockDir = new File("test/resources/TestResultDirectory");
        TestHistory testHistory = new TestHistory(mockDir);
        System.out.println(testHistory.getHistoryLines());
    }
}