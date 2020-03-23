package nl.praegus.fitnesse.responders;

import fitnesse.FitNesseContext;
import fitnesse.wiki.*;

import java.util.Map;

public class WikiPageHelper {
    public static WikiPage loadPage(FitNesseContext context, String pageName, Map<String, String> inputs) {
        WikiPage page;
        if (RecentChanges.RECENT_CHANGES.equals(pageName)) {
            page = context.recentChanges.toWikiPage(context.getRootPage());
        } else {
            WikiPagePath path = PathParser.parse(pageName);
            PageCrawler crawler = context.getRootPage(inputs).getPageCrawler();
            page = crawler.getPage(path);
        }
        return page;
    }
}
