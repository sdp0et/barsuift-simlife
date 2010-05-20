package barsuift.simLife.j2d.panel;

import junit.framework.TestCase;
import barsuift.simLife.Percent;
import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.j3d.universe.environment.MockSun3D;


public class SunColorPanelTest extends TestCase {

    private MockSun3D mockSun;

    private SunColorPanel display;

    protected void setUp() throws Exception {
        super.setUp();
        mockSun = new MockSun3D();
        display = new SunColorPanel(mockSun);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        mockSun = null;
        display = null;
    }

    public void testInit() {
        assertEquals(mockSun.getWhiteFactor(), new Percent(display.getSlider().getValue()));
        mockSun.setWhiteFactor(new Percent(90));
        display = new SunColorPanel(mockSun);
        assertEquals(mockSun.getWhiteFactor(), new Percent(display.getSlider().getValue()));
        mockSun.setWhiteFactor(new Percent(80));
        display = new SunColorPanel(mockSun);
        assertEquals(mockSun.getWhiteFactor(), new Percent(display.getSlider().getValue()));
        mockSun.setWhiteFactor(new Percent(100));
        display = new SunColorPanel(mockSun);
        assertEquals(mockSun.getWhiteFactor(), new Percent(display.getSlider().getValue()));
    }

    public void testUpdate() {
        assertEquals(mockSun.getWhiteFactor(), new Percent(display.getSlider().getValue()));
        mockSun.setWhiteFactor(new Percent(90));
        display.update(mockSun, SunUpdateCode.color);
        assertEquals(mockSun.getWhiteFactor(), new Percent(display.getSlider().getValue()));
        mockSun.setWhiteFactor(new Percent(90));
        display.update(mockSun, SunUpdateCode.color);
        assertEquals(mockSun.getWhiteFactor(), new Percent(display.getSlider().getValue()));
        mockSun.setWhiteFactor(new Percent(100));
        display.update(mockSun, SunUpdateCode.color);
        assertEquals(mockSun.getWhiteFactor(), new Percent(display.getSlider().getValue()));
    }

}
