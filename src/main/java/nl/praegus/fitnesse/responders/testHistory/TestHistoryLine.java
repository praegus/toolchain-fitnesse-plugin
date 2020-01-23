package nl.praegus.fitnesse.responders.testHistory;

//import com.sun.tools.javac.util.List;

import fitnesse.reporting.history.PageHistory;

import java.time.LocalDateTime;
import java.util.List;

public class TestHistoryLine {

    private String page;
    private int numberOfTimesPassed;
    private int numberOfTimesFailed;
    private LocalDateTime lastRun;
    private PageHistory.BarGraph passFailArray;
    //private List<Boolean> latestResults;

    //Get+Set
    public TestHistoryLine(String page, int numberOfTimesFailed, int numberOfTimesPassed, LocalDateTime lastRun, PageHistory.BarGraph passFailArray) {
        this.page = page;
        this.numberOfTimesFailed = numberOfTimesFailed;
        this.numberOfTimesPassed = numberOfTimesPassed;
        this.lastRun = lastRun;
        this.passFailArray = passFailArray;
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

    public PageHistory.BarGraph getPassFailArray(){
        return passFailArray;
    }
}
/*
class Result {
    private Boolean result;
    private LocalDateTime resultMoment;
}
*/