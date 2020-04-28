package nl.praegus.fitnesse.responders.ToolTip;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ToolTips {
    private static List<String> toolTipsCache = new ArrayList<>();
    private static String toolTipPath = System.getProperty("user.dir") + "/TooltipData";
    private static String bootStrapPath = null;

    public static void setToolTipPath(String toolTipPath) {
        ToolTips.toolTipPath = toolTipPath;
    }

    public static String getToolTipPath() {
        return toolTipPath;
    }

    public static void setBootStrapPath(String bootStrapPath) {
        ToolTips.bootStrapPath = bootStrapPath;
    }

    public static void setToolTipsCache(List<String> toolTipsCache) {
        ToolTips.toolTipsCache = toolTipsCache;
    }

    public static String getRandomToolTip() {
        if (toolTipsCache.isEmpty()) {
            addFixtureToolTipsToList();
            addBootstrapTooltipsToList();
        }

        if (toolTipsCache.size() != 0) {
            Random rand = new Random();
            int pickedTip = rand.nextInt(toolTipsCache.size());
            return toolTipsCache.get(pickedTip);
        } else {
            return null;
        }
    }

    private static void addFixtureToolTipsToList() {
        File[] dirs = new File(toolTipPath).listFiles();
        for (File dir : dirs) {
            // check if File in list is directory so we wont try to listfiles from a file
            if (dir.isDirectory()) {
                try {
                    toolTipsCache.addAll(readToolTips(new URL("file:///" + dir.getPath() + "/Tooltips.txt")));
                } catch (MalformedURLException e) {
                    System.out.println("couldn't find tooltips for fixture " + dir.getName());
                }
            }
        }
    }

    private static void addBootstrapTooltipsToList() {
        // get the bootstrap path
        if (bootStrapPath == null) {
            String[] classPaths = System.getProperty("java.class.path").split(";");
            for (String classpath : classPaths) {
                if (classpath.contains("toolchain-fitnesse-plugin")) {
                    bootStrapPath = classpath;
                }
            }
        }
        try {
            toolTipsCache.addAll(readToolTips(new URL("jar:file:" + bootStrapPath + "!/fitnesse/resources/bootstrap-plus/txt/toolTipData.txt")));
        } catch (MalformedURLException e) {
            System.out.println("couldn't find bootstrap tooltips");
        }
    }

    private static List<String> readToolTips(URL url) {
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
