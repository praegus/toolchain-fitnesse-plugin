package nl.praegus.fitnesse.junit;

import nl.hsac.fitnesse.junit.HsacFitNesseRunner;
import org.junit.runners.model.InitializationError;


public class ToolchainRunner extends HsacFitNesseRunner {
    public ToolchainRunner(Class<?> suiteClass) throws InitializationError {
        super(suiteClass);
        System.getProperties().setProperty("nodebug", "true");
    }

}
