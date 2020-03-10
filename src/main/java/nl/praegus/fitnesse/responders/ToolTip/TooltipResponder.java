package nl.praegus.fitnesse.responders.ToolTip;

import fitnesse.FitNesseContext;
import fitnesse.Responder;
import fitnesse.authentication.SecureOperation;
import fitnesse.authentication.SecureReadOperation;
import fitnesse.http.Request;
import fitnesse.http.Response;
import fitnesse.http.SimpleResponse;

import java.io.UnsupportedEncodingException;

public class TooltipResponder implements Responder {
    ToolTips toolTips = new ToolTips();

    public TooltipResponder() {
    }

    @Override
    public Response makeResponse(FitNesseContext context, Request request) throws UnsupportedEncodingException {
        return makeToolTipResponse();
    }

    private Response makeToolTipResponse() throws UnsupportedEncodingException {
        String toolTip = toolTips.getToolTip();
        SimpleResponse response = new SimpleResponse();

        response.setContent(String.valueOf(toolTip));
        return response;
    }


    public SecureOperation getSecureOperation() {
        return new SecureReadOperation();
    }
}
