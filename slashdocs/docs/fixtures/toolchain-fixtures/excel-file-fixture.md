Package to import: _nl.praegus.fitnesse.slim.fixtures_

Excel file fixture is a basic fixture to read and write Strings from and to Excel sheets

## Methods

### open excel sheet (file)
Select the xls file to read. Default directory is files/xls/ if it is not specified as a link/full path.

### value in row (rowNumber) column (columnNumber)
Retrieve the String value of a cell, defined by row- and column number. Both indexes are zero-based.

### value in row (rowNumber) column (columnNumber) in sheet (worksheet)
Retrieve the String value of a cell, defined by row- and column number from the given worksheet. 
Both indexes are zero-based.

### write (value) to row (rowNumber) column (columnNumber)
Write a value to a cell, defined by row- and column number. Both indexes are zero-based.

### write (value) to row (rowNumber) column (columnNumber)
Write a value to a cell, defined by row- and column number, in the given worksheet. 
Both indexes are zero-based.