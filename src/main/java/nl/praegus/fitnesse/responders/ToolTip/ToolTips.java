package nl.praegus.fitnesse.responders.ToolTip;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ToolTips {

    private List<String> toolTips = new ArrayList<>();
    public String tooltipTargetDirectory = "/TooltipData";
    public String bootstrapTargetDirectory =  "/TooltipData/fitnesse/resources/bootstrap-plus/txt/toolTipData.txt";

    private void addToolTips(){
        File[] dirs = new File(System.getProperty("user.dir")+ tooltipTargetDirectory).listFiles();
        for (File dir : dirs){
            // check if File in list is directory so we wont try to listfiles from a file
            if(dir.isDirectory()){
                for (File tooltipFile : new File(dir.getPath()).listFiles()) {
                    //check if the file is actual tooltips.txt file so we arent reading any other txt files
                    if (tooltipFile.getName().toLowerCase().equals("tooltips.txt")) {
                        try {
                            URL TooltipsURL = new URL("file:///" + dir.getPath() + "/tooltips.txt");

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
        }
    }
    private void addBootstrapTooltips(){
        File BootstrapTooltips = new File(System.getProperty("user.dir") + bootstrapTargetDirectory);
        // check if file exists so we wont get an error
        if (BootstrapTooltips.exists()){
            try {
                URL BootstrapTooltipsURL = new URL("file:///" + BootstrapTooltips.getPath());

                InputStream inputStream = BootstrapTooltipsURL.openStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                //for each line, which is a tooltip add it to the tooltip list
                toolTips.addAll(bufferedReader.lines().collect(Collectors.toList()));
            }catch (IOException e){
                System.out.println("couldn't parse bootstrap tooltips");
            }
        }

    }

    public String getToolTip() {

        addBootstrapTooltips();
        //after bootstrap tooltips have been loaded, check if tooltips actuallly contains tooltips. if not return null so bootstrap tooltips will be loaded in JS.
        if (toolTips.size() != 0) {
            addToolTips();
            Random rand = new Random();
            int pickedTip = rand.nextInt(toolTips.size());

            return toolTips.get(pickedTip);
        } else {
            return "";
        }
    }
}
