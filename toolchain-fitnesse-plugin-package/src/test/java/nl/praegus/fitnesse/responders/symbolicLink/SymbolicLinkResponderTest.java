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

    @Test
    public void check_if_response_is_a_empty_arrayList() {
        TestWikiPageDummySymlinks testWikiPageDummy = getWikiPageWithoutSymlinks();

        ProjectSymbolicLinkInfo projectSymbolicLinkInfo = new ProjectSymbolicLinkInfo(testWikiPageDummy);
        List<SymbolicLinkInfo> receivedValue = projectSymbolicLinkInfo.getSymbolicLinkInfos();

         assertThat(receivedValue).isEmpty();
    }

    @Test
    public void check_if_response_is_not_a_empty_arrayList() {
        TestWikiPageDummySymlinks testWikiPageDummy = getWikiPageWithSymlinks();

        ProjectSymbolicLinkInfo projectSymbolicLinkInfo = new ProjectSymbolicLinkInfo(testWikiPageDummy);
        List<SymbolicLinkInfo> receivedValue = projectSymbolicLinkInfo.getSymbolicLinkInfos();

        assertThat(receivedValue).hasSize(2);
    }

    @Test
    public void check_if_response_arrayList_has_the_correct_info() {
        TestWikiPageDummySymlinks testWikiPageDummy = getWikiPageWithSymlinks();

        ProjectSymbolicLinkInfo projectSymbolicLinkInfo = new ProjectSymbolicLinkInfo(testWikiPageDummy);
        List<SymbolicLinkInfo> receivedValue = projectSymbolicLinkInfo.getSymbolicLinkInfos();

        for (SymbolicLinkInfo value : receivedValue) {
            assertThat(value.getLinkName()).isEqualTo("BackEndSymlink");
            assertThat(value.getBackupLinkPath()).isEqualTo("DummyPage.BackEnd");
        }
    }

    @Test
    public void check_if_response_is_a_empty_JSONArray() {
        TestWikiPageDummySymlinks testWikiPageDummy = getWikiPageWithoutSymlinks();

        ProjectSymbolicLinkInfo projectSymbolicLinkInfo = new ProjectSymbolicLinkInfo(testWikiPageDummy);
        JSONArray receivedValue = projectSymbolicLinkInfo.getSymbolicLinkInfosJsonArray();

        assertThat(receivedValue).isEmpty();
    }

    @Test
    public void check_if_response_is_not_a_empty_JSONArray() {
        TestWikiPageDummySymlinks testWikiPageDummy = getWikiPageWithSymlinks();

        ProjectSymbolicLinkInfo projectSymbolicLinkInfo = new ProjectSymbolicLinkInfo(testWikiPageDummy);
        JSONArray receivedValue = projectSymbolicLinkInfo.getSymbolicLinkInfosJsonArray();

        assertThat(receivedValue.length()).isEqualTo(2);
    }

    @Test
    public void check_if_response_JSONArray_has_the_correct_info() {
        TestWikiPageDummySymlinks testWikiPageDummy = getWikiPageWithSymlinks();

        ProjectSymbolicLinkInfo projectSymbolicLinkInfo = new ProjectSymbolicLinkInfo(testWikiPageDummy);
        JSONArray receivedValue = projectSymbolicLinkInfo.getSymbolicLinkInfosJsonArray();
        JSONObject receivedObject = receivedValue.getJSONObject(0);

        assertThat(receivedObject.get("linkName")).isEqualTo("BackEndSymlink");
        assertThat(receivedObject.get("backUpLinkPath")).isEqualTo("DummyPage.BackEnd");
    }

    private TestWikiPageDummySymlinks getWikiPageWithoutSymlinks() {
        // Make a child
        TestWikiPageDummySymlinks testWikiPageDummyChild = new TestWikiPageDummySymlinks("childPageTest", "", new WikiPageDummy(), new WikiPageProperty());
        List<WikiPage> children = Collections.singletonList(testWikiPageDummyChild);
        // Make the parent
        return new TestWikiPageDummySymlinks("dummyPage", "", new WikiPageDummy(), new WikiPageProperty(), children);
    }

    private TestWikiPageDummySymlinks getWikiPageWithSymlinks() {
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

}
