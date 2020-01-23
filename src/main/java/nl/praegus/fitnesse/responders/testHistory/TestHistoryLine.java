package nl.praegus.fitnesse.responders.testHistory;

//import com.sun.tools.javac.util.List;

import java.time.LocalDateTime;

public class TestHistoryLine {
    private String page;
    private int numberOfTimesPassed;
    private int numberOfTimesFailed;
    private LocalDateTime lastRun;
    //private List<Boolean> latestResults;

    //Get+Set
    public TestHistoryLine(String page, int numberOfTimesFailed, int numberOfTimesPassed, LocalDateTime lastRun) {
        this.page = page;
        this.numberOfTimesFailed = numberOfTimesFailed;
        this.numberOfTimesPassed = numberOfTimesPassed;
        this.lastRun = lastRun;

    }

    public String getPage() {

        return this.page;
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
}
/*
class Result {
    private Boolean result;
    private LocalDateTime resultMoment;
}
*/