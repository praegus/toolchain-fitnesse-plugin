package nl.praegus.fitnesse.responders.testHistory;

//import com.sun.tools.javac.util.List;

import fitnesse.reporting.history.PageHistory;
import java.time.LocalDateTime;

public class TestHistoryLine {

    private String page;
    private int numberOfTimesPassed;
    private int numberOfTimesFailed;
    private LocalDateTime lastRun;
    private String formattedDate;
    private PageHistory pageHistory;
    //private List<Boolean> latestResults;

    //Get+Set
    public TestHistoryLine(String page, int numberOfTimesFailed, int numberOfTimesPassed, LocalDateTime lastRun, PageHistory pageHistory,String formattedDate) {
        this.page = page;
        this.numberOfTimesFailed = numberOfTimesFailed;
        this.numberOfTimesPassed = numberOfTimesPassed;
        this.lastRun = lastRun;
        this.pageHistory = pageHistory;
        this.formattedDate = formattedDate;
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

    public LocalDateTime getLastRun() {

        return lastRun;
    }

    public PageHistory getPageHistory() {
        return pageHistory;
    }

    public String getFormattedDate() {
        return formattedDate;
    }
}
/*
class Result {
    private Boolean result;
    private LocalDateTime resultMoment;
}
*/