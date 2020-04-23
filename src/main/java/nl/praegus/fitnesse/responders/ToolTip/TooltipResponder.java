package nl.praegus.fitnesse.responders.ToolTip;

import fitnesse.FitNesseContext;
import fitnesse.Responder;
import fitnesse.authentication.SecureOperation;
import fitnesse.authentication.SecureReadOperation;
import fitnesse.http.Request;
import fitnesse.http.Response;
import fitnesse.http.SimpleResponse;

import java.io.*;

public class TooltipResponder implements Responder {
    ToolTips  toolTips = new ToolTips();

    @Override
    public Response makeResponse(FitNesseContext context, Request request) throws IOException {

        return makeToolTipResponse();
    }

    private Response makeToolTipResponse() throws IOException {
        String toolTip = toolTips.getRandomToolTip();
        SimpleResponse response = new SimpleResponse();

        response.setContent(toolTip);
        return response;
    }

    public SecureOperation getSecureOperation() {
        return new SecureReadOperation();
    }
}
