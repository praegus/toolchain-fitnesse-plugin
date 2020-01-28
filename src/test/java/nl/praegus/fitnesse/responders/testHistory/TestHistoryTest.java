package nl.praegus.fitnesse.responders.testHistory;
import fitnesse.reporting.history.PageHistory;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TestHistoryTest {


    @Test
    public void test() {
        TestHistory testHistory = new TestHistory();
        testHistory.getPageNames();

        String[] pagenamesarray = testHistory.getPageNames().toArray(new String[0]);
        List<TestHistoryLine> testHistoryLineList = new ArrayList();
        TestHistory testhistory = new TestHistory();
        // loop for each name in pagenames array
        for (int i=0; i<pagenamesarray.length; i++){
            //populate data for testhistoryline object
            int totalOfFailures = testHistory.getPageHistory(pagenamesarray[i]).getFailures();
            int totalOfPasses = testHistory.getPageHistory(pagenamesarray[i]).getPasses();
            LocalDateTime whatDate = testHistory.getPageHistory(pagenamesarray[i]).getMaxDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            PageHistory history = testHistory.getPageHistory(pagenamesarray[i]);
            String formattedDate = whatDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"));
            //make new historyline object and add to list
            testHistoryLineList.add(new TestHistoryLine(String.valueOf(pagenamesarray[i]),
                    totalOfFailures,
                    totalOfPasses,
                    whatDate,
                    history,
                    formattedDate
            ));
        }
        assertEquals( testhistory.getSortedLines(testHistoryLineList) ,testHistory.getHistoryLineList());


    }
}