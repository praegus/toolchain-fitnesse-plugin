package nl.praegus.fitnesse.slim.fixtures.web.mendix;

import nl.hsac.fitnesse.fixture.slim.web.BrowserTest;
import nl.hsac.fitnesse.fixture.slim.web.annotation.TimeoutPolicy;
import nl.hsac.fitnesse.fixture.slim.web.annotation.WaitUntil;
import nl.hsac.fitnesse.fixture.util.selenium.SeleniumHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Fixture class customized to test mendix web apps.
 */

public class MendixBrowserTest extends BrowserTest<WebElement> {
    private boolean waitForJquery = false;
    private int jqueryTimeout = 5; //Timeout in seconds
    private SeleniumHelper seleniumHelper = getEnvironment().getSeleniumHelper();
    private int delayBeforeValue = 0;
    private int inputDelay = 0;

    public void waitForJquery(boolean wait) {
        waitForJquery = wait;
    }

    public void setJqueryTimeout(int timeout) {
        jqueryTimeout = timeout;
    }

    public void setInputDelay(int delay) {
        inputDelay = delay;
    }

    public int getInputDelay() {
        return inputDelay;
    }

    @WaitUntil(TimeoutPolicy.RETURN_NULL)
    public Integer numberOfItems(String xPath) {
        if (xPath.startsWith("xpath=")) {
            xPath = xPath.substring(6);
        }
        return getSeleniumHelper().driver().findElements(By.xpath(xPath)).size();
    }

    @Override
    public boolean selectForIn(String value, String place, String container) {
        clickIn(place, container);
        return super.selectForIn(value, place, container);
    }

    @Override
    public boolean selectFor(String value, String place) {
        click(place);
        return super.selectFor(value, place);
    }


    public boolean clickItems(String listOfItems) {
        String[] items = listOfItems.split(";");
        for (String item : items) {
            if (!click(item.trim())) {
                return false;
            }
        }
        return true;
    }

    public String getCsrfToken() {
        return seleniumHelper.executeJavascript("return window.mx.session.sessionData.csrftoken").toString();
    }

    public void delayValueExtractionByMilliseconds(int millis) {
        delayBeforeValue = millis;
    }

    @WaitUntil(TimeoutPolicy.RETURN_NULL)
    public String valueOfInRowWhereIsInTable(String requestedColumnName, String selectOnColumn, String selectOnValue, String table) {
        //Search in the right table
        String xpathForHeadTable = xpathForTable(table, "head");
        String xpathForBodyTable = xpathForTable(table, "body");

        int selectColumnIndex = columnIndex(xpathForHeadTable, selectOnColumn);
        int requestedColumnIndex = columnIndex(xpathForHeadTable, requestedColumnName);

        String xpath = String.format("%s//td[%s][contains(.,'%s')]/parent::tr/td[%s]", xpathForBodyTable, selectColumnIndex, selectOnValue, requestedColumnIndex);
        WebElement element = seleniumHelper.findByXPath(xpath);
        String result = valueFor(element);
        if (result.length() == 0) {
            result = element.getAttribute("title");
        }
        return result;
    }

    @WaitUntil(TimeoutPolicy.RETURN_NULL)
    public String valueOfInRowWhereIs(String requestedColumnName, String selectOnColumn, String selectOnValue) {
        waitForJqueryIfNeeded();
        WebElement element = elementInRowWhereIs(requestedColumnName, selectOnColumn, selectOnValue);
        String result = valueFor(element);
        if (result.length() == 0) {
            result = element.getAttribute("title");
        }
        return result;
    }

    @Override
    public boolean clickInRowWhereIs(String requestedColumnName, String selectOnColumn, String selectOnValue) {
        return clickElement(elementInRowWhereIs(requestedColumnName, selectOnColumn, selectOnValue));
    }

    private WebElement elementInRowWhereIs(String requestedColumnName, String selectOnColumn, String selectOnValue) {
        //Search in the right table
        String xpathForHeadTable = "//table[contains(@class, 'mx-datagrid-head-table')]";
        String xpathForBodyTable = "//table[contains(@class, 'mx-datagrid-body-table')]";

        int selectColumnIndex = columnIndex(xpathForHeadTable, selectOnColumn);
        int requestedColumnIndex = columnIndex(xpathForHeadTable, requestedColumnName);

        String xpath = String.format("%s//td[%s][contains(.,'%s')]/parent::tr/td[%s]", xpathForBodyTable, selectColumnIndex, selectOnValue, requestedColumnIndex);
        waitForJqueryIfNeeded();
        return seleniumHelper.findByXPath(xpath);
    }

    private String xpathForTable(String table, String type) {
        int i = 1;
        String xpath = "";
        boolean displayed = false;
        WebElement el;
        while (!displayed) {
            xpath = String.format("(//*[text()='%s']/ancestor::div[@class='row']//table[contains(@class, '%s-table')])[%s]", table, type, String.valueOf(i));
            waitForJqueryIfNeeded();
            el = seleniumHelper.findByXPath(xpath);
            if (el == null) {
                xpath = String.format("//*[text()='%s']/ancestor::div[@class='row']//table[contains(@class, '%s-table')]", table, type);
                break;
            } else {
                displayed = el.isDisplayed();
                i++;
            }
        }
        return xpath;
    }

    private int columnIndex(String tableXpath, String columnName) {
        int precedingColumns = findElements(By.xpath(String.format("%s//th[contains(.,'%s')]/preceding-sibling::th", tableXpath, columnName))).size();
        return precedingColumns + 1;
    }

    @Override
    @WaitUntil(TimeoutPolicy.RETURN_NULL)
    public String valueOf(String place) {
        waitMilliseconds(delayBeforeValue);
        return super.valueOf(place);
    }

    @Override
    protected boolean enter(String value, String place, String container, boolean shouldClear) {
        WebElement element = getElementToSendValue(place, container);
        boolean result = element != null && isInteractable(element);

        if (result) {
            if (shouldClear) {
                element.clear();
            }
            waitMilliseconds(inputDelay);
            sendValue(element, value);
        }
        return result;
    }

    @Override
    protected WebElement getElement(String place, String container){
        waitForJqueryIfNeeded();
        return super.getElement(place, container);
    }

    @Override
    protected WebElement getElementToClick(String place, String container) {
        waitForJqueryIfNeeded();
        return super.getElementToClick(place, container);
    }

    private void waitForJqueryIfNeeded() {
        if(waitForJquery) {
            try{
                waitForJQuery(getSeleniumHelper().driver());
            } catch (WebDriverException e) {
                System.err.println("Exception when checking jquery status. Continue.");
            }
        }
    }

    private void waitForJQuery(WebDriver driver) {
        (new WebDriverWait(driver, jqueryTimeout)).until((ExpectedCondition<Boolean>) d -> {
            JavascriptExecutor js = (JavascriptExecutor) d;
            return (Boolean) executeScript("return window.jQuery.active == 0");
        });
    }
}