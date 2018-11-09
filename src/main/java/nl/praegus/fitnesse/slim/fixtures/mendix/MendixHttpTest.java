package nl.praegus.fitnesse.slim.fixtures.mendix;

import nl.hsac.fitnesse.fixture.Environment;
import nl.hsac.fitnesse.fixture.slim.JsonHttpTest;
import nl.hsac.fitnesse.fixture.util.JsonHelper;

import java.util.List;
import java.util.Map;

import static org.apache.commons.text.StringEscapeUtils.escapeJava;

/**
 * Mendix-specific fixture extension of json http test to easily configure actions on mendix' REST interfaces
 */
public class MendixHttpTest extends JsonHttpTest {

    private String TPL_ACTION = "action";
    private String TPL_ACTIONNAME = "actionName";
    private String TPL_CHANGES = "changes";
    private String TPL_CONTEXT = "contextGuids";
    private String TPL_VALIDATION = "validationGuids";
    private String TPL_USER = "username";
    private String TPL_PASS = "password";
    private String TPL_GUIDS = "guids";
    private String TPL_XPATH = "xpath";
    private String TPL_SCHEMA = "schema";
    private String TPL_COUNT = "count";
    private String TPL_AGGREGATES = "aggregates";
    private String TPL_IDS = "ids";
    private String TPL_CONSTRAINTS = "constraints";
    private String TPL_APPLYTO = "applyto";
    private String TPL_SORT = "sort";
    private String TPL_GRIDID = "gridid";
    private String TPL_OBJECTS = "objects";
    private String TPL_PARAMS = "params";
    private String TPL_ASYNCID = "asyncid";

    private String csrfToken;

    private String actionName;
    private String action;
    private String changes;
    private String contextGuids;
    private String validationGuids;
    private String mendixAppUrl;
    private String xpath;
    private String schema;
    private String ids;
    private Boolean count;
    private Boolean aggregates;
    private String constraints;
    private String applyto;
    private String sort;
    private String gridid;
    private String objects;
    private String params;
    private String asyncid;

    private String MENDIX_TEMPLATE = "mendixTemplate.ftl.json";
    private static Environment environment = Environment.getInstance();
    private static JsonHelper h = new JsonHelper();

    public void setActionName (String action) {
        actionName = action;
    }
    public void setAction (String newAction) {
        action = newAction;
    }
    public void setChanges (String newChanges) {
        changes = newChanges;
    }
    public void setContext (String newContext) {
        contextGuids = newContext;
    }
    public void setValidationGuids (String newValidationGuids) {
        validationGuids = newValidationGuids;
    }
    public void setMendixUrl(String url) {
        mendixAppUrl = url + "/xas/";
    }
    public void setXpath (String newXpath) { xpath = newXpath; }
    public void setSchema (String newSchema) { schema = newSchema; }
    public void setIds (String newIds) { ids = newIds; }
    public void setCount (Boolean newCount) {count = newCount; }
    public void setAggregates (Boolean newAggregates) { aggregates = newAggregates; }
    public void setConstraints (String newConstraints) {
        constraints = newConstraints;
    }
    public void setApplyto(String newApplyTo) {
        applyto = newApplyTo;
    }
    public void setSort (String newSort) {
        sort = newSort;
    }
    public void setGridid (String newGridid) {
        gridid = newGridid;
    }
    public void setObjects (String newObjects) {
        objects = newObjects;
    }
    public void setParams (String newParams) {
        params = newParams;
    }
    public void setAsyncid (String newAsyncid) {
        asyncid = newAsyncid;
    }

    public boolean executeAction() {
        template(MENDIX_TEMPLATE);
        if (null != actionName) {
            setValueFor("executeaction", TPL_ACTION);
            setValueFor(actionName, TPL_ACTIONNAME);
        }
        if (null != action) {
            setValueFor(action, TPL_ACTION);
        }
        if (null != changes) {
            setValueFor(changes, TPL_CHANGES);
        }
        if (null != contextGuids) {
            setValueFor(contextGuids, TPL_CONTEXT);
        }
        if (null != validationGuids) {
            setValueFor(validationGuids, TPL_VALIDATION);
        }
        if (null != xpath) {
            setValueFor(xpath, TPL_XPATH);
        }
        if (null != schema) {
            setValueFor(schema, TPL_SCHEMA);
        }
        if (null != ids) {
            setValueFor(ids, TPL_IDS);
        }
        if (null != count) {
            setValueFor(count, TPL_COUNT);
        }
        if (null != aggregates) {
            setValueFor(aggregates, TPL_AGGREGATES);
        }
        if (null != constraints) {
            setValueFor(constraints, TPL_CONSTRAINTS);
        }
        if (null != applyto) {
            setValueFor(applyto, TPL_APPLYTO);
        }
        if (null != sort) {
            setValueFor(sort, TPL_SORT);
        }
        if (null != gridid) {
            setValueFor(gridid, TPL_GRIDID);
        }
        if (null != objects) {
            setValueFor(objects, TPL_OBJECTS);
        }
        if (null != params) {
            setValueFor(params, TPL_PARAMS);
        }
        if (null != asyncid) {
            setValueFor(asyncid, TPL_ASYNCID);
        }
        return performConfiguredMendixRequest();
    }

    public boolean deleteItem(String guid) {
        template(MENDIX_TEMPLATE);
        setValueFor("delete", TPL_ACTION);
        setValueFor("\"" + guid + "\"", TPL_GUIDS);
        return performConfiguredMendixRequest();
    }

    public boolean logInWithUserAndPassword (String user, String password) {
        boolean result;
        try{
            template(MENDIX_TEMPLATE);
            setValueFor("login", TPL_ACTION);
            setValueFor(user, TPL_USER);
            setValueFor(escapeJava(password), TPL_PASS);
            result = performConfiguredMendixRequest();
            setCsrfToken(jsonPath("csrftoken").toString());
        } catch (Exception e) {
            result = false;
            System.err.println("An Error Occurred. Request:\n" + request() + "\n\nResponse:\n" + rawResponse());
        }
        return result;
    }

    public void setCsrfToken(String token) {
        csrfToken = token;
    }

    public boolean logOut () {
        template(MENDIX_TEMPLATE);
        setValueFor("logout", TPL_ACTION);
        return performConfiguredMendixRequest();

    }

    private boolean performConfiguredMendixRequest() {
        boolean result;
        setValueForHeader("application/json", "content-type");
        if(null != csrfToken) {
            setValueForHeader(csrfToken, "X-Csrf-Token");
        }
        result = postTemplateTo(mendixAppUrl);
        resetRequestData();
        return result;
    }

    private void resetRequestData() {
        actionName = null;
        action = null;
        xpath = null;
        schema = null;
        ids = null;
        aggregates = null;
        count = null;
        changes = null;
        contextGuids = null;
        validationGuids = null;
        constraints = null;
        applyto = null;
        gridid = null;
        sort = null;
        objects = null;
        params = null;
        asyncid = null;
        setValueFor(null, TPL_USER);
        setValueFor(null, TPL_PASS);
        setValueFor(null, TPL_ACTION);
        setValueFor(null, TPL_ACTIONNAME);
        setValueFor(null, TPL_VALIDATION);
        setValueFor(null, TPL_CONTEXT);
        setValueFor(null, TPL_CHANGES);
        setValueFor(null, TPL_GUIDS);
        setValueFor(null, TPL_XPATH);
        setValueFor(null, TPL_SCHEMA);
        setValueFor(null, TPL_IDS);
        setValueFor(null, TPL_COUNT);
        setValueFor(null, TPL_AGGREGATES);
        setValueFor(null, TPL_CONSTRAINTS);
        setValueFor(null, TPL_APPLYTO);
        setValueFor(null, TPL_GRIDID);
        setValueFor(null, TPL_SORT);
        setValueFor(null, TPL_OBJECTS);
        setValueFor(null, TPL_PARAMS);
        setValueFor(null, TPL_ASYNCID);
    }

    public void saveToSymbol(String val, String key) {
        environment.setSymbol(key, val);
    }

    public String getFromSymbol(String key) {
        return environment.getSymbol(key);
    }


    private List<Object> getAllMatchesIn(String path, String json) {
        String jsonPath = getPathExpr(path);
        return getPathHelper().getAllJsonPath(json, jsonPath);
    }

    public String jsonPathValues(String expr){
        String responseString = getResponseBody();
        return  jsonPathValuesIn(expr, responseString);
    }

    public String jsonPathValuesIn(String expr, String json) {
        String result = null;
        List<Object> allJsonPath = getAllMatchesIn(expr, json);
        if (allJsonPath != null && !allJsonPath.isEmpty()) {
            int count = 1;
            StringBuilder sb = new StringBuilder();
            for (Object match : allJsonPath) {
                sb.append(match);
                if(count < allJsonPath.size()) {
                    sb.append("\r\n");
                    count++;
                }
            }
            result = sb.toString();
        }
        return result;

    }

    public String rawResponse(){
        return getResponseBody();
    }


    public Map<String, Object> getMap() {
        executeAction();
        return h.jsonStringToMap(getResponseBody());
    }





}
