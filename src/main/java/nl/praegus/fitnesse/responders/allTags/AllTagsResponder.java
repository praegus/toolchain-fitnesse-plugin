package nl.praegus.fitnesse.responders.allTags;

import fitnesse.FitNesseContext;
import fitnesse.authentication.SecureOperation;
import fitnesse.authentication.SecureReadOperation;
import fitnesse.authentication.SecureResponder;
import fitnesse.http.Request;
import fitnesse.http.Response;
import fitnesse.http.SimpleResponse;
import fitnesse.wiki.*;
import fitnesse.wikitext.parser.SourcePage;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

import static nl.praegus.fitnesse.responders.WikiPageHelper.loadPage;

public class AllTagsResponder implements SecureResponder {
    private JSONObject tagObject = new JSONObject();

    @Override
    public Response makeResponse(FitNesseContext fitNesseContext, Request request) throws Exception {
        WikiSourcePage sourcePage = new WikiSourcePage(loadPage(fitNesseContext, request.getResource(), request.getMap()));

        return makeTagResponse(sourcePage);
    }

    public SimpleResponse makeTagResponse(SourcePage sourcePage) throws UnsupportedEncodingException {
        tagObject.put("Tags", getPageTags(sourcePage));

        SimpleResponse response = new SimpleResponse();
        response.setMaxAge(0);
        response.setStatus(200);
        response.setContentType("application/json");
        response.setContent(tagObject.toString(3));
        return response;
    }

    public Set<String> getPageTags(SourcePage page){
        return getPageTagsHelper(page, new HashSet<>());
    }

    private Set<String> getPageTagsHelper(SourcePage page, Set<String> allTagsArray) {
        String[] tags = page.getProperty(WikiPageProperty.SUITES).split(", ");

        allTagsArray.addAll(Arrays.stream(tags).filter(t -> t.length() > 0).collect(Collectors.toList()));

        for (SourcePage p : getPageChildren(page)) {
            getPageTagsHelper(p, allTagsArray);
        }

        return allTagsArray;
    }

    @Override
    public SecureOperation getSecureOperation() {
        return new SecureReadOperation();
    }

    private List<SourcePage> getPageChildren(SourcePage parent) {
        return new ArrayList<>(parent.getChildren());
    }


}
