Package: nl.praegus.fitnesse.slim.fixtures.util

Calculator is a utility fixture and will in most cases be included as a library:

```
|import                                |
|nl.praegus.fitnesse.slim.fixtures.util|

|library   |
|calculator|
```

??? info "using calculator with specified precision"
    Calling the constructor with a specific number of decimals to return as precision:
    ```
    |library     |
    |calculator|4|
    ```
    Will return 4 decimals


using this configuration, Slim can calculate complex mathematical expressions at runtime. 
Any expression can be passed as a simple String. 

Some Examples:

```
|script|calculator|5                                  |
|check |calculate |1+1                      |2        |
|check |calculate |sum(i, 1, 10, 2*i^2 + pi)|801.41593|
|check |calculate |2+(3-5)^2                |6        |
```

Reference: Calculator uses <a href="https://github.com/mariuszgromada/MathParser.org-mXparser" target="_blank">MathParser.org-mXparser</a> to parse expressions.