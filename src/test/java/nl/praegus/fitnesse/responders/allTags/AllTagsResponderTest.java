package nl.praegus.fitnesse.responders.allTags;

import fitnesse.wiki.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class AllTagsResponderTest {

    /* getPageTags */
    @Test
    public void checkTagListResponse() {
        AllTagsResponder tagResponder = new AllTagsResponder();
        WikiPageProperty testTagProperty = new WikiPageProperty();
        testTagProperty.set("Suites", "testTag");
        TestWikiPageDummy testWikiPageDummy = new TestWikiPageDummy("dummyPage", "", new WikiPageDummy(), testTagProperty);
        WikiSourcePage testWikiSourcePage = new WikiSourcePage(testWikiPageDummy);

        List<String> receivedValue = tagResponder.getPageTags(testWikiSourcePage);

        assertThat(receivedValue, is(Collections.singletonList("testTag")));
    }

    /* getPageTags */
    @Test
    public void checkTagListChildrenResponse() {
        AllTagsResponder tagResponder = new AllTagsResponder();
        WikiPageProperty testTagPropertyMainSuite = new WikiPageProperty();
        testTagPropertyMainSuite.set("Suites", "mainTag");
        WikiPageProperty testTagPropertyChildSuite = new WikiPageProperty();
        testTagPropertyChildSuite.set("Suites", "childTag");
        TestWikiPageDummy testWikiPageDummyChild = new TestWikiPageDummy("childPageTest", "", new WikiPageDummy(), testTagPropertyChildSuite);
        List<WikiPage> children = Collections.singletonList(testWikiPageDummyChild);
        TestWikiPageDummy testWikiPageDummy = new TestWikiPageDummy("dummyPage", "", new WikiPageDummy(), testTagPropertyMainSuite, children);
        WikiSourcePage testWikiSourcePage = new WikiSourcePage(testWikiPageDummy);

        List<String> receivedValue = tagResponder.getPageTags(testWikiSourcePage);

        assertThat(receivedValue, is(Arrays.asList("mainTag", "childTag")));
    }
}

