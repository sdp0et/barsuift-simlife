package barsuift.simLife.j2d.panel;

import junit.framework.TestCase;
import barsuift.simLife.Percent;
import barsuift.simLife.environment.MockSun;


public class SunZenithPanelTest extends TestCase {

    private MockSun mockSun;

    private SunZenithPanel display;

    protected void setUp() throws Exception {
        super.setUp();
        mockSun = new MockSun();
        display = new SunZenithPanel(mockSun);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        mockSun = null;
        display = null;
    }

    public void testInit() {
        assertEquals(mockSun.getZenithAngle(), new Percent(display.getSlider().getValue()));
        mockSun.setZenithAngle(new Percent(90));
        display = new SunZenithPanel(mockSun);
        assertEquals(mockSun.getZenithAngle(), new Percent(display.getSlider().getValue()));
        mockSun.setZenithAngle(new Percent(80));
        display = new SunZenithPanel(mockSun);
        assertEquals(mockSun.getZenithAngle(), new Percent(display.getSlider().getValue()));
        mockSun.setZenithAngle(new Percent(100));
        display = new SunZenithPanel(mockSun);
        assertEquals(mockSun.getZenithAngle(), new Percent(display.getSlider().getValue()));
    }

}
