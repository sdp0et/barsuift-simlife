package barsuift.simLife.j3d;

import junit.framework.TestCase;
import barsuift.simLife.time.FpsCounter;


public class BasicSimLifeCanvas3DTest extends TestCase {

    private BasicSimLifeCanvas3D canvas;

    private SimLifeCanvas3DState state;

    protected void setUp() throws Exception {
        super.setUp();
        FpsCounter coreFpsCounter = new FpsCounter();
        state = DisplayDataCreatorForTests.createSpecificCanvasState();
        canvas = new BasicSimLifeCanvas3D(coreFpsCounter, state);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        state = null;
        canvas = null;
    }

    public void testIsFpsShowing() {
        assertFalse(canvas.isFpsShowing());
        canvas.setFpsShowing(true);
        assertTrue(canvas.isFpsShowing());
    }

    public void testGetState() {
        assertEquals(state, canvas.getState());
        assertSame(state, canvas.getState());
        assertFalse(canvas.getState().isFpsShowing());
        canvas.setFpsShowing(true);
        assertEquals(state, canvas.getState());
        assertSame(state, canvas.getState());
        assertTrue(canvas.getState().isFpsShowing());
    }

}
