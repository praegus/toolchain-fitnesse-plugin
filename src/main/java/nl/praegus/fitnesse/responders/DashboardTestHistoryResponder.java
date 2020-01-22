package nl.praegus.fitnesse.responders;
import fitnesse.FitNesseContext;
import fitnesse.authentication.SecureOperation;
import fitnesse.authentication.SecureResponder;
import fitnesse.html.template.HtmlPage;
import fitnesse.http.Request;
import fitnesse.http.Response;
import fitnesse.http.SimpleResponse;
import fitnesse.reporting.history.TestHistory;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


public class DashboardTestHistoryResponder implements SecureResponder{

    private FitNesseContext context;

    public Response makeResponse(FitNesseContext context, Request request) throws IOException, ParserConfigurationException, SAXException {
        this.context = context;
        File resultsDirectory = context.getTestHistoryDirectory();
        String pageName = request.getResource();
        TestHistory testHistory = new TestHistory(resultsDirectory, pageName);

        return DashboardTestHistoryResponse(testHistory, request, pageName);
    }

    private SimpleResponse DashboardTestHistoryResponse(TestHistory testHistory, Request request, String pageName) throws IOException, ParserConfigurationException, SAXException
    {
        HtmlPage page = context.pageFactory.newPage();
        // testHistory is the data we need
        page.put("testHistory", testHistory);
        page.setMainTemplate("");
        SimpleResponse response = new SimpleResponse();
        response.setContent(page.html(request));
        return response;
    }


    @Override
    public SecureOperation getSecureOperation() {
        return null;
    }



}