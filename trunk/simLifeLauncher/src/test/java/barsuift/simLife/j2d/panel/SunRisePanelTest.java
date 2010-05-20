package barsuift.simLife.j2d.panel;

import junit.framework.TestCase;
import barsuift.simLife.Percent;
import barsuift.simLife.environment.MockSun;


public class SunRisePanelTest extends TestCase {

    private MockSun sun;

    private SunRisePanel display;

    protected void setUp() throws Exception {
        super.setUp();
        sun = new MockSun();
        display = new SunRisePanel(sun);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        sun = null;
        display = null;
    }

    public void testInit() {
        assertEquals(sun.getRiseAngle(), new Percent(display.getSlider().getValue()));
        sun.setRiseAngle(new Percent(90));
        display = new SunRisePanel(sun);
        assertEquals(sun.getRiseAngle(), new Percent(display.getSlider().getValue()));
        sun.setRiseAngle(new Percent(80));
        display = new SunRisePanel(sun);
        assertEquals(sun.getRiseAngle(), new Percent(display.getSlider().getValue()));
        sun.setRiseAngle(new Percent(100));
        display = new SunRisePanel(sun);
        assertEquals(sun.getRiseAngle(), new Percent(display.getSlider().getValue()));
    }

}
