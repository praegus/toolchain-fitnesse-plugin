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

//
//    //Get+Set
//    public TestHistoryLine(String pageName, int numberOfTimesFailed, int numberOfTimesPassed, LocalDateTime MostRecentRunDate, PageHistory pageHistory) {
//        this.pageName = pageName;
//        this.numberOfTimesFailed = numberOfTimesFailed;
//        this.numberOfTimesPassed = numberOfTimesPassed;
//        this.MostRecentRunDate = MostRecentRunDate;
//        this.pageHistory = pageHistory;
//    }

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

//    public PageHistory.PassFailReport[] getPageHistory() {
//        return pageHistory;
//    }
////
//    public String getMostRecentRunDateFormatted() {
//        return MostRecentRunDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"));
//    }
}
