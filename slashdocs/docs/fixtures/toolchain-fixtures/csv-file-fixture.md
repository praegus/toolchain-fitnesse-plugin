Package to import: _nl.praegus.fitnesse.slim.fixtures_

CSV File fixture is an extension to FileFixture that allows accessing CSV files as tabular data.

??? info "Example wiki page"
    ```
    |Import                                |
    |nl.praegus.fitnesse.slim.fixtures     |
    
    !*> CSV DATA
    !define csv {Name,Postcode,Creditcard,Identification,Birthday
                 Sara Salinas,78442,4408 860 40 5167,1676060566099,27/05/1972
                 Thomas Rogers,50004,492951 9091443431,1699022435799,29/10/1975
                 Alfreda Lawrence,43814,453235 9337067507,1633060397599,24/07/1972
                 Aladdin Baxter,4724 QU,4929 7426 3808 5917,1618041459199,12/05/1980
                 Daryl Montgomery,41200-524,471694 3020128193,1608011492699,09/11/1981
                 Jasper Hurley,64-488,4716094087196929,1640112045699,01/12/1986
                 Tiger Wilkinson,KP89 4CY,4916 625 59 5965,1653022312099,08/11/1975
                 Cherokee King,43-178,4916439411671014,1617061300099,05/10/1985
                 Justine Roman,7286,4024007154517,1655102934199,12/07/1979}
    *!
    
    |script      |csv file fixture             |
    |create      |csvfile.csv|containing|${csv}|
    |set csv file|testdata.csv                 |
    
    -| script|
    |# '''Find data in a csv table'''                                             |
    |check |name of column|0         |Name                                        |
    |check |name of column|1         |Postcode                                    |
    |check |name of column|2         |Creditcard                                  |
    |check |name of column|3         |Identification                              |
    |check |name of column|4         |Birthday                                    |
    |$name=|value of      |Name      |in row number|10                            |
    |check |value of      |Creditcard|in row where |Name|is|$name|4916 795 75 6256|
    |show  |value of      |$name                                                  |
    
    -|script|
    |# '''Getting data rows as a map'''                                |
    |$testPerson=|data in row where|Identification    |is|1653022312099|
    |check       |value of         |$`testPerson.Name`|Tiger Wilkinson |
    |show        |data in row      |8                                  |
    |show        |content of       |testdata.csv                       |
    
    
    -|script|
    |delete|csvfile.csv|
    ```

## Methods

### set separator (separator)
Define a separator character to use. Defaults to comma.

### set csv file (file)
Set a file to work with.

### value of (column) in row where (another column) is (some value)
Get the value of a cell by matching a given value in another column.

### value of (column) in row where (another column) is (some value) in (file)
Get the value of a cell by matching a given value in another column in a specified file.

### value of (column) in row number (number)
retrieves the value of the given column from the given row.

### value of (column) in row number (number) in (file)
retrieves the value of the given column from the given row in a specified file.

### name of column (column number)
retrieves the name of the n-th column (zero based index)

### data in row (row number)
Returns the data from the given row number as a map that holds key value pairs (where the key is the column name and the value is
the value from the given row).

### data in row (row number) in (file)
Returns the data from the given row number as a map that holds key value pairs (where the key is the column name and the value is
the value from the given row) in a specified file.

### data in row where (column) is (expected value) 
Returns the data from the row where the given column has the expected value as a map that holds key value pairs 
(where the key is the column name and the value is the value from the given row).

### data in row where (column) is (expected value) in (file)
Returns the data from the row where the given column has the expected value as a map that holds key value pairs 
(where the key is the column name and the value is the value from the given row) in a specified file.

### number of lines
Returns the number of lines.

### number of lines in (file)
Returns the number of lines in a specified file.

### number of lines where (column) is (value)
Returns the number of lines where the value in the given column is (value).

### number of lines where (column) is (value) in (file)
Returns the number of lines where the value in the given column is (value) in a specified file.