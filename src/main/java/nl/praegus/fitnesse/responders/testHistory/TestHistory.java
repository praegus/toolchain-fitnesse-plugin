package nl.praegus.fitnesse.responders.testHistory;

import fitnesse.reporting.history.PageHistory;
import fitnesse.wiki.PathParser;
import util.FileUtil;

import java.io.File;
import java.time.ZoneId;
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

    public List<TestHistoryLine> getSortedLines(List lijstje) {
        List sorted = (List) lijstje.stream()
                .sorted(comparing(TestHistoryLine::getLastRun, nullsLast(reverseOrder())))
                .collect(toList());

        return sorted;
    }

    public List getHistoryLineList(){
        String[] pagenamesarray = getPageNames().toArray(new String[0]);
        TestHistoryLine tablecontent[] = new TestHistoryLine[pagenamesarray.length];
        List testHistoryLineList = new ArrayList();

        for (int i=0; i<pagenamesarray.length; i++){

            testHistoryLineList.add(new TestHistoryLine(String.valueOf(pagenamesarray[i]),getPageHistory(pagenamesarray[i]).getFailures(),getPageHistory(pagenamesarray[i]).getPasses(),getPageHistory(pagenamesarray[i]).getMaxDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),getPageHistory(pagenamesarray[i]).getBarGraph()));
        }

        TestHistory testhistory = new TestHistory();
        List sorted = testhistory.getSortedLines(testHistoryLineList);

        return sorted;
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
