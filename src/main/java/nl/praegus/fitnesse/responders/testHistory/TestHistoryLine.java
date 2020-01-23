package nl.praegus.fitnesse.responders.testHistory;

//import com.sun.tools.javac.util.List;

import java.time.LocalDateTime;

public class TestHistoryLine {
    private String page;
    private int numberOfTimesPassed;
    private int numberOfTimesFailed;
    //private List<Boolean> latestResults;

    //Get+Set
    public TestHistoryLine() {
        this.page = page;
        this.numberOfTimesFailed = numberOfTimesFailed;
        this.numberOfTimesPassed = numberOfTimesPassed;
    }

    public String getPage() {
        return this.page;
    }

    public int getNumberOfTimesFailed() {
        return numberOfTimesFailed;
    }

    public int getNumberOfTimesPassed() {
        return numberOfTimesPassed;
    }
}
class Result {
    private Boolean result;
    private LocalDateTime resultMoment;
}
