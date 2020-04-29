package nl.praegus.fitnesse.responders.ToolTip;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ToolTips {
    private final ArrayList<String> toolTipsCache = new ArrayList<>();
    private final String toolTipPath;
    private final String bootStrapPath;

    public ToolTips(String toolTipPath, String bootStrapPath) {
        this.toolTipPath = toolTipPath;
        this.bootStrapPath = bootStrapPath;
        this.toolTipsCache.addAll(getFixtureToolTips());
        this.toolTipsCache.addAll(getBootstrapTooltips());
    }

    public ToolTips() {
        this.toolTipPath = System.getProperty("user.dir") + "/TooltipData";
        this.bootStrapPath = getBootstrapPath();
        this.toolTipsCache.addAll(getFixtureToolTips());
        this.toolTipsCache.addAll(getBootstrapTooltips());

    }

    public String getRandomToolTip() {
        if (toolTipsCache.size() != 0) {
            Random rand = new Random();
            int pickedTip = rand.nextInt(toolTipsCache.size());
            return toolTipsCache.get(pickedTip);
        } else {
            return null;
        }
    }

    public static String getBootstrapPath() {

        String[] classPaths = System.getProperty("java.class.path").split(";");
        for (String classpath : classPaths) {
            if (classpath.contains("toolchain-fitnesse-plugin")) {
                return classpath;
            }
        }
        return null;
    }

    private ArrayList<String> getFixtureToolTips() {
        ArrayList<String> toolTips = new ArrayList<>();
        File[] dirs = new File(toolTipPath).listFiles();
        if (dirs != null) {
            for (File dir : dirs) {
                // check if File in list is directory so we wont try to listfiles from a file
                if (dir.isDirectory()) {
                    try {
                        toolTips.addAll(readToolTips(new URL("file:///" + dir.getPath() + "/Tooltips.txt")));
                    } catch (MalformedURLException e) {
                        System.out.println("couldn't find tooltips for fixture " + dir.getName());
                    }
                }
            }
        }
        return toolTips;
    }

    private ArrayList<String> getBootstrapTooltips() {
        // get the bootstrap path
        ArrayList<String> tooltips = new ArrayList<>();
        try {
            tooltips.addAll(readToolTips(new URL("jar:file:" + bootStrapPath + "!/fitnesse/resources/bootstrap-plus/txt/toolTipData.txt")));
        } catch (MalformedURLException e) {
            System.out.println("couldn't find bootstrap tooltips");
        }
        return tooltips;
    }

    private List<String> readToolTips(URL url) {
        try {
            InputStream inputStream = url.openStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("couldnt read tooltips on url: " + url.getPath());
        }
        return new ArrayList<>();
    }
}
