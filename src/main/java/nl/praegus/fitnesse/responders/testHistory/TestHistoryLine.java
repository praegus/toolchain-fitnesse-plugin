package nl.praegus.fitnesse.responders.testHistory;

//import com.sun.tools.javac.util.List;

import fitnesse.reporting.history.PageHistory;
import java.time.LocalDateTime;

public class TestHistoryLine {

    private String page;
    private int numberOfTimesPassed;
    private int numberOfTimesFailed;
    private LocalDateTime selectMostRecentDate;
    private PageHistory pageHistory;
    //private List<Boolean> latestResults;

    //Get+Set
    public TestHistoryLine(String page, int numberOfTimesFailed, int numberOfTimesPassed, LocalDateTime selectMostRecentDate, PageHistory pageHistory) {
        this.page = page;
        this.numberOfTimesFailed = numberOfTimesFailed;
        this.numberOfTimesPassed = numberOfTimesPassed;
        this.selectMostRecentDate = selectMostRecentDate;
        this.pageHistory = pageHistory;
    }

    public String getPage() {

        return page;
    }
    //Get+Set int getNumberOfTimesFailed
    public int getNumberOfTimesFailed() {

        return numberOfTimesFailed;
    }
    public int getNumberOfTimesPassed() {

        return numberOfTimesPassed;
    }

    public LocalDateTime getSelectMostRecentDate() {

        return selectMostRecentDate;
    }

    public PageHistory getPageHistory() {
        return pageHistory;
    }

    public String getFormattedDate() {
        return selectMostRecentDate.format();
    }
}
/*
class Result {
    private Boolean result;
    private LocalDateTime resultMoment;
}
*/