package barsuift.simLife.j3d;

import barsuift.simLife.JaxbTestCase;


public class Point3dStateTest extends JaxbTestCase {

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
        Point3dState pointState = DisplayDataCreatorForTests.createRandomPointState();
        write(pointState);
        Point3dState pointState2 = (Point3dState) read();
        assertEquals(pointState, pointState2);
    }

}
