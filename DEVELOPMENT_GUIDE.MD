# Getting Started with Toolchain FitNesse Plugin
This is meant for people who want to develop for the Toolchain FitNesse Plugin.

## Running the code 

1. Put the correct Toolchain Plugin version in the pom.xml.
2. Press on "Maven" on the right side.
    2. Open the folder “Lifecycle”.
    2. Select “clean” and “install”. 
    2. Press on “Run Maven Build”.

The Plugin required to be included in a project with the original FitNesse. 

## Code Set Up 

### README

There is a README in both projects that shows all current features. This README needs to be updated when a new feature is developed.   

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
    - Don't add unnecessary enters, but place them in places to provide more a beter overall picture. 
    
- Spaces
    - Place spaces where necessary. For example do not write an if statement like this: “if(saveButton==true)” but like this “if (saveButton == true)“.
     

### Unit testing
#### Naming
Straightforward naming that indicates what the test is or tests and what is expected of that test.

#### Set Up
Create a clear layout by first setting down all the necessary variables, then add a white line, then add the function/execution, another white line and finally the test itself.
