package barsuift.simLife.j3d;

import junit.framework.TestCase;


public class SimLifeCanvas3DStateFactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateRandomUniverseContextState() {
        SimLifeCanvas3DStateFactory factory = new SimLifeCanvas3DStateFactory();
        SimLifeCanvas3DState canvasState = factory.createRandomCanvasState();
        assertFalse(canvasState.isFpsShowing());
    }


    public void testCreateEmptyUniverseContextState() {
        SimLifeCanvas3DStateFactory factory = new SimLifeCanvas3DStateFactory();
        SimLifeCanvas3DState canvasState = factory.createEmptyCanvasState();
        assertFalse(canvasState.isFpsShowing());
    }

}
