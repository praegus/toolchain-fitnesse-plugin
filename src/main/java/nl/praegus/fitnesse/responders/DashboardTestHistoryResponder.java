package nl.praegus.fitnesse.responders;
import fitnesse.FitNesseContext;
import fitnesse.authentication.SecureOperation;
import fitnesse.authentication.SecureResponder;
import fitnesse.http.Request;
import fitnesse.http.Response;
import fitnesse.http.SimpleResponse;
import fitnesse.reporting.history.TestHistory;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class DashboardTestHistoryResponder implements SecureResponder{

    private FitNesseContext context;

    public Response makeResponse(FitNesseContext context, Request request) throws UnsupportedEncodingException {
        this.context = context;
        File resultsDirectory = context.getTestHistoryDirectory();
        String pageName = request.getResource();
        TestHistory testHistory = new TestHistory(resultsDirectory, pageName);

        try {
            return DashboardTestHistoryResponse(testHistory, request, pageName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return null;
    }

    private SimpleResponse DashboardTestHistoryResponse(TestHistory testHistory, Request request, String pageName) throws IOException, ParserConfigurationException, SAXException {


        SimpleResponse response = new SimpleResponse();

        response.setContent(String.valueOf(testHistory));
        return response;
    }

    @Override
    public SecureOperation getSecureOperation() {
        return null;
    }



}