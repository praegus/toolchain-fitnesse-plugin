package nl.praegus.fitnesse.responders.symbolicLink;

import fitnesse.wiki.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SymbolicLinkResponderTest {

    public TestWikiPageDummySymlinks getWikiPageWithoutSymlinks() {
        // Make a child
        TestWikiPageDummySymlinks testWikiPageDummyChild = new TestWikiPageDummySymlinks("childPageTest", "", new WikiPageDummy(), new WikiPageProperty());
        List<WikiPage> children = Collections.singletonList(testWikiPageDummyChild);
        // Make the parent
        TestWikiPageDummySymlinks testWikiPageDummy = new TestWikiPageDummySymlinks("dummyPage", "", new WikiPageDummy(), new WikiPageProperty(), children);
        return testWikiPageDummy;
    }

    public TestWikiPageDummySymlinks getWikiPageWithSymlinks() {
        // Make WikiPage (parent and two children)
        TestWikiPageDummySymlinks WikiPageDummyFrontEnd = new TestWikiPageDummySymlinks("FrontEnd", "", new WikiPageDummy(), new WikiPageProperty());
        TestWikiPageDummySymlinks WikiPageDummyBackEnd = new TestWikiPageDummySymlinks("BackEnd", "", new WikiPageDummy(), new WikiPageProperty());
        List<WikiPage> children = new ArrayList<>();
        children.add(WikiPageDummyFrontEnd);
        children.add(WikiPageDummyBackEnd);
        TestWikiPageDummySymlinks testWikiPageDummy = new TestWikiPageDummySymlinks("SuiteTest", "", new WikiPageDummy(), new WikiPageProperty(), children);

        // Add Symbolic link to SuiteTest (parent)
        PageData dataSuiteTest = testWikiPageDummy.getData();
        WikiPageProperty propsSuiteTest = dataSuiteTest.getProperties();
        WikiPageProperty symPropSuiteTest = propsSuiteTest.set(SymbolicPage.PROPERTY_NAME);
        symPropSuiteTest.set("BackEndSymlink", "DummyPage.BackEnd");

        // Add Symbolic link to FrontEnd (child)
        PageData dataFrontEnd = WikiPageDummyFrontEnd.getData();
        WikiPageProperty propsFrontEnd = dataFrontEnd.getProperties();
        WikiPageProperty symPropFrontEnd = propsFrontEnd.set(SymbolicPage.PROPERTY_NAME);
        symPropFrontEnd.set("BackEndSymlink", "DummyPage.BackEnd");

        return testWikiPageDummy;
    }


    @Test
    public void check_If_Response_Is_A_Empty_ArrayList() {
        TestWikiPageDummySymlinks testWikiPageDummy = getWikiPageWithoutSymlinks();

        ProjectSymbolicLinkInfo projectSymbolicLinkInfo = new ProjectSymbolicLinkInfo(testWikiPageDummy);
        List<SymbolicLinkInfo> receivedValue = projectSymbolicLinkInfo.getSymbolicLinkInfos();

         assertThat(receivedValue).isEqualTo(new ArrayList<>());
    }

    @Test
    public void check_If_Response_Is_Not_A_Empty_ArrayList() {
        TestWikiPageDummySymlinks testWikiPageDummy = getWikiPageWithSymlinks();

        ProjectSymbolicLinkInfo projectSymbolicLinkInfo = new ProjectSymbolicLinkInfo(testWikiPageDummy);
        List<SymbolicLinkInfo> receivedValue = projectSymbolicLinkInfo.getSymbolicLinkInfos();

        assertThat(receivedValue.size()).isEqualTo(2);
    }

    @Test
    public void check_If_Response_ArrayList_Has_The_Correct_Info() {
        TestWikiPageDummySymlinks testWikiPageDummy = getWikiPageWithSymlinks();

        ProjectSymbolicLinkInfo projectSymbolicLinkInfo = new ProjectSymbolicLinkInfo(testWikiPageDummy);
        List<SymbolicLinkInfo> receivedValue = projectSymbolicLinkInfo.getSymbolicLinkInfos();

        for (SymbolicLinkInfo value : receivedValue) {
            assertThat(value.getLinkName()).isEqualTo("BackEndSymlink");
            assertThat(value.getBackupLinkPath()).isEqualTo("DummyPage.BackEnd");
        }
    }

    @Test
    public void check_If_Response_Is_A_Empty_JSONArray() {
        TestWikiPageDummySymlinks testWikiPageDummy = getWikiPageWithoutSymlinks();

        ProjectSymbolicLinkInfo projectSymbolicLinkInfo = new ProjectSymbolicLinkInfo(testWikiPageDummy);
        JSONArray receivedValue = projectSymbolicLinkInfo.getSymbolicLinkInfosJsonArray();

        assertThat(receivedValue).isEmpty();
    }

    @Test
    public void check_If_Response_Is_Not_A_Empty_JSONArray() {
        TestWikiPageDummySymlinks testWikiPageDummy = getWikiPageWithSymlinks();

        ProjectSymbolicLinkInfo projectSymbolicLinkInfo = new ProjectSymbolicLinkInfo(testWikiPageDummy);
        JSONArray receivedValue = projectSymbolicLinkInfo.getSymbolicLinkInfosJsonArray();

        assertThat(receivedValue.length()).isEqualTo(2);
    }

    @Test
    public void check_If_Response_JSONArray_Has_The_Correct_Info() {
        TestWikiPageDummySymlinks testWikiPageDummy = getWikiPageWithSymlinks();

        ProjectSymbolicLinkInfo projectSymbolicLinkInfo = new ProjectSymbolicLinkInfo(testWikiPageDummy);
        JSONArray receivedValue = projectSymbolicLinkInfo.getSymbolicLinkInfosJsonArray();
        JSONObject receivedObject = receivedValue.getJSONObject(0);

        assertThat(receivedObject.get("linkName")).isEqualTo("BackEndSymlink");
        assertThat(receivedObject.get("backUpLinkPath")).isEqualTo("DummyPage.BackEnd");
    }
}
