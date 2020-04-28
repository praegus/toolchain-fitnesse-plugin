package nl.praegus.fitnesse.responders.symbolicLink;

import fitnesse.wiki.PageCrawler;
import fitnesse.wiki.PathParser;
import fitnesse.wiki.WikiPage;
import fitnesse.wiki.WikiPagePath;
import fitnesse.wiki.WikiPageProperty;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SymbolicLinkInfo {

    private String pagePath;
    private String linkName;
    private String linkPath;
    private String backupLinkPath;

    public SymbolicLinkInfo(String linkName, WikiPage wikiPage, WikiPageProperty symLinksProperty) {
        this.pagePath = wikiPage.getFullPath().toString();
        this.linkName = linkName;
        this.linkPath = makePathForSymbolicLink(wikiPage, PathParser.parse(symLinksProperty.get(linkName)));
        this.backupLinkPath = symLinksProperty.get(linkName);
    }

    public static JSONArray getJsonArray(ArrayList<SymbolicLinkInfo> symlinksArrayList){
        JSONArray symlinksJsonArray = new JSONArray();

        for (SymbolicLinkInfo symlink : symlinksArrayList) {
            JSONObject symlinkJsonObject = new JSONObject();
            symlinkJsonObject.put("pagePath", symlink.pagePath);
            symlinkJsonObject.put("linkName", symlink.linkName);
            symlinkJsonObject.put("linkPath", symlink.linkPath);
            symlinkJsonObject.put("backUpLinkPath", symlink.backupLinkPath);

            symlinksJsonArray.put(symlinkJsonObject);
        }

        return symlinksJsonArray;
    }

    private String makePathForSymbolicLink(WikiPage page, WikiPagePath wikiPagePath) {
        if (wikiPagePath != null) {
            WikiPage wikiPage = wikiPagePath.isRelativePath() ? page.getParent() : page;
            PageCrawler crawler = wikiPage.getPageCrawler();
            WikiPage wikiPageFromCrawler = crawler.getPage(wikiPagePath);
            WikiPagePath fullPath;
            if (wikiPageFromCrawler != null) {
                fullPath = wikiPageFromCrawler.getFullPath();
                fullPath.makeAbsolute();
            } else {
                fullPath = new WikiPagePath();
            }
            String pathString = fullPath.toString();
            return pathString.length() > 0 && pathString.charAt(0) == '.' ? pathString.substring(1) : pathString;
        }
        return null;
    }

}