package nl.praegus.fitnesse.responders.allTags;

import fitnesse.wiki.*;
import fitnesse.wiki.WikiPageDummy;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class AllTagsResponderTest {

    /* getPageTags */
    @Test
    public void checkTagListResponse() {
        String dummyContent = "!contents -R2 -g -f -h\n" +
                "\n" +
                "!*> Config & Classpath\n" +
                "!define TEST_SYSTEM {slim}\n" +
                "!path fixtures/\n" +
                "!path fixtures/*.jar\n" +
                "*!\n" +
                "\n" +
                "Dit is een link >TestSuiteDemo>";
        WikiPageProperty testTagProperty = new WikiPageProperty();
        testTagProperty.set("SUITES", "testTag");
        AlexWikiPageDummy testWikiPageDummy = new AlexWikiPageDummy("dummyPage", dummyContent, new WikiPageDummy(), testTagProperty);
        WikiSourcePage testWikiSourcePage = new WikiSourcePage(testWikiPageDummy);
        List<String> allTagsArray = new ArrayList<>();
        AllTagsResponder tagResponder = new AllTagsResponder();

        List<String> expectedValue = new ArrayList<>();
        expectedValue.add("testTag");
        List<String> receivedValue = tagResponder.getPageTags(testWikiSourcePage, allTagsArray);

        assertThat(receivedValue, is(expectedValue));
    }
}

