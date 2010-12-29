package barsuift.simLife.j3d.terrain;

import junit.framework.TestCase;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;


public class BasicLandscape3DTest extends TestCase {

    private BasicLandscape3D landscape3D;

    protected void setUp() throws Exception {
        super.setUp();
        Landscape3DState state = DisplayDataCreatorForTests.createSpecificLandscape3DState();
        landscape3D = new BasicLandscape3D(state);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        landscape3D = null;
    }

    public void testGetVertexIndix() {
        assertEquals(0, landscape3D.getVertexIndix(0, 0));
        assertEquals(1, landscape3D.getVertexIndix(1, 0));
        assertEquals(2, landscape3D.getVertexIndix(0, 1));
        assertEquals(3, landscape3D.getVertexIndix(1, 1));

        // the following points are not in landscape
        // we don't actually care about the returned values. The only assertion is that this method won't fail.
        landscape3D.getVertexIndix(-1, 0);
        landscape3D.getVertexIndix(0, -1);
        landscape3D.getVertexIndix(2, 0);
        landscape3D.getVertexIndix(0, 2);

    }

    public void testInLandscape() {
        double epsilon = 0.00001;

        assertFalse(landscape3D.inLandscape(0, -1));
        assertFalse(landscape3D.inLandscape(epsilon, -1));

        assertFalse(landscape3D.inLandscape(0, -epsilon));
        assertFalse(landscape3D.inLandscape(epsilon, -epsilon));
        assertFalse(landscape3D.inLandscape(1 - epsilon, -epsilon));
        assertFalse(landscape3D.inLandscape(1, -epsilon));

        assertFalse(landscape3D.inLandscape(-1, 0));
        assertFalse(landscape3D.inLandscape(-epsilon, 0));
        assertTrue(landscape3D.inLandscape(0, 0));
        assertTrue(landscape3D.inLandscape(epsilon, 0));
        assertTrue(landscape3D.inLandscape(1 - epsilon, 0));
        assertFalse(landscape3D.inLandscape(1, 0));
        assertFalse(landscape3D.inLandscape(1 + epsilon, 0));
        assertFalse(landscape3D.inLandscape(2, 0));

        assertFalse(landscape3D.inLandscape(-1, epsilon));
        assertFalse(landscape3D.inLandscape(-epsilon, epsilon));
        assertTrue(landscape3D.inLandscape(0, epsilon));
        assertTrue(landscape3D.inLandscape(epsilon, epsilon));
        assertTrue(landscape3D.inLandscape(1 - epsilon, epsilon));
        assertFalse(landscape3D.inLandscape(1, epsilon));
        assertFalse(landscape3D.inLandscape(1 + epsilon, epsilon));
        assertFalse(landscape3D.inLandscape(2, epsilon));

        assertFalse(landscape3D.inLandscape(-1, 1 - epsilon));
        assertFalse(landscape3D.inLandscape(-epsilon, 1 - epsilon));
        assertTrue(landscape3D.inLandscape(0, 1 - epsilon));
        assertTrue(landscape3D.inLandscape(epsilon, 1 - epsilon));
        assertTrue(landscape3D.inLandscape(1 - epsilon, 1 - epsilon));
        assertFalse(landscape3D.inLandscape(1, 1 - epsilon));
        assertFalse(landscape3D.inLandscape(1 + epsilon, 1 - epsilon));
        assertFalse(landscape3D.inLandscape(2, 1 - epsilon));

        assertFalse(landscape3D.inLandscape(-1, 1));
        assertFalse(landscape3D.inLandscape(-epsilon, 1));
        assertFalse(landscape3D.inLandscape(0, 1));
        assertFalse(landscape3D.inLandscape(epsilon, 1));
        assertFalse(landscape3D.inLandscape(1 - epsilon, 1));
        assertFalse(landscape3D.inLandscape(1, 1));
        assertFalse(landscape3D.inLandscape(1 + epsilon, 1));
        assertFalse(landscape3D.inLandscape(2, 1));

        assertFalse(landscape3D.inLandscape(0, 1 + epsilon));
        assertFalse(landscape3D.inLandscape(epsilon, 1 + epsilon));
        assertFalse(landscape3D.inLandscape(1 - epsilon, 1 + epsilon));
        assertFalse(landscape3D.inLandscape(1, 1 + epsilon));

        assertFalse(landscape3D.inLandscape(0, 2));
    }

    public void testGetHeight() {
        double epsilon = 0.00001;
        double delta = 0.001;

        assertEquals(0.0, landscape3D.getHeight(0.0, 0.0), delta);
        assertEquals(0.2, landscape3D.getHeight(0.5, 0.0), delta);
        assertEquals(0.4, landscape3D.getHeight(1.0 - epsilon, 0.0), delta);

        assertEquals(0.3, landscape3D.getHeight(0.0, 0.5), delta);
        assertEquals(0.5, landscape3D.getHeight(0.5, 0.5), delta);
        assertEquals(0.8, landscape3D.getHeight(1.0 - epsilon, 0.5), delta);

        assertEquals(0.6, landscape3D.getHeight(0.0, 1.0 - epsilon), delta);
        assertEquals(0.9, landscape3D.getHeight(0.5, 1.0 - epsilon), delta);
        assertEquals(1.2, landscape3D.getHeight(1.0 - epsilon, 1.0 - epsilon), delta);

        assertEquals(0.28, landscape3D.getHeight(0.4, 0.2), delta);
        assertEquals(0.32, landscape3D.getHeight(0.2, 0.4), delta);

        assertEquals(0.6, landscape3D.getHeight(0.8, 0.4), delta);
        assertEquals(0.68, landscape3D.getHeight(0.4, 0.8), delta);


        // when the position is not in the landscape, we simply return 0
        assertEquals(0d, landscape3D.getHeight(-1, 0));
        assertEquals(0d, landscape3D.getHeight(0, -1));
        assertEquals(0d, landscape3D.getHeight(2, 0));
        assertEquals(0d, landscape3D.getHeight(0, 2));

        assertEquals(0d, landscape3D.getHeight(0, -epsilon));
        assertEquals(0d, landscape3D.getHeight(1, -epsilon));
        assertEquals(0d, landscape3D.getHeight(-epsilon, 0));
        assertEquals(0d, landscape3D.getHeight(1d + epsilon, 0));
        assertEquals(0d, landscape3D.getHeight(-epsilon, 1));
        assertEquals(0d, landscape3D.getHeight(1 + epsilon, 1));
        assertEquals(0d, landscape3D.getHeight(0, 1 + epsilon));
        assertEquals(0d, landscape3D.getHeight(1, 1 + epsilon));
    }

}
