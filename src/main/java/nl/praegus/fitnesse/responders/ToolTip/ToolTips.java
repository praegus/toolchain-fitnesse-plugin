package nl.praegus.fitnesse.responders.ToolTip;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ToolTips {

    private List<String> toolTips = new ArrayList<>();
    public String fixturePath = "/fixtures";
    public String toolchainPath = "/plugins";

    private void addToolTips(){
        List<JarFile> fixtures = getFixtures();

        for (JarFile fixture : fixtures) {
            //for each fixture check if tooltips.txt is present and then parse it to jar entry
            Optional<JarEntry> tooltips = fixture.stream().filter(name -> name.getName().toLowerCase().contains("tooltips.txt")).findFirst();
            //check if tooltips is present so url.openstream doesnt make a null error
            if (tooltips.isPresent()) {
                try {
                    URL url = new URL("jar:file:" + fixture.getName() + "!/Tooltips.txt");
                    InputStream inputStream = url.openStream();

                    InputStreamReader reader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    // for each line, which is a tooltip add it to the tooltip list
                    toolTips.addAll(bufferedReader.lines().collect(Collectors.toList()));
                } catch (IOException e) {
                    System.out.println("couldn't parse tooltips in fixture " + fixture.getName());
                }
            }
        }
        addBootstrapTooltips();
    }

    private List<JarFile> getFixtures() {
        // get all content within the fixture map and check if they arent null
        List<File> directoryContent = Arrays.asList(Objects.requireNonNull(new File(System.getProperty("user.dir") + fixturePath).listFiles()));
        // get all jars within the fixture map and parse it to JarFile
        List<JarFile> fixtures = directoryContent.stream().filter(name -> name.toString().contains(".jar")).map(o -> {
            try {
                return new JarFile(o);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        return fixtures;
    }

    private void addBootstrapTooltips() {
        // get tooltips.txt from the bootstrap plugin
        Optional<File> toolChain = Arrays.stream(Objects.requireNonNull(new File(System.getProperty("user.dir") + toolchainPath).listFiles())).filter(name -> name.toString().toLowerCase().contains("toolchain-fitnesse-plugin")).findFirst();
        // check if toochain is a file we can read so inputStream doesnt give a null error
        if (toolChain.get().isFile()) {
            try {
                URL url = new URL("jar:file:" + toolChain.get().getPath() + "!/fitnesse/resources/bootstrap-plus/txt/toolTipData.txt");
                InputStream inputStream = url.openStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                // for each line, which is a tooltip add it to the tooltip list
                toolTips.addAll(bufferedReader.lines().collect(Collectors.toList()));
            } catch (IOException e) {
                System.out.println("couldn't parse bootstrap tooltips");
            }
        }
    }

    public String getToolTip() {
        addToolTips();

        if (toolTips.size() != 0) {
            Random rand = new Random();
            int pickedTip = rand.nextInt(toolTips.size());

            return toolTips.get(pickedTip);
        } else {
            return null;
        }
    }
}
