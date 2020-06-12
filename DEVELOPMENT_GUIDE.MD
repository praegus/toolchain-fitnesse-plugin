# Getting Started with the Toolchain FitNesse Plugin
This MD is meant for people who want to develop for the Toolchain FitNesse Plugin.

## Running the code 

The Toolchain Plugin can't be run on it's own. 
To run the Toolchain Plugin it's required to be included in a project with the [original FitNesse](https://mvnrepository.com/artifact/org.fitnesse/fitnesse). 

## Code Set Up 

### README

The README.MD shows all current features. This README needs to be updated when a new feature is developed.      

### Java

To overwrite or add new responders, symbols, decorators in the FitNesse Toolchain you need to adjust the PraegusPluginFeatureFactory class.

#### Responders

Responder is the operation that the FitNesse Toolchain can perform for you. 
The Responder is indicated in the URL such as "localhost:9090/FrontPage?editPage" where "?editPage" is the operation.

#### Symbols

Symbols is a special type of syntax that the user can use in the edit page.
The user can for example use "!VersionChecker" to add a version checker table on the current page. 
[HSAC symbols](https://github.com/fhoeben/hsac-fitnesse-plugin/tree/master/src/main/java/nl/hsac/fitnesse/symbols) was used as a example for creating this wiki symbol. 

### Unit testing

The written Java code needs to be tested. 
The Java testing framework "JUNIT" is used for unit testing the code. 
The created unit tests are located in the "test/java/nl/praegus/fitnesse" folder.
The Praegus repo has an integrated build that runs the tests.


## Code Conventions
### General
#### Notation
    
- Notation
    - Use Pascal casing when naming a Class and a Interfaces.
    - Use Camel casing when naming a Variables and a Methods.
    - Use Snake casing when naming a Constants and a Test.
    
    
#### Formating

- Enters
    - Don't add unnecessary enters, but place them in places to provide a better overall picture.
    
    
- Spaces
    - Place spaces where it's necessary. For example do not write an if statement like this: “if(newValue!=null)” but like this “if (newValue != null)“.
     

### Unit testing
#### Naming
The test name has to describe the goal of the test. 
For example; "When _[explain what happens in the test]_ then _[explain the expected result]_". 

#### Set Up
To create a clear layout you first need to set up al the necessary variables. 
Place after that the functions/executions. 
And finally the expected result of the test.
