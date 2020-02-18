package nl.praegus.fitnesse.responders.allTags;

import fitnesse.FitNesseContext;
import fitnesse.http.Request;
import fitnesse.wiki.WikiSourcePage;
import org.junit.Test;

import java.io.File;

public class AllTagsTest {

    /* makeTocResponse */
    @Test
    public void checkResponseParameters() {

        AllTagsResponder tagResponder = new AllTagsResponder();

//        List<TestHistoryLine> receivedResult = allTags.getHistoryLines();
//        tagResponder.makeTocResponse(getMockSuite("AllTagsDirectory.TestSuiteDemo"));
//        tagResponder.makeTocResponse();
//        System.out.println("Test");
    }

    /* getPageInfo */
    @Test
    public void checkArrayList() {

        AllTagsResponder tagResponder = new AllTagsResponder();

//        List<TestHistoryLine> receivedResult = allTags.getHistoryLines();
//        tagResponder.makeTocResponse(getMockSuite("AllTagsDirectory.TestSuiteDemo"));
        System.out.println("Test");
    }

    /* getSortedChildren */
    @Test
    public void checkIfChildrenAreSorted() {

        AllTagsResponder tagResponder = new AllTagsResponder();

//        List<TestHistoryLine> receivedResult = allTags.getHistoryLines();
//        tagResponder.makeTocResponse(getMockSuite("AllTagsDirectory.TestSuiteDemo"));
        System.out.println("Test");
    }

    // Setup method
    private File getMockSuite(String DirName){
        File getMockSuite = new File(getClass().getClassLoader().getResource(DirName).getFile());
        return getMockSuite;
    }
}
