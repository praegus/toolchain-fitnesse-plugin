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
    public JSONArray symboliclinkArray = new JSONArray();
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
        // Get Symlinks for the wiki page
        PageData data = wikiPage.getData();
        WikiPageProperty symLinksProperty = data.getProperties().getProperty(SymbolicPage.PROPERTY_NAME);

        // Loop to the names
        if (symLinksProperty != null) {
            for (String name : symLinksProperty.keySet()) {
                symboliclinkArray.put(getObject(name, wikiPage, symLinksProperty));
            }
        }

        // Get Symlinks from the children
        // TO DO: Get the source page en check if there are children


        return symLinksProperty != null ? symboliclinkArray : new JSONArray();
    }

    private JSONObject getObject(String name, WikiPage wikiPage, WikiPageProperty symLinksProperty) {
        JSONObject symboliclinkInformation = new JSONObject();
        symboliclinkInformation.put("pagePath", wikiPage.getName());
        symboliclinkInformation.put("linkName", name);
        symboliclinkInformation.put("linkPath", symLinksProperty.get(name));
        return symboliclinkInformation;
    }

    @Override
    public SecureOperation getSecureOperation() {
        return new SecureReadOperation();
    }

}
