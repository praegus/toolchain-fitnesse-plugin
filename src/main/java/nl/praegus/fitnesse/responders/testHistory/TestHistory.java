package nl.praegus.fitnesse.responders.testHistory;

import fitnesse.reporting.history.PageHistory;
import fitnesse.wiki.PathParser;
import util.FileUtil;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.toList;

public class TestHistory {
    private List<TestHistoryLine> testHistoryLineList = new ArrayList<>();

    final Map<String, File> pageDirectoryMap = new HashMap<>();

    public TestHistory(File historyDirectory) {
        readHistoryDirectory(historyDirectory);
    }

    public TestHistory(File historyDirectory, String pageName) {
        readPageHistoryDirectory(historyDirectory, pageName);
    }

    public TestHistory() {

    }


    public Set<String> getPageNames() {
        return new TreeSet<>(pageDirectoryMap.keySet());
    }

    public PageHistory getPageHistory(String pageName) {
        File pageHistoryDirectory = pageDirectoryMap.get(pageName);
        if (pageHistoryDirectory == null)
            return null;
        else {
            PageHistory pageHistory = new PageHistory(pageHistoryDirectory);
            if (pageHistory.size() == 0)
                return null;
            else
                return pageHistory;
        }
    }

    public static List<TestHistoryLine> getSortedLines(List<TestHistoryLine> LineList) {
        // sort list using stream and return it
        return LineList.stream()
                .sorted(comparing(TestHistoryLine::getLastRun, nullsLast(reverseOrder())))
                .collect(toList());
    }

    public List<TestHistoryLine> getHistoryLineList(){
        //inits
        String[] pagenamesarray = getPageNames().toArray(new String[0]);
        List<TestHistoryLine> testHistoryLineList = new ArrayList();
        TestHistory testhistory = new TestHistory();
        // loop for each name in pagenames array
        for (int i=0; i<pagenamesarray.length; i++){
            //populate data for testhistoryline object
            int totalOfFailures = getPageHistory(pagenamesarray[i]).getFailures();
            int totalOfPasses = getPageHistory(pagenamesarray[i]).getPasses();
            LocalDateTime whatDate = getPageHistory(pagenamesarray[i]).getMaxDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            PageHistory history = getPageHistory(pagenamesarray[i]);
            String formattedDate = whatDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"));
            //make new historyline object and add to list
            testHistoryLineList.add(new TestHistoryLine(String.valueOf(pagenamesarray[i]),
                    totalOfFailures,
                    totalOfPasses,
                    whatDate,
                    history,
                    formattedDate
                    ));
        }
        // return historylinelist sorted using getSortedLines()
        return testhistory.getSortedLines(testHistoryLineList);
    }

    private void readHistoryDirectory(File historyDirectory) {
        File[] pageDirectories = FileUtil.getDirectoryListing(historyDirectory);
        for (File file : pageDirectories)
            if (isValidFile(file))
                pageDirectoryMap.put(file.getName(), file);
    }

    private boolean isValidFile(File file) {
        return file.isDirectory() && file.list().length > 0 && PathParser.isWikiPath(file.getName());
    }

    private void readPageHistoryDirectory(File historyDirectory, String pageName) {
        File[] pageDirectories = FileUtil.getDirectoryListing(historyDirectory);
        for (File file : pageDirectories)
            if ((isValidFile(file)) && file.getName().startsWith(pageName))
                pageDirectoryMap.put(file.getName(), file);
    }

}
