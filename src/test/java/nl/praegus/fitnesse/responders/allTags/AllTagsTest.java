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

    /* makeTocResponse */
    @Test
    public void checkResponseParameters() {
        String dummyContent = "---\n" +
                "Suite\n" +
                "Suites: testSuiteDemoTag\n" +
                "Test: no\n" +
                "---\n" +
                "!contents -R2 -g -f -h\n" +
                "\n" +
                "!*> Config & Classpath\n" +
                "!define TEST_SYSTEM {slim}\n" +
                "!path fixtures/\n" +
                "!path fixtures/*.jar\n" +
                "*!\n" +
                "\n" +
                "Dit is een link >TestSuiteDemo>";
        WikiPageDummy testDummy = new WikiPageDummy("dummyPage", dummyContent, new WikiPageDummy());
        WikiSourcePage testWikiSourcePage = new WikiSourcePage(testDummy);
        ArrayList<String> allTagsArray = new ArrayList<String>();
        AllTagsResponder tagResponder = new AllTagsResponder();

        ArrayList expectedValue = tagResponder.getPageInfo(testWikiSourcePage, allTagsArray);

//        System.out.println(expectedValue);

//        Test WikiSourcePage dummy tags
        System.out.println(testWikiSourcePage.getProperty(WikiPageProperty.SUITES));

    }
}
