[![Maven Central](https://img.shields.io/maven-central/v/nl.praegus/toolchain-fitnesse-plugin.svg?maxAge=21600)](https://mvnrepository.com/artifact/nl.praegus/toolchain-fitnesse-plugin)
# Toolchain FitNesse Plugin

This plug-in contains the following extensions to FitNesse (20190620 or later):
 * Bootstrap-plus theme
 * AutoComplete responder
 * Pausing table (be sure to include toolchain-fixtures and set Pause Test Fixture as a library)
 * Conditional script table
 * Conditional scenario table
 * Looping scenario table

### Bootstrap-plus
See https://github.com/praegus/fitnesse-bootstrap-plus-theme

### Table Decorator
Assigns custom classes to different table types

### AutoComplete responder
See https://github.com/praegus/fitnesse-autocomplete-responder
### Update tags responder
Allows setting only new tags on a testpage
### Table of contents responder
Returns the full document tree as json from the level it's called from

### Pausing table
#### Usage
`| debug script |` instead of `| script |`
This will pause execution before each script table row and prompt the user to continue of stop the test, informing the user what the next instruction will be.

Note: make sure to not commit these tables when using tests in a CI run. The debug will timeout in 10mins by default. Timeout can be set using system property fitnesse.debugscript.timeout

## Extra table types

### Conditional script table
##### Usage
```
| conditional script         |
| boolean expression         |
| normal script instructions |
```
Expressions can contain slim symbols (as JS object, reference without the $) or wiki variables. A Javascript executor is used.
When the expression is true, the table will continue as a normal script table.
Otherwise, all instructions in the table will be skipped.

Please, use the conditional script table responsibly. Every time you need it, you're likely to be compensating for a test data problem..

### Conditional scenario table
A conditional scenario is only executed if the (Javascript) expression in the first row/cell evaluates to true.
The expression can reference slim symbols by name, as if they were variables.

##### Usage
```
| conditional scenario|do |stuff| if some variable equals abc |
| someVar == 'abc'                                            |
| script steps                                                |
```
##### Limitations

Conditional scenario's can be used in the same script table, however in that case the expression will only know the slim context as it exists when the table is called.
The expression context cannot be updated within the same table.

This means that setting a symbol in a script or conditional scenario has to happen in a table before the table that calls the conditional scenario table.

### Looping scenario table

Note that the looping scenario accepts only one argument and will ignore any others. parameterization of underlying scenario's should be done via slim symbols. These can contain any number of arguments.
The table keeps a loop counter (currentLoopCount). If you need to access it in your scenario's, copy its value from the slim runner to the wiki by assigning it to itself in your loop (workaround for Slim server and runner sync issue)

##### Usage
```
| looping scenario | loop over | dataToLoop |
| script steps                              |

| script                             |
| loop over | item 1, item 2, item 3 |
```
The above example will execute the scenario loop over three times, once for each (comma separated) item.

Contact https://www.praegus.nl for more info.


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
The tests are integrated with a automated build in Github. When creating a pull request to the Praegus.

## Code Conventions
### General
#### Naming and notation

- Naming
    - Do not change the existing name from the FitNesse master.
    
    
- Notation
    - Use Snake casing when naming tests.
    - Use Pascal casing when naming functions.
    - Use Camel casing when naming variables.
    
#### Formating

- Format button
    - Using the format button, everyone will get their code to look well arranged.
    
    
- Enters
    - Don't add unnecessary enters, but place them in places to provide more a beter overall picture. Just like with each new CSS element and between functions.
    
    
- Spaces
    - Place spaces where necessary. For example do not write an if statement like this: “if(saveButton==true){}” but like this “if (saveButton == true) { }“. As another example, do not write a variable like this ”const saveButton=false;" but like this “const saveButton = false;”
    
    
- Quotes
    - If you need to add quotes, use single quotes.
    
    
### Unit testing
#### Naming
Straightforward naming that indicates what the test is or tests and what is expected of that test.

#### Set Up
Create a clear layout by first setting down all the necessary variables, then add a white line, then add the function/execution, another white line and finally the test itself.
