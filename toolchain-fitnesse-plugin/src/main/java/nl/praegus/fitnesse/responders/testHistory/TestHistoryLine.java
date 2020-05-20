package nl.praegus.fitnesse.responders.testHistory;

import fitnesse.reporting.history.PageHistory;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class TestHistoryLine {

    private String pageName;
    private int numberOfTimesPassed;
    private int numberOfTimesFailed;
    private LocalDateTime MostRecentRunDate;
    private PageHistory.BarGraph barGraph;

    public TestHistoryLine(PageHistory pageHistory){
        this.pageName = pageHistory.getFullPageName();
        this.numberOfTimesFailed = pageHistory.getFailures();
        this.numberOfTimesPassed = pageHistory.getPasses();
        this.MostRecentRunDate = pageHistory.getMaxDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.barGraph = pageHistory.getBarGraph();
    }

    public String getPageName() {
        return pageName;
    }

    public int getNumberOfTimesFailed() {

        return numberOfTimesFailed;
    }

    public int getNumberOfTimesPassed() {

        return numberOfTimesPassed;
    }

    public LocalDateTime getMostRecentRunDate() {

        return MostRecentRunDate;
    }

    public PageHistory.BarGraph getBarGraph(){

        return barGraph;
    }

}
