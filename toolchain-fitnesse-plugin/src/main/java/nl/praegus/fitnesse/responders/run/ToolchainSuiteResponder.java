package nl.praegus.fitnesse.responders.run;

import fitnesse.reporting.BaseFormatter;
import fitnesse.responders.run.SuiteResponder;
import fitnesse.wiki.PageType;
import nl.praegus.fitnesse.reporting.ToolchainSuiteHtmlFormatter;

public class ToolchainSuiteResponder extends SuiteResponder {

    @Override
    protected BaseFormatter newHtmlFormatter() {
        return new ToolchainSuiteHtmlFormatter(page, PageType.fromWikiPage(page) == PageType.SUITE, response.getWriter());
    }

}
