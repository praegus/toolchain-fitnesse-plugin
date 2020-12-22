# Run tests from the command line
Test can be run by clicking the 'Test' button shown on the test pages. There are, however, many use cases the require tests
being run from the command line. For example when the tests are run as part of a build pipeline. 


## Add the FixtureDebugTest java class
To run tests from the command line some extra configuration is needed. The setup consists of adding a file with a small
amount of Java code the right folder. 

* Create a new package named ```nl.praegus``` in the folder ```new-praegus-toolchain-project/src/test/java```.

![New package](/images/getting-started/package.png)

![New package](/images/getting-started/package-name.png)

* in the new package create a file called ```FixtureDebugTest.java``` and paste the code below 

??? note "FixtureDebugTest.java code"
    ```JAVA
    
    package nl.praegus;
    
    import fitnesse.junit.FitNesseRunner;
    import nl.hsac.fitnesse.junit.HsacFitNesseRunner;
    import org.junit.runner.RunWith;
    
    /**
    * Test class to allow fixture code to be debugged, or run by build server.
      */
      @RunWith(HsacFitNesseRunner.class)
      @FitNesseRunner.Suite("HsacExamples.SlimTests.BrowserTests.OrangeBeardListenerSuite")
      public class FixtureDebugTest {
      }
    ```

![FixtureDebugTest.java](/images/getting-started/fixture-debug-test.png)

## The basic command to run a test suite
We are now ready to run tests from the command line. 
The basic command looks like this. It has to be run from the root of the project folder. The equals sign after 
-DfitnesseSuiteToRun should be followed by the name of an existing test suite should.
  
```mvn clean test-compile failsafe:integration-test failsafe:verify -DfitnesseSuiteToRun=<put-path-of-suite-here>```
  
The easiest way to try this command is by creating another IntelliJ run configuration. This way whe always have it at hand. 
How to add a configuration was already described [here](set-up-a-toolchain-project.md). We just add another one now. 

* Click the configuration dropdown and select 'Edit configurations'
![Add extra configuration](/images/getting-started/add-extra-configuration.png)
  
* Add a new Maven configuration
![New Maven configuration](/images/getting-started/command-line-config.png)

    1. Add an descriptive name
    2. Enter the commmand```clean test-compile failsafe:integration-test failsafe:verify -DfitnesseSuiteToRun=HsacExamples.SlimTests.HttpTests.JsonHttpTest```
    3. Click 'OK'.

* Now this configuration can be run by selecting it and click the 'Play' button. The result of the run will be shown in
the log. And will also be available in the folder ```target\fitnesse-results```. 