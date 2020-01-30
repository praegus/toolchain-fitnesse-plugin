package nl.praegus.fitnesse.responders.testHistory;

import fitnesse.reporting.history.PageHistory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.toList;

public class TestHistory {
    private List<TestHistoryLine> testHistoryLines = new ArrayList<>();
    public Map<String, File> pageHistoryIndex;
    public TestHistory(File historyDirectory) {


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
}
