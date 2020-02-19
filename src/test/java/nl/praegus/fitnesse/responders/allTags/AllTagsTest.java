package nl.praegus.fitnesse.responders.allTags;

import fitnesse.wiki.WikiPage;
import fitnesse.wiki.WikiPageDummy;
import fitnesse.wiki.WikiPageProperty;
import fitnesse.wiki.WikiSourcePage;
import fitnesse.wikitext.parser.SourcePage;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class AllTagsTest {

    /* getPageInfo */
    @Test
    public void checkResponseParameters() {
        String dummyContent = "!contents -R2 -g -f -h\n" +
                "\n" +
                "!*> Config & Classpath\n" +
                "!define TEST_SYSTEM {slim}\n" +
                "!path fixtures/\n" +
                "!path fixtures/*.jar\n" +
                "*!\n" +
                "\n" +
                "Dit is een link >TestSuiteDemo>";
        WikiPageDummy testWikiPageDummy = new WikiPageDummy("dummyPage", dummyContent, new WikiPageDummy());
        WikiSourcePage testWikiSourcePage = new WikiSourcePage(testWikiPageDummy);
        ArrayList<String> allTagsArray = new ArrayList<String>();
        AllTagsResponder tagResponder = new AllTagsResponder();

        ArrayList receivedValue = tagResponder.getPageInfo(testWikiSourcePage, allTagsArray);

        System.out.println(receivedValue);

    }
}
