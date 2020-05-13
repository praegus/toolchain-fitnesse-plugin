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

import java.io.UnsupportedEncodingException;

import static fitnesse.wiki.WikiPageProperty.SUITES;
import static nl.praegus.fitnesse.responders.WikiPageHelper.loadPage;

public class SymbolicLinkResponder implements SecureResponder {

    @Override
    public SecureOperation getSecureOperation() {
        return new SecureReadOperation();
    }

    @Override
    public Response makeResponse(FitNesseContext fitNesseContext, Request request) throws Exception {
        WikiPage wikiPage = loadPage(fitNesseContext, request.getResource(), request.getMap());

        return makeSymlinksResponse(wikiPage, fitNesseContext, request);
    }

    private SimpleResponse makeSymlinksResponse(WikiPage wikiPage, FitNesseContext fitNesseContext, Request request) throws UnsupportedEncodingException {
        SimpleResponse response = new SimpleResponse();
        response.setMaxAge(0);
        response.setStatus(200);
        response.setContentType("text/html");
        response.setContent(getHtmlPageAsString(fitNesseContext, request, wikiPage));

        return response;
    }

    private String getHtmlPageAsString(FitNesseContext fitNesseContext, Request request, WikiPage wikiPage) {
        ProjectSymbolicLinkInfo projectSymbolicLinkInfo = new ProjectSymbolicLinkInfo(wikiPage);

        HtmlPage htmlPage = fitNesseContext.pageFactory.newPage();
        htmlPage.setNavTemplate("viewNav");
        htmlPage.setMainTemplate("symlinks");
        htmlPage.put("viewLocation", request.getResource());
        htmlPage.put("symlinks", projectSymbolicLinkInfo.getSymbolicLinkInfosJsonArray());
        htmlPage.setTitle("Symbolic links: " + request.getResource());

        String tags = "";
        if(wikiPage.getData() != null)  {
            tags = wikiPage.getData().getAttribute(SUITES);
        }
        htmlPage.setPageTitle(new PageTitle("Symbolic links", PathParser.parse(request.getResource()), tags));
        return htmlPage.html(request);
    }

}
