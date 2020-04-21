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

import static fitnesse.wiki.WikiPageProperty.SUITES;
import static nl.praegus.fitnesse.responders.WikiPageHelper.loadPage;

public class SymbolicLinkResponder implements SecureResponder {
    public JSONArray symbolicLinkArray = new JSONArray();
    private PageData pageData;

    @Override
    public Response makeResponse(FitNesseContext fitNesseContext, Request request) throws Exception {
        WikiSourcePage sourcePage = new WikiSourcePage(loadPage(fitNesseContext, request.getResource(), request.getMap()));
        WikiPage wikiPage = loadPage(fitNesseContext, request.getResource(), request.getMap());
        pageData = wikiPage.getData();
        return makeTagResponse(wikiPage, fitNesseContext, request);
    }

    public SimpleResponse makeTagResponse(WikiPage wikiPage, FitNesseContext fitNesseContext, Request request) throws UnsupportedEncodingException {
        SimpleResponse response = new SimpleResponse();
        response.setMaxAge(0);
        response.setStatus(200);
        response.setContentType("text/html");
//        response.setContent(getSymlink(wikiPage).toString());
        response.setContent(createHTMLPage(fitNesseContext, request, wikiPage));

        return response;
    }

    @Override
    public SecureOperation getSecureOperation() {
        return new SecureReadOperation();
    }

    public JSONArray getSymlink(WikiPage page){
        return getSymlinkHelper(page);
    }

    private JSONArray getSymlinkHelper(WikiPage wikiPage) {
        // Get Symlinks for the wiki page
        WikiPageProperty symLinksProperty = pageData.getProperties().getProperty(SymbolicPage.PROPERTY_NAME);

        // Loop to the names
        if (symLinksProperty != null) {
            for (String name : symLinksProperty.keySet()) {
                symbolicLinkArray.put(getObject(name, wikiPage, symLinksProperty));
            }
        }

        // Get Symlinks from the children
        // TO DO: Get the source page en check if there are children


        return symLinksProperty != null ? symbolicLinkArray : new JSONArray();
    }

    private JSONObject getObject(String name, WikiPage wikiPage, WikiPageProperty symLinksProperty) {
        JSONObject symboliclinkInformation = new JSONObject();
        symboliclinkInformation.put("pagePath", wikiPage.getName());
        symboliclinkInformation.put("linkName", name);
        symboliclinkInformation.put("linkPath", symLinksProperty.get(name));
        return symboliclinkInformation;
    }

    private String createHTMLPage(FitNesseContext fitNesseContext, Request request, WikiPage wikiPage) {
        HtmlPage html = fitNesseContext.pageFactory.newPage();
        html.setNavTemplate("viewNav");
        html.setMainTemplate("symlinks");
        html.put("viewLocation", request.getResource());
        html.put("symlinks", getSymlink(wikiPage));
        html.setTitle("Symbolic links: " + request.getResource());

        String tags = "";
        if(pageData != null)  {
            tags = pageData.getAttribute(SUITES);
        }
        html.setPageTitle(new PageTitle("Symbolic links", PathParser.parse(request.getResource()), tags));
        return html.html(request);
    }

}
