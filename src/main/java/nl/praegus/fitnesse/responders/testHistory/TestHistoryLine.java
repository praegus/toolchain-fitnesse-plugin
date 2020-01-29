package nl.praegus.fitnesse.responders.testHistory;

//import com.sun.tools.javac.util.List;

import fitnesse.reporting.history.PageHistory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestHistoryLine {

    private String page;
    private int numberOfTimesPassed;
    private int numberOfTimesFailed;
    private LocalDateTime MostRecentRunDate;
    private PageHistory pageHistory;
    //private List<Boolean> latestResults;

    //Get+Set
    public TestHistoryLine(String page, int numberOfTimesFailed, int numberOfTimesPassed, LocalDateTime MostRecentRunDate, PageHistory pageHistory) {
        this.page = page;
        this.numberOfTimesFailed = numberOfTimesFailed;
        this.numberOfTimesPassed = numberOfTimesPassed;
        this.MostRecentRunDate = MostRecentRunDate;
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

    public LocalDateTime getMostRecentRunDate() {

        return MostRecentRunDate;
    }

    public PageHistory getPageHistory() {
        return pageHistory;
    }

    public String getFormattedDate() {
        return MostRecentRunDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"));
    }
}
/*
class Result {
    private Boolean result;
    private LocalDateTime resultMoment;
}
*/