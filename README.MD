[![Maven Central](https://img.shields.io/maven-central/v/nl.praegus/toolchain-fitnesse-plugin.svg?maxAge=21600)](https://mvnrepository.com/artifact/nl.praegus/toolchain-fitnesse-plugin)

You can go to the [DEVELOPMENT_GUIDE.MD](./DEVELOPMENT_GUIDE.MD), for information about how to get started with the Toolchain FitNesse Plugin.

# Toolchain FitNesse Plugin

This plug-in contains the following extensions to FitNesse (20190620 or later):
 * Bootstrap-plus theme
 * AutoComplete responder
 * Pausing table (be sure to include toolchain-fixtures and set Pause Test Fixture as a library)
 * Conditional script table
 * Conditional scenario table
 * Looping scenario table
 * !VersionChecker symbol to check your dependencies against maven central (if your project uses Maven)
 * !includeIfAvailable symbol, that only includes a page if it's there, without throwing exceptions, or providing a create-link if it's not (useful when you have symlinked scopes)
 * !Fake symbol, allowing for advanced random test data,implementing https://github.com/DiUS/java-faker as a wiki symbol. See: https://github.com/praegus/toolchain-fitnesse-plugin/releases/tag/toolchain-fitnesse-plugin-2.0.9 for examples of usage.
 
 

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

### Toolchain FitNesse plugin Package project
This project contains two Sub projects which can be used to build the Toolchain FitNesse plugin and host an FitNesse instance

in this the Toolchain FitNesse plugin is the java project and the Toolchain FitNesse plugin test contains an FitNesse instance.

the Toolchain FitNesse plugin package is not used for anything except containing the two subprojects.
