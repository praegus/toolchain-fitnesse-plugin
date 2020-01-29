package nl.praegus.fitnesse.responders.testHistory;

import fitnesse.reporting.history.PageHistory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TestHistoryLine {

    private String pageName;
    private int numberOfTimesPassed;
    private int numberOfTimesFailed;
    private LocalDateTime MostRecentRunDate;
//    private PageHistory pageHistory;

    public TestHistoryLine(PageHistory pageHistory){
        this.numberOfTimesFailed = pageHistory.getFailures();
        this.numberOfTimesPassed = pageHistory.getPasses();
        LocalDateTime mostRecentRunDate = pageHistory.getMaxDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
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

    public LocalDateTime getMostRecentRunDate() {

        return MostRecentRunDate;
    }

    public PageHistory getPageHistory() {
        return pageHistory;
    }

    public String getMostRecentRunDateFormatted() {
        return MostRecentRunDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"));
    }
}
