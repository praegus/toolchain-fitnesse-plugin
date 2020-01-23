package nl.praegus.fitnesse.responders.testHistory;

//import com.sun.tools.javac.util.List;

import java.time.LocalDateTime;

public class TestHistoryLine {
    private String page;
    private int numberOfTimesPassed;
    private int numberOfTimesFailed;
    //private List<Boolean> latestResults;

    //Get+Set Page String
    public String getPage() {
        return this.page;
    }
    public void SetPage() {
        this.page = page;
    }
    //Get+Set int getNumberOfTimesFailed
    public int getNumberOfTimesFailed() {
        return numberOfTimesFailed;
    }
    public void setNumberOfTimesFailed(int numberOfTimesFailed) {
        this.numberOfTimesFailed = numberOfTimesFailed;
    }
    //Get+Set int getNumberOfTimesPassed
    public int getNumberOfTimesPassed() {
        return numberOfTimesPassed;
    }
    public void setNumberOfTimesPassed(int numberOfTimesPassed) {
        this.numberOfTimesPassed = numberOfTimesPassed;
    }
}
class Result {
    private Boolean result;
    private LocalDateTime resultMoment;
}
