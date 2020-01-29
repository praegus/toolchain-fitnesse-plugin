package nl.praegus.fitnesse.responders.testHistory;

import fitnesse.reporting.history.PageHistory;
import fitnesse.wiki.PathParser;
import util.FileUtil;

import java.io.File;
import java.util.*;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.toList;

public class TestHistory {
    private List<TestHistoryLine> testHistoryLines = new ArrayList<>();
    private Map<String, File> pageHistoryIndex;

    public TestHistory(File historyDirectory) {
        this.pageHistoryIndex = getHistoryIndex(historyDirectory);
        // loop for each name in pagenames array
        for (String pageName : pageHistoryIndex.keySet()) {
//            //populate data for testhistoryline object
            PageHistory pageHistory = getPageHistory(pageName);
//            int totalOfFailures = pageHistory.getFailures();
//            int totalOfPasses = pageHistory.getPasses();
//            LocalDateTime mostRecentRunDate = pageHistory.getMaxDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//            //make new historyline object and add to list
            testHistoryLines.add(new TestHistoryLine(pageHistory));
        }
    }

    public List<TestHistoryLine> getHistoryLines() {
        // sort list using stream and return it
        return testHistoryLines.stream()
                .sorted(comparing(TestHistoryLine::getMostRecentRunDate, nullsLast(reverseOrder())))
                .collect(toList());
    }

    private PageHistory getPageHistory(String pageName) {
        File historyPage = pageHistoryIndex.get(pageName);
        if (historyPage == null)
            return null;
        else {
            PageHistory pageHistory = new PageHistory(historyPage);
            if (pageHistory.size() == 0)
                return null;
            else
                return pageHistory;
        }
    }


    private Map<String, File> getHistoryIndex(File historyDirectory) {
        Map<String, File> map = new HashMap<>();
        File[] pageDirectories = FileUtil.getDirectoryListing(historyDirectory);
        for (File file : pageDirectories) {
            if (isValidFile(file)) {
                map.put(file.getName(), file);
            }
        }
        return map;
    }

    private boolean isValidFile(File file) {
        return file.isDirectory() && Objects.requireNonNull(file.list()).length > 0 && PathParser.isWikiPath(file.getName());
    }
}
