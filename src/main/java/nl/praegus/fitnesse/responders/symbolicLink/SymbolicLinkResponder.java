package nl.praegus.fitnesse.responders.symbolicLink;

import fitnesse.FitNesseContext;
import fitnesse.authentication.SecureOperation;
import fitnesse.authentication.SecureReadOperation;
import fitnesse.authentication.SecureResponder;
import fitnesse.http.Request;
import fitnesse.http.Response;
import fitnesse.http.SimpleResponse;
import fitnesse.wiki.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import static nl.praegus.fitnesse.responders.WikiPageHelper.loadPage;

public class SymbolicLinkResponder implements SecureResponder {

    @Override
    public Response makeResponse(FitNesseContext fitNesseContext, Request request) throws Exception {
        WikiPage wikiPage = loadPage(fitNesseContext, request.getResource(), request.getMap());

        return makeTagResponse(wikiPage);
    }

    public SimpleResponse makeTagResponse(WikiPage wikiPage) throws UnsupportedEncodingException {
        SimpleResponse response = new SimpleResponse();
        response.setMaxAge(0);
        response.setStatus(200);
        response.setContentType("application/json");
        response.setContent(getSymlink(wikiPage).toString());
        return response;
    }

    public JSONArray getSymlink(WikiPage page){
        return getSymlinkHelper(page);
    }

    private JSONArray getSymlinkHelper(WikiPage wikiPage) {
        JSONArray symboliclinkArray = new JSONArray();
        PageData data = wikiPage.getData();
        WikiPageProperty symLinksProperty = data.getProperties().getProperty(SymbolicPage.PROPERTY_NAME);
        if (symLinksProperty == null) {
            return new JSONArray();
        }
        String pagePath = wikiPage.getName();

        Set<String> symbolicLinkNames = symLinksProperty.keySet();
        for (String name : symbolicLinkNames) {
            JSONObject symboliclinkInformation = new JSONObject();

            symboliclinkInformation.put("pagePath", pagePath);
            symboliclinkInformation.put("linkName", name);
            symboliclinkInformation.put("linkPath", symLinksProperty.get(name));

            symboliclinkArray.put(symboliclinkInformation);
        }

        return symboliclinkArray;
    }

    @Override
    public SecureOperation getSecureOperation() {
        return new SecureReadOperation();
    }

}
