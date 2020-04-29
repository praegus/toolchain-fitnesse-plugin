package nl.praegus.fitnesse.responders.symbolicLink;

import fitnesse.wiki.PageData;
import fitnesse.wiki.SymbolicPage;
import fitnesse.wiki.WikiPage;
import fitnesse.wiki.WikiPageProperty;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class ProjectSymbolicLinkInfo {
    private List<SymbolicLinkInfo> symbolicLinkInfos = new ArrayList<>();

    public ProjectSymbolicLinkInfo(WikiPage wikiPage){
        symbolicLinkInfos.addAll(getSymlinksHelper(wikiPage, new ArrayList<>()));
    }

    public List<SymbolicLinkInfo> getSymbolicLinkInfos() {
        return symbolicLinkInfos;
    }

    public JSONArray getSymbolicLinkInfosJsonArray(){
        JSONArray symlinksJsonArray = new JSONArray();

        for (SymbolicLinkInfo symlink : symbolicLinkInfos) {
            JSONObject symlinkJsonObject = new JSONObject();
            symlinkJsonObject.put("pagePath", symlink.getPagePath());
            symlinkJsonObject.put("linkName", symlink.getLinkName());
            symlinkJsonObject.put("linkPath", symlink.getLinkPath());
            symlinkJsonObject.put("backUpLinkPath", symlink.getBackupLinkPath());

            symlinksJsonArray.put(symlinkJsonObject);
        }

        return symlinksJsonArray;
    }

    private List<SymbolicLinkInfo> getSymlinksHelper(WikiPage wikiPage, List<SymbolicLinkInfo> symbolicLinkArray) {
        PageData pageData = wikiPage.getData();
        // Get Symlinks of the wiki page
        WikiPageProperty symLinksProperty = pageData.getProperties().getProperty(SymbolicPage.PROPERTY_NAME);

        // Loop through the names
        if (symLinksProperty != null) {
            for (String name : symLinksProperty.keySet()) {
                symbolicLinkArray.add(new SymbolicLinkInfo(name, wikiPage, symLinksProperty));
            }
        }

        // Get Symlinks from the children
        if (wikiPage.getChildren() != null) {
            for (WikiPage p : wikiPage.getChildren()) {
                getSymlinksHelper(p, symbolicLinkArray);
            }
        }

        return symbolicLinkArray != null ? symbolicLinkArray : new ArrayList<>();
    }

}
