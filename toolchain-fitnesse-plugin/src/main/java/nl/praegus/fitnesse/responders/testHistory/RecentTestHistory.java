package nl.praegus.fitnesse.responders.testHistory;

import fitnesse.reporting.history.PageHistory;
import fitnesse.wiki.PathParser;

import util.FileUtil;
import java.io.File;
import java.util.*;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.toList;

public class RecentTestHistory {
    private List<TestHistoryLine> testHistoryLines = new ArrayList<>();
    private Map<String, File> pageHistoryIndex;

    public RecentTestHistory(File historyDirectory){
        this.pageHistoryIndex = getHistoryIndex(historyDirectory);

        for (String pageName : pageHistoryIndex.keySet()) {
            PageHistory pageHistory = getPageHistory(pageName);
            testHistoryLines.add(new TestHistoryLine(pageHistory));
        }
    }

    public List<TestHistoryLine> getHistoryLines() {
        return testHistoryLines.stream()
                .sorted(comparing(TestHistoryLine::getMostRecentRunDate, nullsLast(reverseOrder())))
                .collect(toList());
    }

    public List<TestHistoryLine> getFilteredTestHistoryLines() {
        List<TestHistoryLine> filteredTestHistoryLines = new ArrayList<>();

        for (TestHistoryLine testHistoryLine : testHistoryLines) {
            String testName;

            if (testHistoryLine.getPageName().contains(".")) {
                testName = testHistoryLine.getPageName().substring(testHistoryLine.getPageName().lastIndexOf(".") + 1);
            } else {
                testName = testHistoryLine.getPageName();
            }

            if (!testName.equals("SetUp") && !testName.equals("TearDown") && !testName.equals("SuiteTearDown") && !testName.equals("SuiteSetUp")) {
                filteredTestHistoryLines.add(testHistoryLine);
            }
        }

        return filteredTestHistoryLines.stream()
                .sorted(comparing(TestHistoryLine::getMostRecentRunDate, nullsLast(reverseOrder())))
                .collect(toList());
    }

    public Set<String> getPageNames(){
        return pageHistoryIndex.keySet();
    }

    public PageHistory getPageHistory(String pageName) {
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
