# HSAC FitNesse Fixtures

The [HSAC FitNesse Fixtures](https://github.com/fhoeben/hsac-fitnesse-fixtures) provides the fixtures for testing websites 
and Web-API's.


If you want to debug your test, you can use the following `FixtureDebugTest.java` as a starting point:

``` java
package nl.praegus;

import fitnesse.junit.FitNesseRunner;
import nl.hsac.fitnesse.junit.HsacFitNesseRunner;
import org.junit.runner.RunWith;

/**
 * Test class to allow fixture code to be debugged, or run by build server.
 */
@RunWith(HsacFitNesseRunner.class)
@FitNesseRunner.Suite("MyFirstSuite.BackEndTests.FirstTest")
public class FixtureDebugTest {
}
```

The important part is this line:

``` java hl_lines="10 11"
package nl.praegus;

import fitnesse.junit.FitNesseRunner;
import nl.hsac.fitnesse.junit.HsacFitNesseRunner;
import org.junit.runner.RunWith;

/**
 * Test class to allow fixture code to be debugged, or run by build server.
 */
@RunWith(HsacFitNesseRunner.class)
@FitNesseRunner.Suite("MyFirstSuite.BackEndTests.FirstTest")
public class FixtureDebugTest {
}
```
