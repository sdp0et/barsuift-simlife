package barsuift.simLife.tree;

import java.math.BigDecimal;

import javax.vecmath.Point3d;

import junit.framework.TestCase;
import barsuift.simLife.j3d.Point3dState;
import barsuift.simLife.j3d.tree.TreeLeaf3DState;
import barsuift.simLife.universe.MockUniverse;
import barsuift.simLife.universe.Universe;


public class BasicTreeLeafFactoryTest extends TestCase {

    private BasicTreeLeafFactory factory;

    protected void setUp() throws Exception {
        super.setUp();
        Universe universe = new MockUniverse();
        factory = new BasicTreeLeafFactory(universe);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        factory = null;
    }

    public void testCreateRandom() {
        Point3d leafAttachPoint = new Point3d(Math.random(), Math.random(), Math.random());
        TreeLeaf leaf = factory.createRandom(leafAttachPoint);
        TreeLeaf3DState leaf3dState = leaf.getTreeLeaf3D().getState();
        // test the leaf created is a random one (by testing the 3d leaf)
        Point3dState initialEndPoint1 = leaf3dState.getInitialEndPoint1();
        Point3dState initialEndPoint2 = leaf3dState.getInitialEndPoint2();
        Point3dState expectedEndPoint1 = new Point3dState(initialEndPoint1.getX() * 10, initialEndPoint1.getY() * 10,
                initialEndPoint1.getZ() * 10);
        Point3dState expectedEndPoint2 = new Point3dState(initialEndPoint2.getX() * 10, initialEndPoint2.getY() * 10,
                initialEndPoint2.getZ() * 10);
        assertEquals(expectedEndPoint1, leaf3dState.getEndPoint1());
        assertEquals(expectedEndPoint2, leaf3dState.getEndPoint2());
    }

    public void testCreateNew() {
        Point3d leafAttachPoint = new Point3d(Math.random(), Math.random(), Math.random());
        BigDecimal energy = new BigDecimal(30);
        TreeLeaf leaf = factory.createNew(leafAttachPoint, energy);
        assertEquals(energy, leaf.getEnergy());
        TreeLeaf3DState leaf3dState = leaf.getTreeLeaf3D().getState();
        // test the leaf created is a new one (by testing the 3d leaf)
        assertEquals(leaf3dState.getInitialEndPoint1(), leaf3dState.getEndPoint1());
        assertEquals(leaf3dState.getInitialEndPoint2(), leaf3dState.getEndPoint2());
    }

}
