package nl.praegus.fitnesse.responders;

import fitnesse.FitNesseContext;
import fitnesse.authentication.SecureOperation;
import fitnesse.authentication.SecureReadOperation;
import fitnesse.authentication.SecureResponder;
import fitnesse.http.Request;
import fitnesse.http.Response;
import fitnesse.http.SimpleResponse;
import fitnesse.wiki.*;
import fitnesse.wikitext.parser.SourcePage;
import org.json.JSONArray;
import org.json.JSONObject;
import util.GracefulNamer;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class TableOfContentsResponder implements SecureResponder {

    private JSONArray toc = new JSONArray();

    @Override
    public Response makeResponse(FitNesseContext fitNesseContext, Request request) throws Exception {
        WikiSourcePage sourcePage = new WikiSourcePage(loadPage(fitNesseContext, request.getResource(), request.getMap()));
        return makeTocResponse(sourcePage);
    }

    private WikiPage loadPage(FitNesseContext context, String pageName, Map<String, String> inputs) {
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

    private SimpleResponse makeTocResponse(SourcePage sourcePage) throws UnsupportedEncodingException {
        toc.put(getPageInfo(sourcePage));

        SimpleResponse response = new SimpleResponse();
        response.setMaxAge(0);
        response.setStatus(200);
        response.setContentType("application/json");
        response.setContent(toc.toString(3));

        return response;
    }

    private Collection<SourcePage> getSortedChildren(SourcePage parent) {
        ArrayList<SourcePage> result = new ArrayList<>(parent.getChildren());
        Collections.sort(result);
        return result;
    }

    private JSONObject getPageInfo(SourcePage page) {
        JSONObject pageInfo = new JSONObject();
        pageInfo.append("name", GracefulNamer.regrace(page.getName()));
        pageInfo.append("type", getBooleanPropertiesClasses(page));
        pageInfo.append("help", page.getProperty(WikiPageProperty.HELP));
        pageInfo.append("tags", page.getProperty(WikiPageProperty.SUITES));
        pageInfo.append("path", page.getFullPath());
        for (SourcePage p : getSortedChildren(page)) {
            pageInfo.append("children", getPageInfo(p));
        }
        return pageInfo;
    }

    private String getBooleanPropertiesClasses(SourcePage sourcePage) {
        String result = "";
        if (sourcePage.hasProperty(PageType.SUITE.toString())) {
            result += "suite";
        } else if (sourcePage.hasProperty(PageType.TEST.toString())) {
            result += "test";
        } else {
            result += "static";
        }
        if (sourcePage.hasProperty(WikiImportProperty.PROPERTY_NAME)) result += " linked";
        if (sourcePage.hasProperty(WikiPageProperty.PRUNE)) result += " pruned";
        return result;
    }

    @Override
    public SecureOperation getSecureOperation() {
        return new SecureReadOperation();
    }
}
