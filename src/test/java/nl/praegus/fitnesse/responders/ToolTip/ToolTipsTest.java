package nl.praegus.fitnesse.responders.ToolTip;

import org.junit.Test;

import javax.tools.Tool;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class ToolTipsTest {


    @Test
    public void check_if_ToolTip_is_not_null_when_fixture_tooltips_and_bootstrap_tooltips_are_given() {
        ToolTips.setToolTipPath( System.getProperty("user.dir")+ "/src/test/resources/Tooltip/TooltipData_default");
        ToolTips.setBootStrapPath(ToolTips.getToolTipPath() + "/toolchain.jar");
        ToolTips.setToolTipsCache(new ArrayList<>());

        String receivedResult = ToolTips.getRandomToolTip();

        assertThat(receivedResult).isNotEqualTo("null");
    }

    @Test
    public void Check_if_ToolTip_Is_null_when_Nothing_is_given() {

        ToolTips.setToolTipPath( System.getProperty("user.dir")+ "/src/test/resources/Tooltip/TooltipData_no_txt");
        ToolTips.setBootStrapPath(ToolTips.getToolTipPath() + "/toolchain.jar");
        ToolTips.setToolTipsCache(new ArrayList<>());

        String receivedResult = ToolTips.getRandomToolTip();

        assertThat(receivedResult).isEqualTo("null");
    }

    @Test
    public void Check_if_ToolTip_Is_null_when_Empty_txt_is_given() {
        ToolTips.setToolTipPath( System.getProperty("user.dir")+ "/src/test/resources/Tooltip/TooltipData_empty_txt");
        ToolTips.setBootStrapPath(ToolTips.getToolTipPath() + "/toolchain.jar");
        ToolTips.setToolTipsCache(new ArrayList<>());

        String receivedResult = ToolTips.getRandomToolTip();

        assertThat(receivedResult).isEqualTo("null");
    }

    @Test
    public void Check_If_Tooltip_is_returned_if_only_Bootstrap_tooltips_are_Found (){
        ToolTips.setToolTipPath( System.getProperty("user.dir")+ "/src/test/resources/Tooltip/TooltipData_noFixtureTooltip");
        ToolTips.setBootStrapPath(ToolTips.getToolTipPath() + "/toolchain.jar");
        ToolTips.setToolTipsCache(new ArrayList<>());

        String receivedResult = ToolTips.getRandomToolTip();

        assertThat(receivedResult).isNotEqualTo("null");
    }

    @Test
    public void Check_If_Tooltip_is_returned_if_only_fixture_tooltips_are_Found (){
        ToolTips.setToolTipPath( System.getProperty("user.dir")+ "/src/test/resources/Tooltip/TooltipData_default");
        ToolTips.setBootStrapPath(ToolTips.getToolTipPath() + "/toolchain2.jar");
        ToolTips.setToolTipsCache(new ArrayList<>());

        String receivedResult = ToolTips.getRandomToolTip();

        assertThat(receivedResult).isNotEqualTo("null");
    }
}
