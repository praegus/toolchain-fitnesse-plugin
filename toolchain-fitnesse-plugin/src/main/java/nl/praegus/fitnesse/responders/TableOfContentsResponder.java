package nl.praegus.fitnesse.responders;

import fitnesse.FitNesseContext;
import fitnesse.authentication.SecureOperation;
import fitnesse.authentication.SecureReadOperation;
import fitnesse.authentication.SecureResponder;
import fitnesse.http.Request;
import fitnesse.http.Response;
import fitnesse.http.SimpleResponse;
import fitnesse.wiki.*;
import fitnesse.wikitext.SourcePage;
import org.json.JSONArray;
import org.json.JSONObject;
import util.GracefulNamer;

import java.io.UnsupportedEncodingException;
import java.util.*;

import static nl.praegus.fitnesse.responders.WikiPageHelper.loadPage;

public class TableOfContentsResponder implements SecureResponder {

    private JSONArray tableOfContents = new JSONArray();

    @Override
    public Response makeResponse(FitNesseContext fitNesseContext, Request request) throws Exception {
        WikiSourcePage sourcePage = new WikiSourcePage(loadPage(fitNesseContext, request.getResource(), request.getMap()));
        return makeTableOfContentsResponse(sourcePage);
    }

    private SimpleResponse makeTableOfContentsResponse(SourcePage sourcePage) throws UnsupportedEncodingException {
        tableOfContents.put(getPageInfo(null, sourcePage));

        SimpleResponse response = new SimpleResponse();
        response.setMaxAge(0);
        response.setStatus(200);
        response.setContentType("application/json");
        response.setContent(tableOfContents.toString(3));

        return response;
    }

    private Collection<SourcePage> getSortedChildren(SourcePage parent) {
        ArrayList<SourcePage> result = new ArrayList<>(parent.getChildren());
        Collections.sort(result);
        return result;
    }

    private JSONObject getPageInfo(SourcePage parent, SourcePage page) {
        JSONObject pageInfo = new JSONObject();
        pageInfo.put("name", GracefulNamer.regrace(page.getName()));
        pageInfo.put("type", getBooleanPropertiesClasses(page));
        pageInfo.put("help", page.getProperty(WikiPageProperty.HELP));
        String[] tags = page.getProperty(WikiPageProperty.SUITES).split(", ");

        if (parent instanceof WikiSourcePage && ((WikiSourcePage) parent).hasSymbolicLinkChild(page.getName())) {
            pageInfo.put("isSymlink", true);
        } else {
            pageInfo.put("isSymlink", false);
        }

        for (String tag : tags) {
            if(tag.length() > 0) {
                pageInfo.append("tags", tag);
            }
        }
        pageInfo.put("path", page.getFullPath());
        for (SourcePage p : getSortedChildren(page)) {
            pageInfo.append("children", getPageInfo(page, p));
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
        if (sourcePage.hasProperty(WikiImportProperty.PROPERTY_NAME)) {
            result += " linked";
        }
        if (sourcePage.hasProperty(WikiPageProperty.PRUNE)) {
            result += " pruned";
        }
        return result;
    }

    @Override
    public SecureOperation getSecureOperation() {
        return new SecureReadOperation();
    }
}
