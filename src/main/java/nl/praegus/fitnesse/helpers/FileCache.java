package nl.praegus.fitnesse.helpers;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileCache {
    // this function will create the file cache
    public void setFileCache(String path, List<String> data) {
        File fileCache = new File(path);
        try {
            //if previous cache exists, delete it
            if (fileCache.exists()) {
                fileCache.delete();
            }
            // use a printwriter to populate list
            FileWriter fileWriter = new FileWriter(path);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (String line : data) {
                printWriter.println(line);
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {

        }
    }

    //this function reads the file cache
    public List<String> getFileCache(String path) {
        File fileCache = new File(path);
        try {
            if (fileCache.exists()) {
                List<String> cacheData = new ArrayList<>();
                URL fileCacheURL = new URL("file:///" + fileCache.getPath());

                InputStream inputStream = fileCacheURL.openStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);

                cacheData.addAll(bufferedReader.lines().collect(Collectors.toList()));

                return cacheData;


            }
        } catch (IOException e) {

        }
        return null;
    }
}
