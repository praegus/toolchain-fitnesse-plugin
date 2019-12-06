[![Maven Central](https://img.shields.io/maven-central/v/nl.praegus/toolchain-fitnesse-plugin.svg?maxAge=21600)](https://mvnrepository.com/artifact/nl.praegus/toolchain-fitnesse-plugin)
## Toolchain FitNesse Plugin

This plug-in contains the following extensions to FitNesse (20190620 or later):
 * Bootstrap-plus theme
 * AutoComplete responder
 * Pausing table (be sure to include toolchain-fixtures and set Pause Test Fixture as a library)
 * Conditional script table

### Bootstrap-plus
See https://github.com/praegus/fitnesse-bootstrap-plus-theme

### AutoComplete responder
See https://github.com/praegus/fitnesse-autocomplete-responder

### Pausing table
#### Usage
`| debug script |` instead of `| script |`
This will pause execution before each script table row and prompt the user to continue of stop the test, informing the user what the next instruction will be.

Note: make sure to not commit these tables when using tests in a CI run, or better yet, set a system property called `nodebug` and assign it the value `true` when running your tests. This will cause a pause call to stop the test (instead of causing an infinite wait).

### Conditional script table
#### Usage
```
| conditional script         |
| boolean expression         |
| normal script instructions |
```
Expressions can contain slim symbols or wiki variables. A Javascript executor is used.
When the expression is true, the table will continue as a normal script table.
Otherwise, all instructions in the table will be skipped.

Please, use the conditional script table responsibly. Every time you need it, you're likely to be compensating for a test data problem..

Contact https://www.praegus.nl for more info.
