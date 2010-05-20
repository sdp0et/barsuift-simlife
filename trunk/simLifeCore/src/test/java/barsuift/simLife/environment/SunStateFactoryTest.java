package barsuift.simLife.environment;

import barsuift.simLife.PercentState;
import junit.framework.TestCase;


public class SunStateFactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateSunState() {
        SunStateFactory factory = new SunStateFactory();
        SunState sunState = factory.createSunState();
        PercentState luminosity = sunState.getLuminosity();
        assertEquals(1.00, luminosity.getValue().doubleValue());
        PercentState riseAngle = sunState.getRiseAngle();
        assertEquals(0.25, riseAngle.getValue().doubleValue());
        PercentState zenithAngle = sunState.getZenithAngle();
        assertEquals(0.50, zenithAngle.getValue().doubleValue());
    }

}
