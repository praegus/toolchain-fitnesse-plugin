package nl.praegus.fitnesse.responders.symbolicLink;

import fitnesse.wiki.PageData;
import fitnesse.wiki.WikiPage;
import fitnesse.wiki.WikiPageProperty;

public class TestWikiPageDummy {
    private PageData pageData;

    public TestWikiPageDummy(String name, String content, WikiPage parent, WikiPageProperty property) {
        this.pageData = new PageData(content, property);
    }

    public PageData getData() {
        return this.pageData;
    }
}
