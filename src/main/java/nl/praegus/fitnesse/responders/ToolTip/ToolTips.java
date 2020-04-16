package nl.praegus.fitnesse.responders.ToolTip;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ToolTips {
    private List<String> toolTips = new ArrayList<>();
    public String tooltipTargetDirectory = "/TooltipData";

    private void addToolTips() {
        File[] dirs = new File(System.getProperty("user.dir") + tooltipTargetDirectory).listFiles();
        for (File dir : dirs) {
            // check if File in list is directory so we wont try to listfiles from a file
            if (dir.isDirectory()) {
                try {
                    URL TooltipsURL = new URL("file:///" + dir.getPath() + "/Tooltips.txt");

                    InputStream inputStream = TooltipsURL.openStream();
                    InputStreamReader reader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    //for each line, which is a tooltip add it to the tooltip list
                    toolTips.addAll(bufferedReader.lines().collect(Collectors.toList()));
                } catch (IOException e) {
                    System.out.println("couldn't parse tooltips for fixture " + dir.getName());
                }
            }
        }
    }
    private void setFileCache(String path,List<String> data) throws IOException {
        File filecache = new File(path);
        if (filecache.exists()) {
            filecache.delete();
        }
        FileWriter fileWriter = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (String line:data) {
            printWriter.println(line);
        }
        fileWriter.flush();
        fileWriter.close();
    }
    private List<String> getFileCache(String path) throws IOException {
        File fileCache = new File(path);
        if (fileCache.exists()){
            List<String> cacheData = new ArrayList<>();
            URL fileCacheURL = new URL("file:///" +fileCache.getPath());

            InputStream inputStream = fileCacheURL.openStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);

            cacheData.addAll(bufferedReader.lines().collect(Collectors.toList()));

            return cacheData;

        }else{
            return null;
        }
    }

    private void addBootstrapTooltips() {
        String[] classPaths = System.getProperty("java.class.path").split(";");
        String toolchainPath = null;
        for (String classpath:classPaths) {
            if (classpath.contains("toolchain-fitnesse-plugin")){
                toolchainPath = classpath;
            }
        }

        try {
            if (toolchainPath != null) {
                URL BootstrapTooltipsURL = new URL("jar:file:" + toolchainPath + "!/fitnesse/resources/bootstrap-plus/txt/toolTipData.txt");

                InputStream inputStream = BootstrapTooltipsURL.openStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                //for each line, which is a tooltip add it to the tooltip list
                toolTips.addAll(bufferedReader.lines().collect(Collectors.toList()));
            }
            } catch(IOException e){
                System.out.println("couldn't parse bootstrap tooltips");
            }
    }

    public String getToolTip(String cookieIsValid) throws IOException {
        if (cookieIsValid.equals("false")){
            addBootstrapTooltips();
            addToolTips();
            setFileCache(System.getProperty("user.dir")+tooltipTargetDirectory+"/tooltipCache.txt",toolTips);
        }else{
            toolTips = getFileCache(System.getProperty("user.dir")+tooltipTargetDirectory+"/tooltipCache.txt");
        }
        //after bootstrap tooltips have been loaded, check if tooltips actuallly contains tooltips. if not return null so bootstrap tooltips will be loaded in JS.

        if (toolTips.size() != 0) {

            Random rand = new Random();
            int pickedTip = rand.nextInt(toolTips.size());
            return toolTips.get(pickedTip);
        }else  {
            return "null";
        }


}
}
