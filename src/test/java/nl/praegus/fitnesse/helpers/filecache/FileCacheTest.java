package nl.praegus.fitnesse.helpers.filecache;
import nl.praegus.fitnesse.helpers.FileCache;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
public class FileCacheTest {
  public  ArrayList<String> testData = new ArrayList<>();

    @Test
    public void Set_file_cache_with_data(){
        ArrayList<String> receivedResult  = new ArrayList<>();
        testData.add("test1");
        testData.add("test2");
        testData.add("test3");

        FileCache fileCache = new FileCache();
        fileCache.setFileCache(System.getProperty("user.dir")+"/src/test/resources/FileCache/FileCache.txt",testData);

        ReadTxtFile(System.getProperty("user.dir")+"/src/test/resources/FileCache/FileCache.txt",receivedResult);
        assertThat(receivedResult).containsAll(testData);
    }
    @Test
    public void Set_file_cache_without_data(){
        ArrayList<String> receivedResult  = new ArrayList<>();
        testData = new ArrayList<>();

        FileCache fileCache = new FileCache();
        fileCache.setFileCache(System.getProperty("user.dir")+"/src/test/resources/FileCache/FileCache.txt",testData);

        ReadTxtFile(System.getProperty("user.dir")+"/src/test/resources/FileCache/FileCache.txt",receivedResult);

        assertThat(receivedResult).isEmpty();
    }
    @Test
    public void Get_file_cache_with_data(){
        ArrayList<String> receivedResult  = new ArrayList<>();
        testData.add("test1");
        testData.add("test2");
        testData.add("test3");

        FileCache fileCache = new FileCache();
        fileCache.setFileCache(System.getProperty("user.dir")+"/src/test/resources/FileCache/FileCache.txt",testData);
        receivedResult.addAll(fileCache.getFileCache(System.getProperty("user.dir")+"/src/test/resources/FileCache/FileCache.txt"));


        assertThat(receivedResult).containsAll(testData);
    }
    @Test
    public void Get_file_cache_without_data(){
        ArrayList<String> receivedResult  = new ArrayList<>();
        testData = new ArrayList<>();

        FileCache fileCache = new FileCache();
        fileCache.setFileCache(System.getProperty("user.dir")+"/src/test/resources/FileCache/FileCache.txt",testData);
        receivedResult.addAll(fileCache.getFileCache(System.getProperty("user.dir")+"/src/test/resources/FileCache/FileCache.txt"));


        assertThat(receivedResult).isEmpty();
    }



    public void ReadTxtFile(String path ,ArrayList<String> list){
        try {
            URL fileCacheURL = new URL("file:///" + path);

            InputStream inputStream = fileCacheURL.openStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            list.addAll(bufferedReader.lines().collect(Collectors.toList()));

        }catch(IOException e){

    }
    }

}
