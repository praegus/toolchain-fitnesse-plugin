package nl.praegus.fitnesse.responders.allTags;

import fitnesse.wiki.PageData;
import fitnesse.wiki.WikiPage;
import fitnesse.wiki.WikiPageProperty;

import java.util.Collections;
import java.util.List;

class TestWikiPageDummy extends fitnesse.wiki.WikiPageDummy {
    private final List<WikiPage> children;
    private PageData pageData;

    public TestWikiPageDummy(String name, String content, WikiPage parent, WikiPageProperty property) {
        this.pageData = new PageData(content, property);
        this.children = Collections.emptyList();
    }

    public TestWikiPageDummy(String name, String content, WikiPage parent, WikiPageProperty property, List<WikiPage> children) {
        this.pageData = new PageData(content, property);
        this.children = children;
    }

    public PageData getData() {
        return this.pageData;
    }

    public List<WikiPage> getChildren() {
        return children;
    }
}

