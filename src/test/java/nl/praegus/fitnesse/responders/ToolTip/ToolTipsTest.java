package nl.praegus.fitnesse.responders.ToolTip;

import org.junit.Assert;
import org.junit.Test;

public class ToolTipsTest {
    @Test
    public void check_if_ToolTip_is_not_null_when_TooltipLists_are_given(){
        ToolTips toolTips = new ToolTips();
        toolTips.fixturePath = "/src/test/resources/ToolTips/fixtures";
        toolTips.toolchainPath = "/src/test/resources/ToolTips/toolchain";

       String expectedResult = toolTips.getToolTip();
        Assert.assertNotNull(expectedResult);

    }
    @Test
    public void check_if_ToolTip_is_null_when_ToolTips_are_not_empty() {
        ToolTips toolTips = new ToolTips();
        toolTips.fixturePath = "/src/test/resources/ToolTips/fixtures_null";
        toolTips.toolchainPath = "/src/test/resources/ToolTips/toolchain_null";

        String expectedResult = toolTips.getToolTip();
        Assert.assertNull(expectedResult);
    }

    @Test
    public void Check_if_jars_are_not_found_returns_null(){
        ToolTips toolTips = new ToolTips();
        toolTips.fixturePath = "/src/test/resources/ToolTips";
        toolTips.toolchainPath = "/src/test/resources/ToolTips";

        String expectedResult = toolTips.getToolTip();
        Assert.assertNull(expectedResult);
    }
    @Test
    public void Check_if_only_bootstrap_tips_are_found_returns_tooltip(){
        ToolTips toolTips = new ToolTips();
        toolTips.fixturePath = "/src/test/resources/ToolTips";
        toolTips.toolchainPath = "/src/test/resources/ToolTips/toolchain";

        String expectedResult = toolTips.getToolTip();
        Assert.assertNotNull(expectedResult);

    }
    @Test
    public void Check_if_only_fixture_tips_are_found_returns_tooltips(){
        ToolTips toolTips = new ToolTips();
        toolTips.fixturePath = "/src/test/resources/ToolTips/fixtures";
        toolTips.toolchainPath = "/src/test/resources/ToolTips";

        String expectedResult = toolTips.getToolTip();
        Assert.assertNotNull(expectedResult);
    }
}
