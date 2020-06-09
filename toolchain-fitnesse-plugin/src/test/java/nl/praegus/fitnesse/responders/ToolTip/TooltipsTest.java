package nl.praegus.fitnesse.responders.ToolTip;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TooltipsTest {


    @Test
    public void check_if_ToolTip_is_not_null_when_fixture_tooltips_and_bootstrap_tooltips_are_given() {
        Tooltips toolTips = new Tooltips(System.getProperty("user.dir") + "/src/test/resources/Tooltip/TooltipData_default", System.getProperty("user.dir") + "/src/test/resources/Tooltip/TooltipData_default/toolchain.jar");

        String receivedResult = toolTips.getRandomTooltip();

        assertThat(receivedResult).isNotEqualTo(null);
    }

    @Test
    public void Check_if_txt_are_found_but_empty_null_is_given() {
        Tooltips toolTips = new Tooltips(System.getProperty("user.dir") + "/src/test/resources/Tooltip/TooltipData_empty_txt", System.getProperty("user.dir") + "/src/test/resources/Tooltip/TooltipData_empty_txt/toolchain.jar");

        String receivedResult = toolTips.getRandomTooltip();

        assertThat(receivedResult).isEqualTo(null);
    }

    @Test
    public void Check_if_ToolTip_Is_null_when_Empty_txt_is_given() {
        Tooltips toolTips = new Tooltips(System.getProperty("user.dir") + "/src/test/resources/Tooltip/TooltipData_no_txt", System.getProperty("user.dir") + "/src/test/resources/Tooltip/TooltipData_no_txt/toolchain.jar");

        String receivedResult = toolTips.getRandomTooltip();

        assertThat(receivedResult).isEqualTo(null);
    }

    @Test
    public void Check_If_Tooltip_is_returned_if_only_Bootstrap_tooltips_are_Found() {
        Tooltips toolTips = new Tooltips(System.getProperty("user.dir") + "/src/test/resources/Tooltip/TooltipData_noFixtureTooltip", System.getProperty("user.dir") + "/src/test/resources/Tooltip/TooltipData_noFixtureTooltip/toolchain.jar");

        String receivedResult = toolTips.getRandomTooltip();

        assertThat(receivedResult).isNotEqualTo(null);
    }

    @Test
    public void Check_If_Tooltip_is_returned_if_only_fixture_tooltips_are_Found() {
        Tooltips toolTips = new Tooltips(System.getProperty("user.dir") + "/src/test/resources/Tooltip/TooltipData_default", System.getProperty("user.dir") + "/src/test/resources/Tooltip/TooltipData_default/toolchain2.jar");

        String receivedResult = toolTips.getRandomTooltip();

        assertThat(receivedResult).isNotEqualTo(null);
    }


}
