package nl.praegus.fitnesse.responders.ToolTip;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ToolTipsTest {

    @Test
    public void check_if_ToolTip_is_not_null_when_TooltipLists_are_given() {
        ToolTips toolTips = new ToolTips();
        toolTips.fixturePath = "/src/test/resources/ToolTips/fixtures";
        toolTips.toolchainPath = "/src/test/resources/ToolTips/toolchain";

        String receivedResult = toolTips.getToolTip();

        assertThat(receivedResult).isNotEmpty();

    }

    @Test
    public void check_if_ToolTip_is_null_when_ToolTipslist_is_empty() {
        ToolTips toolTips = new ToolTips();
        toolTips.fixturePath = "/src/test/resources/ToolTips/fixtures_null";
        toolTips.toolchainPath = "/src/test/resources/ToolTips/toolchain_null";

        String receivedResult = toolTips.getToolTip();

        assertThat(receivedResult).isNull();

    }

    @Test
    public void Check_if_jars_are_not_found_returns_null() {
        ToolTips toolTips = new ToolTips();
        toolTips.fixturePath = "/src/test/resources/ToolTips";
        toolTips.toolchainPath = "/src/test/resources/ToolTips";

        String receivedResult = toolTips.getToolTip();

        assertThat(receivedResult).isNull();
    }

    @Test
    public void Check_if_only_bootstrap_tips_are_found_returns_correct_tooltips() {
        ToolTips toolTips = new ToolTips();
        toolTips.fixturePath = "/src/test/resources/ToolTips";
        toolTips.toolchainPath = "/src/test/resources/ToolTips/toolchain";

        String receivedResult = toolTips.getToolTip();
        List expectedResult = getBootStraptoolTipList();

        assertThat(expectedResult).contains(receivedResult);

    }

    @Test
    public void Check_if_only_fixture_tips_are_found_returns_correct_tooltips() {
        ToolTips toolTips = new ToolTips();
        toolTips.fixturePath = "/src/test/resources/ToolTips/fixtures";
        toolTips.toolchainPath = "/src/test/resources/ToolTips";

        String receivedResult = toolTips.getToolTip();
        List expectedResult = getFixtureTooltipList();

        assertThat(expectedResult).contains(receivedResult);
    }


    private List<String> getFixtureTooltipList() {
        InputStreamReader inputStreamReader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("ToolTips/ToolTipData/FixtureToolTips.txt"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        return bufferedReader.lines().collect((Collectors.toList()));
    }

    private List<String> getBootStraptoolTipList() {
        InputStreamReader inputStreamReader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("ToolTips/ToolTipData/BootStrapToolTips.txt"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        return bufferedReader.lines().collect((Collectors.toList()));
    }

}
