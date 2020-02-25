package nl.praegus.fitnesse.responders.allTags;

import fitnesse.FitNesseContext;
import fitnesse.authentication.SecureOperation;
import fitnesse.authentication.SecureReadOperation;
import fitnesse.authentication.SecureResponder;
import fitnesse.http.Request;
import fitnesse.http.Response;
import fitnesse.http.SimpleResponse;
import fitnesse.wiki.*;
import fitnesse.wikitext.parser.SourcePage;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class AllTagsResponder implements SecureResponder {
    private JSONObject tagObject = new JSONObject();

    @Override
    public Response makeResponse(FitNesseContext fitNesseContext, Request request) throws Exception {
        WikiSourcePage sourcePage = new WikiSourcePage(loadPage(fitNesseContext, request.getResource(), request.getMap()));

        return makeTagResponse(sourcePage);
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

    public SimpleResponse makeTagResponse(SourcePage sourcePage) throws UnsupportedEncodingException {
        List<String> allTagsArray = new ArrayList<>();
        tagObject.put("Tags", getPageTags(sourcePage, allTagsArray));

        SimpleResponse response = new SimpleResponse();
        response.setMaxAge(0);
        response.setStatus(200);
        response.setContentType("application/json");
        response.setContent(tagObject.toString(3));
        return response;
    }

    private Collection<SourcePage> getSortedChildren(SourcePage parent) {
        ArrayList<SourcePage> result = new ArrayList<>(parent.getChildren());
        Collections.sort(result);
        return result;
    }

    public List<String> getPageTags(SourcePage page, List<String> allTagsArray) {
        String[] tags = page.getProperty(WikiPageProperty.SUITES).split(", ");

        for(String tag: tags) {
            if (tag.length() > 0 && !allTagsArray.contains(tag)) {
                allTagsArray.add(tag);
            }
        }

        for (SourcePage p : getSortedChildren(page)) {
            getPageTags(p, allTagsArray);
        }

        return allTagsArray;
    }

    @Override
    public SecureOperation getSecureOperation() {
        return new SecureReadOperation();
    }
}
