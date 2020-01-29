package nl.praegus.fitnesse.responders.testHistory;

import fitnesse.reporting.history.PageHistory;

import java.util.Date;

public class TestHistoryLine {

    private String pageName;
    private int numberOfTimesPassed;
    private int numberOfTimesFailed;
    private Date MostRecentRunDate;
    private PageHistory.BarGraph barGraph;

    public TestHistoryLine(PageHistory pageHistory){
        this.pageName = pageHistory.getFullPageName();
        this.numberOfTimesFailed = pageHistory.getFailures();
        this.numberOfTimesPassed = pageHistory.getPasses();
        this.MostRecentRunDate = pageHistory.getMaxDate();
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

    public Date getMostRecentRunDate() {

        return MostRecentRunDate;
    }

    public PageHistory.BarGraph getbarGraph(){

        return barGraph;
    }

}
