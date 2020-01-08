package nl.praegus.fitnesse.responders;
import fitnesse.FitNesseContext;
import fitnesse.authentication.SecureOperation;
import fitnesse.authentication.SecureResponder;
import fitnesse.http.Request;
import fitnesse.http.Response;
import fitnesse.http.SimpleResponse;
import org.json.XML;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import util.FileUtil;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class FitNesseVersionResponder implements SecureResponder{

    private SimpleResponse FitNesseVersionResponse() throws IOException, ParserConfigurationException, SAXException {
     SimpleResponse response = new SimpleResponse();

       File thisFilePath = new File(System.getProperty("user.dir"));
       File pom = new File(thisFilePath.getParent()+"/pom.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document pomContent = dBuilder.parse(pom);
        response.setContent(String.valueOf(pomContent));
        return response;
    }

    @Override
    public SecureOperation getSecureOperation() {
        return null;
    }

    @Override
    public Response makeResponse(FitNesseContext fitNesseContext, Request request) throws Exception {
        return FitNesseVersionResponse();
    }
}
