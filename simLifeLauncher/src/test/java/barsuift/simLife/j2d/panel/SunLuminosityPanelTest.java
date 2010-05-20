package barsuift.simLife.j2d.panel;

import junit.framework.TestCase;
import barsuift.simLife.Percent;
import barsuift.simLife.environment.MockSun;
import barsuift.simLife.environment.SunUpdateCode;


public class SunLuminosityPanelTest extends TestCase {

    private MockSun mockSun;

    private SunLuminosityPanel display;

    protected void setUp() throws Exception {
        super.setUp();
        mockSun = new MockSun();
        display = new SunLuminosityPanel(mockSun);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        mockSun = null;
        display = null;
    }

    public void testInit() {
        assertEquals(mockSun.getLuminosity(), new Percent(display.getSlider().getValue()));
        mockSun.setLuminosity(new Percent(90));
        display = new SunLuminosityPanel(mockSun);
        assertEquals(mockSun.getLuminosity(), new Percent(display.getSlider().getValue()));
        mockSun.setLuminosity(new Percent(80));
        display = new SunLuminosityPanel(mockSun);
        assertEquals(mockSun.getLuminosity(), new Percent(display.getSlider().getValue()));
        mockSun.setLuminosity(new Percent(100));
        display = new SunLuminosityPanel(mockSun);
        assertEquals(mockSun.getLuminosity(), new Percent(display.getSlider().getValue()));
    }

    public void testUpdate() {
        assertEquals("Sun luminosity (100.00%)", display.getLabel().getText());
        assertEquals(mockSun.getLuminosity(), new Percent(display.getSlider().getValue()));
        mockSun.setLuminosity(new Percent(90));
        display.update(mockSun, SunUpdateCode.luminosity);
        assertEquals("Sun luminosity (90.00%)", display.getLabel().getText());
        assertEquals(mockSun.getLuminosity(), new Percent(display.getSlider().getValue()));
        mockSun.setLuminosity(new Percent(90));
        display.update(mockSun, SunUpdateCode.luminosity);
        assertEquals("Sun luminosity (90.00%)", display.getLabel().getText());
        assertEquals(mockSun.getLuminosity(), new Percent(display.getSlider().getValue()));
        mockSun.setLuminosity(new Percent(100));
        display.update(mockSun, SunUpdateCode.luminosity);
        assertEquals("Sun luminosity (100.00%)", display.getLabel().getText());
        assertEquals(mockSun.getLuminosity(), new Percent(display.getSlider().getValue()));
    }

}
