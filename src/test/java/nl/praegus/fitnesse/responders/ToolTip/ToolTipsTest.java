package nl.praegus.fitnesse.responders.ToolTip;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ToolTipsTest {

    @Test
    public void check_if_ToolTip_is_not_null_when_fixture_tooltips_and_bootstrap_tooltips_are_given() {
        ToolTips toolTips = new ToolTips();
        toolTips.tooltipTargetDirectory = "/src/test/resources/Tooltip/TooltipData_default";
        toolTips.toolchainPath =System.getProperty("user.dir")+ toolTips.tooltipTargetDirectory + "/toolchain.jar";

        String receivedResult = toolTips.getToolTip("false");

        assertThat(receivedResult).isNotEqualTo("null");
    }
    @Test
    public void Check_if_ToolTip_Is_null_when_Nothing_is_given() {
        ToolTips toolTips = new ToolTips();
        toolTips.tooltipTargetDirectory = "/src/test/resources/Tooltip/TooltipData_no_txt";
        toolTips.toolchainPath =System.getProperty("user.dir")+ toolTips.tooltipTargetDirectory + "/toolchain.jar";

        String receivedResult = toolTips.getToolTip("false");

        assertThat(receivedResult).isEqualTo("null");
    }
    @Test
    public void Check_if_ToolTip_Is_null_when_Empty_txt_is_given() {
        ToolTips toolTips = new ToolTips();
        toolTips.tooltipTargetDirectory = "/src/test/resources/Tooltip/TooltipData_empty_txt";
        toolTips.toolchainPath =System.getProperty("user.dir")+ toolTips.tooltipTargetDirectory + "/toolchain.jar";

        String receivedResult = toolTips.getToolTip("false");

        assertThat(receivedResult).isEqualTo("null");
    }
    @Test
    public void Check_If_Tooltip_is_returned_if_only_Bootstrap_tooltips_are_Found (){
        ToolTips toolTips = new ToolTips();
        toolTips.tooltipTargetDirectory = "/src/test/resources/Tooltip/TooltipData_noFixtureTooltip";
        toolTips.toolchainPath = System.getProperty("user.dir")+ toolTips.tooltipTargetDirectory + "/toolchain.jar";

        String receivedResult = toolTips.getToolTip("false");

        assertThat(receivedResult).isNotEqualTo("null");
    }
    @Test
    public void Check_If_Tooltip_is_returned_if_only_fixture_tooltips_are_Found (){
        ToolTips toolTips = new ToolTips();
        toolTips.tooltipTargetDirectory = "/src/test/resources/Tooltip/TooltipData_default";
        toolTips.toolchainPath = System.getProperty("user.dir")+ toolTips.tooltipTargetDirectory + "/toolchain2.jar";

        String receivedResult = toolTips.getToolTip("false");

        assertThat(receivedResult).isNotEqualTo("null");
    }

}
