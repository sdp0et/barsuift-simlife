package barsuift.simLife.j3d.environment;

import junit.framework.TestCase;
import barsuift.simLife.PlanetParameters;
import barsuift.simLife.landscape.LandscapeParameters;


public class Sun3DStateFactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateSun3DState() {
        LandscapeParameters landscapeParameters = new LandscapeParameters();
        PlanetParameters planetParameters = new PlanetParameters();
        Sun3DStateFactory factory = new Sun3DStateFactory();
        Sun3DState sunState = factory.createSun3DState(planetParameters, landscapeParameters);
        assertEquals(0.0f, sunState.getEarthRotation(), 0.0001);
        assertEquals(0.0f, sunState.getEarthRevolution(), 0.0001);
        assertNotNull(sunState.getEarthRotationTask());
        assertTrue(sunState.isEarthRotationTaskAutomatic());
        assertTrue(sunState.isEarthRevolutionTaskAutomatic());
    }

}
