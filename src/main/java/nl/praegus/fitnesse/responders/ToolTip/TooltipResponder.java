package nl.praegus.fitnesse.responders.ToolTip;


import fitnesse.FitNesseContext;
import fitnesse.Responder;
import fitnesse.authentication.SecureOperation;
import fitnesse.authentication.SecureReadOperation;
import fitnesse.http.Request;
import fitnesse.http.Response;
import fitnesse.http.SimpleResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class TooltipResponder implements Responder {
    private FitNesseContext context;
    ToolTipList toolTipList = new ToolTipList();

    public TooltipResponder() throws IOException {
    }

    @Override
    public Response makeResponse(FitNesseContext context, Request request) throws UnsupportedEncodingException {
        this.context = context;
        return makeRecentTestHistoryResponse();
    }

    private Response makeRecentTestHistoryResponse() throws UnsupportedEncodingException {
         String toolTip = toolTipList.getToolTip();

        SimpleResponse response = new SimpleResponse();

        response.setContent(String.valueOf(toolTip));
        return response;
    }


    public SecureOperation getSecureOperation() {
        return new SecureReadOperation();
    }
}
