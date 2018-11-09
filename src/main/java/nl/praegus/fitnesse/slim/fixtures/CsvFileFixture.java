package nl.praegus.fitnesse.slim.fixtures;

import nl.hsac.fitnesse.fixture.slim.FileFixture;
import nl.hsac.fitnesse.fixture.slim.SlimFixtureException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CsvFileFixture extends FileFixture {
    private String separator = ",";
    private String csvFile = "";

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public void setCsvFile(String csvFile) {
        this.csvFile = csvFile;
    }

    public String valueOfInRowWhereIs(String resultColumn, String findByColumn, String findByColumnValue) {
        return valueOfInRowWhereIsIn(resultColumn, findByColumn, findByColumnValue, csvFile);
    }

    public String valueOfInRowWhereIsIn(String resultColumn, String findByColumn, String findByColumnValue, String filename) {

        String result = "";
        ArrayList<String> lines = getLinesFromFile(filename);

        try {
            String[] columns = lines.get(0).split(separator);
            int resultColumnIndex = indexOfColumn(columns, resultColumn);

            if (resultColumnIndex >= 0) {
                String line = lineWhereIsIn(findByColumn, findByColumnValue, filename);
                String[] values = line.split(separator);
                result = values[resultColumnIndex];
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new SlimFixtureException("No row found where " + findByColumn + " is " + findByColumnValue);
        }

        return result;
    }

    public String valueOfInRowNumber(String resultColumn, int rowNumber) {
        return valueOfInRowNumberIn(resultColumn, rowNumber, csvFile);
    }

    public String valueOfInRowNumberIn(String resultColumn, int rowNumber, String filename) {
        String result = "";
        ArrayList<String> lines = getLinesFromFile(filename);

        try {
            String[] columns = lines.get(0).split(separator);
            int resultColumnIndex = indexOfColumn(columns, resultColumn);
            String row = lines.get(rowNumber);
            String[] values = row.split(separator);
            result = values[resultColumnIndex];

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new SlimFixtureException("Row " + rowNumber + " has no field " + resultColumn);
        }
        return result;
    }

    public String nameOfColumn(int column) {
        return nameOfColumnIn(column, csvFile);
    }

    public String nameOfColumnIn(int column, String filename) {
        String result;
        ArrayList<String> lines = getLinesFromFile(filename);
        String[] columns = lines.get(0).split(separator);
        try {
            result = columns[column];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new SlimFixtureException("No column at position " + column + " (Row 0 has " + columns.length + " columns)");
        }
        return result;
    }

    public Map<String, String> dataInRow(int row) {
        return dataInRowIn(row, csvFile);
    }

    public Map<String, String> dataInRowWhereIs(String column, String lookupValue) {
        return  dataInRowWhereIsIn(column, lookupValue, csvFile);
    }

    public Map<String, String> dataInRowWhereIsIn(String column, String lookupValue, String filename) {
        Map<String, String> data = new HashMap<>();
        try {
            ArrayList<String> lines = getLinesFromFile(filename);
            String[] columns = lines.get(0).split(separator);
            int columnIndex = indexOfColumn(columns, column);

            if (columnIndex >= 0) {
                for (int i = 0; i < lines.size(); i++) {
                    String[] values = lines.get(i).split(separator);
                    if (values[columnIndex].equals(lookupValue)) {
                        String[] keys = lines.get(0).split(separator);
                        for (int j = 0; j < keys.length; j++) {
                            data.put(keys[j], values[j]);
                        }
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new SlimFixtureException("No row found where " + column + " is " + lookupValue);
        }
        return data;
    }

    public Map<String, String> dataInRowIn(int row, String filename) {
        Map<String, String> data = new HashMap<>();
        ArrayList<String> lines = getLinesFromFile(filename);
        String[] keys = lines.get(0).split(separator);
        String[] values = lines.get(row).split(separator);
        for (int j = 0; j < keys.length; j++) {
            data.put(keys[j], values[j]);
        }
        return data;
    }

    private ArrayList<String> getLinesFromFile(String filename) {
        String fullName = getFullName(filename);
        ensureParentExists(fullName);
        java.io.File file = new File(fullName);

        ArrayList<String> lines = new ArrayList<>();

        try {
            Scanner s = new Scanner(file);
            while (s.hasNextLine()) {
                lines.add(s.nextLine());
            }
            s.close();
        } catch (FileNotFoundException e) {
            throw new SlimFixtureException(e.getMessage());
        }

        return lines;
    }

    private String lineWhereIsIn(String findByColumn, String findByColumnValue, String fileName) {
        ArrayList<String> lines = getLinesFromFile(fileName);
        String result = "";
        try {
            String[] columns = lines.get(0).split(separator);
            int findColumnIndex = indexOfColumn(columns, findByColumn);

            if (findColumnIndex >= 0) {
                lines.remove(0);
                for (String line : lines) {
                    String[] values = line.split(separator);
                    if (values[findColumnIndex].equals(findByColumnValue)) {
                        result = line;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new SlimFixtureException("No row found where " + findByColumn + " is " + findByColumnValue);
        }
        return result;
    }

    private int indexOfColumn(String[] columns, String columnName) {
        int columnIndex = -1;
        for (int i = 0; i < columns.length; i++) {
            if (columns[i].equals(columnName)) {
                columnIndex = i;
            }
        }
        return columnIndex;
    }
}
