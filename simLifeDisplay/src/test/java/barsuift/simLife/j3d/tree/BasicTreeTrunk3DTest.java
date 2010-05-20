package barsuift.simLife.j3d.tree;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import junit.framework.TestCase;
import barsuift.simLife.j3d.helper.ColorTestHelper;
import barsuift.simLife.j3d.tree.helper.BasicTreeTrunk3DTestHelper;
import barsuift.simLife.j3d.universe.MockUniverse3D;
import barsuift.simLife.j3d.util.ColorConstants;
import barsuift.simLife.tree.MockTreeTrunk;

public class BasicTreeTrunk3DTest extends TestCase {

    private TreeTrunk3DState trunk3DState;

    private MockTreeTrunk mockTrunk;

    private MockUniverse3D mockUniverse3D;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TreeTrunk3DStateFactory stateFactory = new TreeTrunk3DStateFactory();
        trunk3DState = stateFactory.createRandomTreeTrunk3DState();
        mockTrunk = new MockTreeTrunk();
        mockUniverse3D = new MockUniverse3D();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        trunk3DState = null;
        mockTrunk = null;
        mockUniverse3D = null;
    }

    public void testConstructor() {
        try {
            new BasicTreeTrunk3D(mockUniverse3D, null, mockTrunk);
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTreeTrunk3D(mockUniverse3D, trunk3DState, null);
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTreeTrunk3D(null, trunk3DState, mockTrunk);
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testGetState() {
        BasicTreeTrunk3D trunk3D = new BasicTreeTrunk3D(mockUniverse3D, trunk3DState, mockTrunk);
        assertEquals(trunk3DState, trunk3D.getState());
    }

    public void testCreateTrunk() {
        float radius = 0.5f;
        float height = 4;
        mockTrunk.setHeight(height);
        mockTrunk.setRadius(radius);
        BasicTreeTrunk3D trunk3D = new BasicTreeTrunk3D(mockUniverse3D, trunk3DState, mockTrunk);

        Point3d expectedLowerBottom = new Point3d(-0.5, -2, -0.5);
        Point3d expectedUpperBottom = new Point3d(0.5, -2, 0.5);
        Point3d expectedMovedLowerBottom = new Point3d(-0.5, 0, -0.5);
        Point3d expectedMovedUpperBottom = new Point3d(0.5, 0, 0.5);
        Point3d expectedLowerTop = new Point3d(-0.5, 2, -0.5);
        Point3d expectedUpperTop = new Point3d(0.5, 2, 0.5);
        Point3d expectedMovedLowerTop = new Point3d(-0.5, 4, -0.5);
        Point3d expectedMovedUpperTop = new Point3d(0.5, 4, 0.5);
        BasicTreeTrunk3DTestHelper.checkTrunk3D(trunk3D, height, radius, expectedLowerBottom, expectedUpperBottom,
                expectedMovedLowerBottom, expectedMovedUpperBottom, expectedLowerTop, expectedUpperTop,
                expectedMovedLowerTop, expectedMovedUpperTop);
        ColorTestHelper.testColorFromMaterial(trunk3D.getTrunk().getAppearance(), ColorConstants.brown, new Color3f(
                0.05f, 0.05f, 0.05f), new Color3f(0.15f, 0.15f, 0.15f));
    }

}
