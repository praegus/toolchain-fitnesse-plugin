package nl.praegus.fitnesse.responders.ToolTip;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ToolTipsTest {

    @Test
    public void check_if_ToolTip_is_not_null_when_fixture_tooltips_and_bootstrap_tooltips_are_given() {
        ToolTips toolTips = new ToolTips();
        toolTips.tooltipTargetDirectory = "/src/test/resources/Tooltip/TooltipData_default";
        toolTips.bootstrapTargetDirectory = "/src/test/resources/Tooltip/TooltipData_default/fitnesse/resources/bootstrap-plus/txt/toolTipData.txt";

        String receivedResult = toolTips.getToolTip();

        assertThat(receivedResult).isNotEqualTo("");
    }
    @Test
    public void check_if_ToolTip_is_not_null_when_fixtureTooltips_are_null_and_bootstrapTooltips_are_given() {
        ToolTips toolTips = new ToolTips();
        toolTips.tooltipTargetDirectory = "/src/test/resources/Tooltip/TooltipData_noFixtureTooltip";
        toolTips.bootstrapTargetDirectory = "/src/test/resources/Tooltip/TooltipData_noFixtureTooltip/fitnesse/resources/bootstrap-plus/txt/toolTipData.txt";

        String receivedResult = toolTips.getToolTip();

        assertThat(receivedResult).isNotEqualTo("");
    }

    @Test
    public void check_if_returns_null_if_ToolTipList_is_empty(){
        ToolTips toolTips = new ToolTips();
        toolTips.tooltipTargetDirectory = "/src/test/resources/Tooltip/TooltipData_empty_txt";
        toolTips.bootstrapTargetDirectory = "/src/test/resources/Tooltip/TooltipData_empty_txt/fitnesse/resources/bootstrap-plus/txt/toolTipData.txt";

        String receivedResult = toolTips.getToolTip();

        assertThat(receivedResult).isEqualTo("");
    }

    @Test
    public void check_if_returns_null_if_there_is_no_txt_given(){
        ToolTips toolTips = new ToolTips();
        toolTips.tooltipTargetDirectory = "/src/test/resources/Tooltip/TooltipData_no_txt";
        toolTips.bootstrapTargetDirectory = "/src/test/resources/Tooltip/TooltipData_no_txt/fitnesse/resources/bootstrap-plus/txt/toolTipData.txt";

        String receivedResult = toolTips.getToolTip();

        assertThat(receivedResult).isEqualTo("");
    }

    @Test
    public void check_return_null_if_bootstrapToolTips_are_missing(){
        ToolTips toolTips = new ToolTips();
        toolTips.tooltipTargetDirectory = "/src/test/resources/Tooltip/TooltipData_default";
        toolTips.bootstrapTargetDirectory = "/src/test/resources/Tooltip/TooltipData_no_txt/fitnesse/resources/bootstrap-plus/txt/toolTipData.txt";

        String receivedResult = toolTips.getToolTip();

        assertThat(receivedResult).isEqualTo("");
    }

}
