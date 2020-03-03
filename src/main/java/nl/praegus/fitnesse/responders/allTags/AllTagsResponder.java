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

    public SimpleResponse makeTagResponse(SourcePage sourcePage) throws UnsupportedEncodingException {
        tagObject.put("Tags", getPageTags(sourcePage, new ArrayList<>()));

        SimpleResponse response = new SimpleResponse();
        response.setMaxAge(0);
        response.setStatus(200);
        response.setContentType("application/json");
        response.setContent(tagObject.toString(3));
        return response;
    }

    public List<String> getPageTags(SourcePage page, ArrayList<String> allTags){
        //return getAllTagsOfPage(page, new ArrayList<>());
            String[] tags = page.getProperty(WikiPageProperty.SUITES).split("\\s*,\\s*");
            allTags.addAll(Arrays.asList(tags));

            for (SourcePage p : getSortedChildren(page)) {
                getPageTags(p, allTags);
            }

            return allTags;
    }

    @Override
    public SecureOperation getSecureOperation() {
        return new SecureReadOperation();
    }

    private List<SourcePage> getSortedChildren(SourcePage parent) {
        List<SourcePage> result = new ArrayList<>(parent.getChildren());
        Collections.sort(result);
        return result;
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


}
