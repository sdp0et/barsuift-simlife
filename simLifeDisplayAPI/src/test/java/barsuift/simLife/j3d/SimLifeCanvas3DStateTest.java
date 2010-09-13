package barsuift.simLife.j3d;

import barsuift.simLife.JaxbTestCase;


public class SimLifeCanvas3DStateTest extends JaxbTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected String getPackage() {
        return "barsuift.simLife.j3d";
    }

    public void testJaxb() throws Exception {
        SimLifeCanvas3DState canvasState = DisplayDataCreatorForTests.createSpecificCanvasState();
        write(canvasState);
        SimLifeCanvas3DState canvasState2 = (SimLifeCanvas3DState) read();
        assertEquals(canvasState, canvasState2);
    }
}
