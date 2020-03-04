package nl.praegus.fitnesse.responders.ToolTip;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ToolTipList<Public> {
    private List<String> toolTipList = new ArrayList<>();

    ToolTipList() throws IOException {
        List<JarFile> fixtures = getFixtures();

        for (JarFile fixture : fixtures){
            Optional<JarEntry> tooltips = fixture.stream().filter(name -> name.getName().contains("Tooltips.txt")).findFirst();
            if (tooltips.isPresent()){
                String line;
                URL url = new URL("jar:file:"+fixture.getName()+"!/Tooltips.txt");
                InputStream inputStream  = url.openStream();
                InputStreamReader reader  = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                while ((line = bufferedReader.readLine()) != null){
                    toolTipList.add(line);
                }
            }
        }
        Optional<File> toolChain = Arrays.stream(new File(System.getProperty("user.dir")+"/plugins").listFiles()).filter(name -> name.toString().contains("toolchain-fitnesse-plugin")).findFirst();
        String line;
        URL url = new URL("jar:file:"+toolChain.get().getPath()+"!/fitnesse/resources/bootstrap-plus/txt/toolTipData.txt");
        InputStream inputStream  = url.openStream();
        InputStreamReader reader  = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);

        while ((line = bufferedReader.readLine()) != null){
            toolTipList.add(line);
        }
    }

    private List<JarFile> getFixtures(){
        // get all content within the fixture map
        List<File> directoryContent = Arrays.asList(Objects.requireNonNull(new File(System.getProperty("user.dir") + "/fixtures").listFiles()));
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


    public String getToolTip(){
        Random rand = new Random();
        int pickedTip = rand.nextInt(toolTipList.size());

        return toolTipList.get(pickedTip);
    }



}
