package nl.praegus.fitnesse.responders;

import fitnesse.FitNesseContext;
import fitnesse.authentication.SecureOperation;
import fitnesse.authentication.SecureResponder;
import fitnesse.authentication.SecureWriteOperation;
import fitnesse.http.Request;
import fitnesse.http.Response;
import fitnesse.http.SimpleResponse;
import fitnesse.responders.editing.EditResponder;
import fitnesse.responders.editing.MergeResponder;
import fitnesse.responders.editing.SaveRecorder;
import fitnesse.wiki.*;
import org.json.JSONObject;

import static fitnesse.responders.editing.SaveRecorder.changesShouldBeMerged;

public class UpdateTagsResponder implements SecureResponder {

    @Override
    public Response makeResponse(FitNesseContext context, Request request) throws Exception {
        long editTimeStamp = getEditTime(request);
        long ticketId = getTicketId(request);

        String resource = request.getResource();
        WikiPage page = getPage(resource, context);
        PageData data = page.getData();

        setData(data, request.getInput(EditResponder.SUITES));
        SaveRecorder.pageSaved(page, ticketId);

        VersionInfo commitRecord = page.commit(data);
        context.recentChanges.updateRecentChanges(page);

        if (changesShouldBeMerged(editTimeStamp, ticketId, page))
            return new MergeResponder(request).makeResponse(context, request);
        else {
            SimpleResponse response = new SimpleResponse();
            JSONObject resp = new JSONObject();
            resp.put("status", "OK");
            response.setMaxAge(0);
            response.setStatus(200);
            response.setContentType("application/json");
            response.setContent(resp.toString(3));
            if (commitRecord != null) {
                response.addHeader("Current-Version", commitRecord.getName());
            }
            return response;
        }
    }

    private long getTicketId(Request request) {
        if (!request.hasInput(EditResponder.TICKET_ID))
            return 0;
        String ticketIdString = request.getInput(EditResponder.TICKET_ID);
        return Long.parseLong(ticketIdString);
    }

    private long getEditTime(Request request) {
        if (!request.hasInput(EditResponder.TIME_STAMP))
            return 0;
        String editTimeStampString = request.getInput(EditResponder.TIME_STAMP);
        return Long.parseLong(editTimeStampString);
    }

    private WikiPage getPage(String resource, FitNesseContext context) {
        WikiPagePath path = PathParser.parse(resource);
        PageCrawler pageCrawler = context.getRootPage().getPageCrawler();
        WikiPage page = pageCrawler.getPage(path);
        if (page == null)
            page = WikiPageUtil.addPage(context.getRootPage(), PathParser.parse(resource));
        return page;
    }

    private void setData(final PageData data, final String suites) {
        data.setOrRemoveAttribute("Suites", suites);
    }

    @Override
    public SecureOperation getSecureOperation() {
        return new SecureWriteOperation();
    }
}
