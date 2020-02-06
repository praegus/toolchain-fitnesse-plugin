package nl.praegus.fitnesse.responders.testHistory;

import fitnesse.FitNesseContext;

import fitnesse.authentication.SecureOperation;
import fitnesse.authentication.SecureReadOperation;
import fitnesse.authentication.SecureResponder;
import fitnesse.html.template.HtmlPage;
import fitnesse.html.template.PageTitle;
import fitnesse.http.Request;
import fitnesse.http.Response;
import fitnesse.http.SimpleResponse;
import fitnesse.wiki.PathParser;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;


public class TestHistoryResponder implements SecureResponder {

    private FitNesseContext context;

    @Override
    public Response makeResponse(FitNesseContext context, Request request) throws UnsupportedEncodingException {
        this.context = context;
        File resultsDirectory = context.getTestHistoryDirectory();
        String pageName = request.getResource();
        TestHistory testHistory = new TestHistory(resultsDirectory);
            return makeTestHistorySortedResponse(testHistory, request, pageName);
    }

    private Response makeTestHistorySortedResponse(TestHistory testHistory, Request request, String pageName) throws UnsupportedEncodingException {
        List<TestHistoryLine> historyLines = testHistory.getHistoryLines();
        HtmlPage page = context.pageFactory.newPage();

        page.setTitle("Test History");
        page.setPageTitle(new PageTitle(PathParser.parse(pageName)));
        page.setNavTemplate("viewNav");
        page.put("viewLocation", request.getResource());
        page.put("testHistory", historyLines);
        page.setMainTemplate("testHistorySorted");

        SimpleResponse response = new SimpleResponse();

        response.setContent(page.html(request));
        return response;
    }

    @Override
    public SecureOperation getSecureOperation() {
        return new SecureReadOperation();
    }
}
