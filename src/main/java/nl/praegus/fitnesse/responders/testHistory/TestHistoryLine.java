package nl.praegus.fitnesse.responders.testHistory;

import com.sun.tools.javac.util.List;

import java.time.LocalDateTime;

public class TestHistoryLine {
    private String page;
    private int numberOfTimesPassed;
    private int numberOfTimesFailed;
    private List<Boolean> latestResults;
}

class Result {
    private Boolean result;
    private LocalDateTime resultMoment;
}
