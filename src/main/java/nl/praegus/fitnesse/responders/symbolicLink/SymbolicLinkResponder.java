package nl.praegus.fitnesse.responders.symbolicLink;

import fitnesse.FitNesseContext;
import fitnesse.authentication.SecureOperation;
import fitnesse.authentication.SecureReadOperation;
import fitnesse.authentication.SecureResponder;
import fitnesse.html.template.HtmlPage;
import fitnesse.html.template.PageTitle;
import fitnesse.http.Request;
import fitnesse.http.Response;
import fitnesse.http.SimpleResponse;
import fitnesse.wiki.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static fitnesse.wiki.WikiPageProperty.SUITES;
import static nl.praegus.fitnesse.responders.WikiPageHelper.loadPage;

public class SymbolicLinkResponder implements SecureResponder {
    private PageData pageData;

    @Override
    public Response makeResponse(FitNesseContext fitNesseContext, Request request) throws Exception {
        WikiPage wikiPage = loadPage(fitNesseContext, request.getResource(), request.getMap());

        return makeTagResponse(wikiPage, fitNesseContext, request);
    }

    public SimpleResponse makeTagResponse(WikiPage wikiPage, FitNesseContext fitNesseContext, Request request) throws UnsupportedEncodingException {
        SimpleResponse response = new SimpleResponse();
        response.setMaxAge(0);
        response.setStatus(200);
        response.setContentType("text/html");
        response.setContent(getHtmlPageAsString(fitNesseContext, request, wikiPage));

        return response;
    }

    @Override
    public SecureOperation getSecureOperation() {
        return new SecureReadOperation();
    }

    public JSONArray getSymlink(WikiPage page){
        return getSymlinkHelper(page, new JSONArray());
    }

    private JSONArray getSymlinkHelper(WikiPage wikiPage, JSONArray symbolicLinkArray) {
        pageData = wikiPage.getData();
        // Get Symlinks of the wiki page
        WikiPageProperty symLinksProperty = pageData.getProperties().getProperty(SymbolicPage.PROPERTY_NAME);

        // Loop through the names
        if (symLinksProperty != null) {
            for (String name : symLinksProperty.keySet()) {
                symbolicLinkArray.put(getObject(name, wikiPage, symLinksProperty));
            }
        }

        // Get Symlinks from the children
        if (wikiPage.getChildren().size() > 0) {
            for (WikiPage p : wikiPage.getChildren()) {
                getSymlinkHelper(p, symbolicLinkArray);
            }
        }

        return symLinksProperty != null ? symbolicLinkArray : new JSONArray();
    }

    private JSONObject getObject(String name, WikiPage wikiPage, WikiPageProperty symLinksProperty) {
        JSONObject symboliclinkInformation = new JSONObject();
        symboliclinkInformation.put("pagePath", wikiPage.getFullPath().toString());
        symboliclinkInformation.put("linkName", name);
        symboliclinkInformation.put("linkPath", makePathForSymbolicLink(wikiPage, PathParser.parse(symLinksProperty.get(name))));
        symboliclinkInformation.put("backUpLinkPath", symLinksProperty.get(name));
        return symboliclinkInformation;
    }

    private String makePathForSymbolicLink(WikiPage page, WikiPagePath wikiPagePath) {
        if (wikiPagePath != null) {
            WikiPage wikiPage = wikiPagePath.isRelativePath() ? page.getParent() : page;
            PageCrawler crawler = wikiPage.getPageCrawler();
            WikiPage wikiPageFromCrawler = crawler.getPage(wikiPagePath);
            WikiPagePath fullPath;
            if (wikiPageFromCrawler != null) {
                fullPath = wikiPageFromCrawler.getFullPath();
                fullPath.makeAbsolute();
            } else {
                fullPath = new WikiPagePath();
            }
            String pathString = fullPath.toString();

            return pathString.length() > 0 && pathString.charAt(0) == '.' ? pathString.substring(1) : pathString;
        }
        return null;
    }

    private String getHtmlPageAsString(FitNesseContext fitNesseContext, Request request, WikiPage wikiPage) {
        HtmlPage htmlPage = fitNesseContext.pageFactory.newPage();
        htmlPage.setNavTemplate("viewNav");
        htmlPage.setMainTemplate("symlinks");
        htmlPage.put("viewLocation", request.getResource());
        htmlPage.put("symlinks", getSymlink(wikiPage));
        htmlPage.setTitle("Symbolic links: " + request.getResource());

        String tags = "";
        if(pageData != null)  {
            tags = pageData.getAttribute(SUITES);
        }
        htmlPage.setPageTitle(new PageTitle("Symbolic links", PathParser.parse(request.getResource()), tags));
        return htmlPage.html(request);
    }

}
