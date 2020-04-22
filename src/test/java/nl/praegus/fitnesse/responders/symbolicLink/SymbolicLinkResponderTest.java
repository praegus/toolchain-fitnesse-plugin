package nl.praegus.fitnesse.responders.symbolicLink;

import fitnesse.wiki.WikiPage;
import fitnesse.wiki.WikiPageDummy;
import fitnesse.wiki.WikiPageProperty;
import fitnesse.wiki.WikiSourcePage;
import nl.praegus.fitnesse.responders.allTags.AllTagsResponder;
import org.json.JSONArray;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SymbolicLinkResponderTest {
    @Test
    public void check_If_Response_Is_Equal_To_Expected_List_With_One_Page() {
        SymbolicLinkResponder tagResponder = new SymbolicLinkResponder();
        WikiPageProperty testTagProperty = new WikiPageProperty();
        testTagProperty.set("Suites", "testTag");
        TestWikiPageDummy TestPageDummy = new TestWikiPageDummy("dummyPage", "", new WikiPageDummy(), new WikiPageProperty());

//        JSONArray receivedValue = tagResponder.getSymlink(TestPageDummy);

//        assertThat(receivedValue).isEqualTo(new HashSet<>(Collections.singletonList("testTag")));
    }
}
