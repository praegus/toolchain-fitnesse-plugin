package nl.praegus.fitnesse.responders.ToolTip;

import fitnesse.FitNesseContext;
import fitnesse.Responder;
import fitnesse.authentication.SecureOperation;
import fitnesse.authentication.SecureReadOperation;
import fitnesse.http.Request;
import fitnesse.http.Response;
import fitnesse.http.SimpleResponse;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TooltipResponder implements Responder {
    ToolTips toolTips = new ToolTips();

    @Override
    public Response makeResponse(FitNesseContext context, Request request) throws IOException {
        return makeToolTipResponse();
    }

    private Response makeToolTipResponse() throws UnsupportedEncodingException {
        String toolTip = toolTips.getToolTip();
        SimpleResponse response = new SimpleResponse();

        response.setContent(toolTip);
        return response;
    }

    public SecureOperation getSecureOperation() {
        return new SecureReadOperation();
    }
}
