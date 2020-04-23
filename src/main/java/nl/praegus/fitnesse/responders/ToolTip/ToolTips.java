package nl.praegus.fitnesse.responders.ToolTip;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ToolTips {
    private static List<String> toolTipsCache = new ArrayList<>();
    public String tooltipTargetDirectory = "/TooltipData";
    public String toolchainPath = null;

    public ToolTips() {
        String[] classPaths = System.getProperty("java.class.path").split(";");
        for (String classpath : classPaths) {
            if (classpath.contains("toolchain-fitnesse-plugin")) {
                toolchainPath = classpath;
            }
        }

        if (toolTipsCache.isEmpty()){
            addFixtureToolTips();
            addBootstrapTooltips();
        }
    }

    public String getRandomToolTip() {

        if (toolTipsCache.size() != 0) {
            Random rand = new Random();
            int pickedTip = rand.nextInt(toolTipsCache.size());
            return toolTipsCache.get(pickedTip);
        } else {
            return "null";
        }
    }

    private void addFixtureToolTips() {
        File[] dirs = new File(System.getProperty("user.dir") + tooltipTargetDirectory).listFiles();
        for (File dir : dirs) {
            // check if File in list is directory so we wont try to listfiles from a file
            if (dir.isDirectory()) {
                try {
                    toolTipsCache.addAll(readToolTips(new URL("file:///" + dir.getPath() + "/Tooltips.txt")));
                } catch (IOException e) {
                    System.out.println("couldn't parse tooltips for fixture " + dir.getName());
                }
            }
        }
    }

    private void addBootstrapTooltips() {
        try {
            if (toolchainPath != null) {
                toolTipsCache.addAll(readToolTips(new URL("jar:file:" + toolchainPath + "!/fitnesse/resources/bootstrap-plus/txt/toolTipData.txt")));
            }
        } catch (IOException e) {
            System.out.println("couldn't parse bootstrap tooltips");
        }
    }

    private List<String> readToolTips(URL url) throws IOException {
        InputStream inputStream = url.openStream();
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        return bufferedReader.lines().collect(Collectors.toList());
    }
}
