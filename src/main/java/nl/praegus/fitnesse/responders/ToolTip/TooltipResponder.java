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
    private static final Tooltips tooltips = new Tooltips();

    @Override
    public Response makeResponse(FitNesseContext context, Request request) throws IOException { return makeToolTipResponse(); }

    private Response makeToolTipResponse() throws UnsupportedEncodingException {

        SimpleResponse response = new SimpleResponse();
        String tooltip = tooltips.getRandomTooltip();

        response.setContent(tooltip);
        return response;
    }

    public SecureOperation getSecureOperation() {
        return new SecureReadOperation();
    }
}
