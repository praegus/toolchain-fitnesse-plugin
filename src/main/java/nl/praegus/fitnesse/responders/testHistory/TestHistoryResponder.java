package nl.praegus.fitnesse.responders.testHistory;

import fitnesse.FitNesseContext;
import fitnesse.authentication.SecureOperation;
import fitnesse.authentication.SecureReadOperation;
import fitnesse.authentication.SecureResponder;
import fitnesse.html.template.HtmlPage;
import fitnesse.html.template.PageTitle;
import fitnesse.http.Request;
import fitnesse.http.Response;
import fitnesse.http.Response.Format;
import fitnesse.http.SimpleResponse;
import fitnesse.wiki.PathParser;
import org.apache.velocity.VelocityContext;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.time.ZoneId;
import java.util.List;


public class TestHistoryResponder implements SecureResponder {

    private FitNesseContext context;

    @Override
    public Response makeResponse(FitNesseContext context, Request request) throws UnsupportedEncodingException {
        this.context = context;
        File resultsDirectory = context.getTestHistoryDirectory();
        String pageName = request.getResource();
        TestHistory testHistory = new TestHistory(resultsDirectory, pageName);

        if (formatIsXML(request)) {
            return makeTestHistoryXmlResponse(testHistory);
        } else {
            return makeTestHistoryResponse(testHistory, request, pageName);
        }
    }

    private Response makeTestHistoryResponse(TestHistory testHistory, Request request, String pageName) throws UnsupportedEncodingException {
        String[] pagenamesarray = testHistory.getPageNames().toArray(new String[0]);

        TestHistory TestHistory = new TestHistory();



        TestHistoryLine tablecontent[] = new TestHistoryLine[pagenamesarray.length];

        for (int i=0; i<pagenamesarray.length; i++){

          tablecontent[i] = new TestHistoryLine(String.valueOf(pagenamesarray[i]),1,1,testHistory.getPageHistory(pagenamesarray[i]).getMaxDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        }

        List sorted = testHistory.getSortedLines();

        HtmlPage page = context.pageFactory.newPage();

        //TestHistoryLine testHistoryLine = new TestHistoryLine("j",1,1,1);
        page.setTitle("Test History");
        page.setPageTitle(new PageTitle(PathParser.parse(pageName)));
        page.setNavTemplate("viewNav");
        page.put("viewLocation", request.getResource());
        page.put("testHistory", testHistory);
        page.setMainTemplate("testHistory");
        SimpleResponse response = new SimpleResponse();

 response.setContent(String.valueOf(sorted));
        return response;
    }

    private Response makeTestHistoryXmlResponse(TestHistory history) throws UnsupportedEncodingException {
        SimpleResponse response = new SimpleResponse();
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("testHistory", history);
        response.setContentType(Format.XML);
        response.setContent(context.pageFactory.render(velocityContext, "testHistoryXML.vm"));
        return response;
    }

    private boolean formatIsXML(Request request) {
        String format = request.getInput("format");
        return "xml".equalsIgnoreCase(format);
    }

    @Override
    public SecureOperation getSecureOperation() {
        return new SecureReadOperation();
    }
}
