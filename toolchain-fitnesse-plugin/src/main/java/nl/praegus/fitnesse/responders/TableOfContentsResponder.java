package nl.praegus.fitnesse.responders;

import fitnesse.FitNesseContext;
import fitnesse.authentication.SecureOperation;
import fitnesse.authentication.SecureReadOperation;
import fitnesse.authentication.SecureResponder;
import fitnesse.http.Request;
import fitnesse.http.Response;
import fitnesse.http.SimpleResponse;
import fitnesse.wiki.PageType;
import fitnesse.wiki.WikiImportProperty;
import fitnesse.wiki.WikiPageProperty;
import fitnesse.wiki.WikiSourcePage;
import fitnesse.wikitext.SourcePage;
import org.json.JSONArray;
import org.json.JSONObject;
import util.GracefulNamer;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static nl.praegus.fitnesse.responders.WikiPageHelper.loadPage;

public class TableOfContentsResponder implements SecureResponder {

    public static final String SUITE_CLASS = "suite";
    public static final String TEST_CLASS = "test";
    public static final String STATIC_CLASS = "static";
    public static final String LINKED_CLASS = " linked";
    public static final String PRUNED_CLASS = " pruned";
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String HELP = "help";
    private static final String IS_SYMLINK = "isSymlink";
    private static final String TAGS = "tags";
    private static final String PATH = "path";
    private static final String CHILDREN = "children";
    private static final String DEPTH_PARAM = "depth";
    private final JSONArray tableOfContents = new JSONArray();

    @Override
    public Response makeResponse(FitNesseContext fitNesseContext, Request request) throws Exception {
        WikiSourcePage sourcePage = new WikiSourcePage(loadPage(fitNesseContext, request.getResource(), request.getMap()));
        Optional<Integer> maxDepth = getMaxDepthFromRequest(request);

        return makeTableOfContentsResponse(sourcePage, maxDepth);
    }

    private Optional<Integer> getMaxDepthFromRequest(Request request) {
        String depthParam = request.getInput(DEPTH_PARAM);
        if (depthParam != null) {
            try {
                return Optional.of(Integer.parseInt(depthParam));
            } catch (NumberFormatException e) {
                // If the depth parameter is not a valid integer, return empty Optional
                return Optional.empty();
            }
        }
        return Optional.empty(); // Return empty Optional when no depth parameter is present
    }

    private SimpleResponse makeTableOfContentsResponse(SourcePage sourcePage, Optional<Integer> maxDepth) throws UnsupportedEncodingException {
        tableOfContents.put(getPageInfo(null, sourcePage, maxDepth, 0));
        SimpleResponse response = new SimpleResponse();
        response.setMaxAge(0);
        response.setStatus(200);
        response.setContentType("application/json");
        response.setContent(tableOfContents.toString(3));

        return response;
    }

    private Collection<SourcePage> getSortedChildren(SourcePage parent) {
        ArrayList<SourcePage> result = new ArrayList<>(parent.getChildren());
        Collections.sort(result);
        return result;
    }

    private JSONObject getPageInfo(SourcePage parent, SourcePage page, Optional<Integer> maxDepth, int currentDepth) {
        JSONObject pageInfo = new JSONObject();
        pageInfo.put(NAME, GracefulNamer.regrace(page.getName()));
        pageInfo.put(TYPE, getBooleanPropertiesClasses(page));
        pageInfo.put(HELP, page.getProperty(WikiPageProperty.HELP));
        String[] tags = page.getProperty(WikiPageProperty.SUITES).split("\\s*,\\s*");

        pageInfo.put(IS_SYMLINK, parent instanceof WikiSourcePage && ((WikiSourcePage) parent).hasSymbolicLinkChild(page.getName()));

        for (String tag : tags) {
            if (!tag.isEmpty()) {
                pageInfo.append(TAGS, tag);
            }
        }

        pageInfo.put(PATH, page.getFullPath());

        // If maxDepth is not present (empty Optional), or if we haven't reached the maximum depth
        // then add children
        if (!maxDepth.isPresent() || currentDepth < maxDepth.get()) {
            for (SourcePage p : getSortedChildren(page)) {
                pageInfo.append(CHILDREN, getPageInfo(page, p, maxDepth, currentDepth + 1));
            }
        }

        return pageInfo;
    }

    private String getBooleanPropertiesClasses(SourcePage sourcePage) {
        String result = "";
        if (sourcePage.hasProperty(PageType.SUITE.toString())) {
            result += SUITE_CLASS;
        } else if (sourcePage.hasProperty(PageType.TEST.toString())) {
            result += TEST_CLASS;
        } else {
            result += STATIC_CLASS;
        }

        if (sourcePage.hasProperty(WikiImportProperty.PROPERTY_NAME)) {
            result += LINKED_CLASS;
        }
        if (sourcePage.hasProperty(WikiPageProperty.PRUNE)) {
            result += PRUNED_CLASS;
        }
        return result;
    }

    @Override
    public SecureOperation getSecureOperation() {
        return new SecureReadOperation();
    }
}
