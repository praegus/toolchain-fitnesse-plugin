package nl.praegus.fitnesse.responders.symbolicLink;

import fitnesse.wiki.WikiPage;
import fitnesse.wiki.WikiPageDummy;
import fitnesse.wiki.WikiPageProperty;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SymbolicLinkResponderTest {
    @Test
    public void check_If_Response_Is_Equal_To_Expected_List_With_One_Page() {
        SymbolicLinkResponder symlinkResponder = new SymbolicLinkResponder();
        WikiPageProperty testTagPropertyMainSuite = new WikiPageProperty();
        testTagPropertyMainSuite.set("Suites", "mainTag");
        WikiPageProperty testTagPropertyChildSuite = new WikiPageProperty();
        testTagPropertyChildSuite.set("Suites", "childTag");
        TestWikiPageDummySymlinks testWikiPageDummyChild = new TestWikiPageDummySymlinks("childPageTest", "", new WikiPageDummy(), testTagPropertyChildSuite);
        List<WikiPage> children = Collections.singletonList(testWikiPageDummyChild);
        TestWikiPageDummySymlinks testWikiPageDummy = new TestWikiPageDummySymlinks("dummyPage", "", new WikiPageDummy(), testTagPropertyMainSuite, children);


//         List<SymbolicLinkInfo> receivedValue = symlinkResponder.getSymlinks(testWikiPageDummy);

//         assertThat(receivedValue).isEqualTo(new JSONArray());
    }
}
