package barsuift.simLife.j3d.tree;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Geometry;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import junit.framework.TestCase;
import barsuift.simLife.Percent;
import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;
import barsuift.simLife.j3d.Point3dState;
import barsuift.simLife.j3d.helper.ColorTestHelper;
import barsuift.simLife.j3d.helper.CompilerHelper;
import barsuift.simLife.j3d.helper.PointTestHelper;
import barsuift.simLife.j3d.helper.Structure3DHelper;
import barsuift.simLife.j3d.universe.MockUniverse3D;
import barsuift.simLife.j3d.util.ColorConstants;
import barsuift.simLife.tree.LeafUpdateCode;
import barsuift.simLife.tree.MockTreeLeaf;

public class BasicTreeLeaf3DTest extends TestCase {

    private MockTreeLeaf mockLeaf;

    private TreeLeaf3DState leaf3DState;

    private MockUniverse3D mockUniverse3D;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mockLeaf = new MockTreeLeaf();
        leaf3DState = DisplayDataCreatorForTests.createRandomTreeLeaf3DState();
        mockUniverse3D = new MockUniverse3D();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mockLeaf = null;
        leaf3DState = null;
        mockUniverse3D = null;
    }

    public void testConstructor() {
        try {
            new BasicTreeLeaf3D(mockUniverse3D, null, mockLeaf);
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTreeLeaf3D(mockUniverse3D, leaf3DState, null);
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTreeLeaf3D(null, leaf3DState, mockLeaf);
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testGetState() {
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(mockUniverse3D, leaf3DState, mockLeaf);
        assertEquals(leaf3DState, leaf3D.getState());
    }

    public void testObservers() {
        mockLeaf.setEfficiency(new Percent(80));
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(mockUniverse3D, leaf3DState, mockLeaf);
        assertEquals(1, mockLeaf.countObservers());
        // check the observer is the leaf3D
        mockLeaf.deleteObserver(leaf3D);
        assertEquals(0, mockLeaf.countObservers());
    }

    public void testAppearance() {
        mockLeaf.setEfficiency(new Percent(80));
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(mockUniverse3D, leaf3DState, mockLeaf);
        CompilerHelper.compile(leaf3D.getBranchGroup());
        Shape3D leafShape3D = (Shape3D) leaf3D.getBranchGroup().getChild(0);

        Color3f expectedColor = new Color3f(ColorConstants.brownYellow);
        expectedColor.interpolate(ColorConstants.green, 0.8f);
        ColorTestHelper.testColorFromMaterial(leafShape3D.getAppearance(), expectedColor, new Color3f(0.05f, 0.05f,
                0.05f), new Color3f(0.15f, 0.15f, 0.15f));

        // check the leaves are not culled (transparent) when seen from behind
        assertEquals(PolygonAttributes.CULL_NONE, leafShape3D.getAppearance().getPolygonAttributes().getCullFace());

        mockLeaf.setEfficiency(new Percent(75));
        leaf3D.update(mockLeaf, LeafUpdateCode.efficiency);

        // check that the color has changed as expected
        expectedColor = new Color3f(ColorConstants.brownYellow);
        expectedColor.interpolate(ColorConstants.green, 0.75f);
        ColorTestHelper.testColorFromMaterial(leafShape3D.getAppearance(), expectedColor, new Color3f(0.05f, 0.05f,
                0.05f), new Color3f(0.15f, 0.15f, 0.15f));


        mockLeaf.setEfficiency(new Percent(60));
        leaf3D.update(mockLeaf, null);

        // check that the color has NOT changed as expected, because the update code is not the appropriate one
        expectedColor = new Color3f(ColorConstants.brownYellow);
        expectedColor.interpolate(ColorConstants.green, 0.75f); // 75%, not 60%
        ColorTestHelper.testColorFromMaterial(leafShape3D.getAppearance(), expectedColor, new Color3f(0.05f, 0.05f,
                0.05f), new Color3f(0.15f, 0.15f, 0.15f));
    }

    public void testGeometry() {
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(mockUniverse3D, leaf3DState, mockLeaf);
        BranchGroup branchGroup = leaf3D.getBranchGroup();
        CompilerHelper.compile(branchGroup);
        Structure3DHelper.assertExactlyOneShape3D(branchGroup);
        Shape3D leafShape3D = (Shape3D) branchGroup.getChild(0);

        // test position and geometry
        Geometry leafGeometry = leafShape3D.getGeometry();
        assertTrue(leafGeometry instanceof TriangleArray);
        TriangleArray leafTriangle = (TriangleArray) leafGeometry;
        assertEquals(3, leafTriangle.getVertexCount());

        Point3d actualStartPoint = new Point3d();
        Point3d actualEndPoint1 = new Point3d();
        Point3d actualEndPoint2 = new Point3d();
        leafTriangle.getCoordinate(0, actualStartPoint);
        leafTriangle.getCoordinate(1, actualEndPoint1);
        leafTriangle.getCoordinate(2, actualEndPoint2);
        PointTestHelper.assertPointEquals(new Point3d(0, 0, 0), actualStartPoint);
        PointTestHelper.assertPointEquals(leaf3DState.getEndPoint1().toPointValue(), actualEndPoint1);
        PointTestHelper.assertPointEquals(leaf3DState.getEndPoint2().toPointValue(), actualEndPoint2);
    }

    public void testFall() {
        double oldRotation = Randomizer.randomRotation();
        leaf3DState.setRotation(oldRotation);
        Point3dState oldLeafAttachPoint = new Point3dState(1, 2, 3);
        leaf3DState.setLeafAttachPoint(oldLeafAttachPoint);
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(mockUniverse3D, leaf3DState, mockLeaf);

        // add the leaf into a graph with translation and rotation
        Transform3D transform3D = new Transform3D();
        Vector3d graphTranslation = new Vector3d(oldLeafAttachPoint.getX(), oldLeafAttachPoint.getY(),
                oldLeafAttachPoint.getZ());
        transform3D.set(graphTranslation);
        Transform3D rotation = new Transform3D();
        rotation.rotY(oldRotation);
        transform3D.mul(rotation);
        TransformGroup transformGroup = new TransformGroup(transform3D);
        BranchGroup branchGroup = new BranchGroup();
        branchGroup.addChild(transformGroup);
        transformGroup.addChild(leaf3D.getBranchGroup());
        CompilerHelper.addToLocale(branchGroup);

        // call to the fall() method
        leaf3D.update(null, LeafUpdateCode.fall);

        Point3dState newLeafAttachPoint = leaf3D.getState().getLeafAttachPoint();
        double newRotation = leaf3D.getState().getRotation();

        Point3d expectedAttachPoint = new Point3d(oldLeafAttachPoint.getX(), 0, oldLeafAttachPoint.getZ());
        assertEquals(expectedAttachPoint, newLeafAttachPoint.toPointValue());
        assertEquals(oldRotation, newRotation, 0.000001);
    }

    public void testGetArea() {
        leaf3DState = DisplayDataCreatorForTests.createSpecificTreeLeaf3DState();
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(mockUniverse3D, leaf3DState, mockLeaf);
        assertEquals(0.08, leaf3D.getArea(), 0.000001);
    }

    public void testIsMaxSizeReached() {
        leaf3DState = DisplayDataCreatorForTests.createSpecificTreeLeaf3DState();
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(mockUniverse3D, leaf3DState, mockLeaf);
        assertFalse(leaf3D.isMaxSizeReached());

        Point3dState initialEndPoint1 = leaf3DState.getInitialEndPoint1();
        leaf3DState.setEndPoint1(new Point3dState(10 * initialEndPoint1.getX(), 10 * initialEndPoint1.getY(),
                10 * initialEndPoint1.getZ()));
        Point3dState initialEndPoint2 = leaf3DState.getInitialEndPoint2();
        leaf3DState.setEndPoint2(new Point3dState(10 * initialEndPoint2.getX(), 10 * initialEndPoint2.getY(),
                10 * initialEndPoint2.getZ()));
        leaf3D = new BasicTreeLeaf3D(mockUniverse3D, leaf3DState, mockLeaf);
        assertTrue(leaf3D.isMaxSizeReached());
    }

    public void testIncreaseSize() {
        leaf3DState = DisplayDataCreatorForTests.createSpecificTreeLeaf3DState();
        leaf3DState.setEndPoint1(leaf3DState.getInitialEndPoint1());
        leaf3DState.setEndPoint2(leaf3DState.getInitialEndPoint2());
        Point3dState initialEndPoint1 = leaf3DState.getInitialEndPoint1();
        Point3dState initialEndPoint2 = leaf3DState.getInitialEndPoint2();
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(mockUniverse3D, leaf3DState, mockLeaf);
        Shape3D leafShape = (Shape3D) leaf3D.getBranchGroup().getChild(0);
        Point3d geomEndPoint1 = new Point3d();
        Point3d geomEndPoint2 = new Point3d();

        assertFalse(leaf3D.isMaxSizeReached());

        leaf3D.increaseSize();

        // test state
        Point3d endPoint1 = leaf3D.getState().getEndPoint1().toPointValue();
        Point3d expectedEndPoint1 = new Point3d(initialEndPoint1.getX() * 2, initialEndPoint1.getY() * 2,
                initialEndPoint1.getZ() * 2);
        PointTestHelper.assertPointEquals(expectedEndPoint1, endPoint1);
        Point3d endPoint2 = leaf3D.getState().getEndPoint2().toPointValue();
        Point3d expectedEndPoint2 = new Point3d(initialEndPoint2.getX() * 2, initialEndPoint2.getY() * 2,
                initialEndPoint2.getZ() * 2);
        PointTestHelper.assertPointEquals(expectedEndPoint2, endPoint2);
        // test geometry
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint1);
        PointTestHelper.assertPointEquals(leaf3D.getState().getEndPoint1().toPointValue(), geomEndPoint1);
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint2);
        PointTestHelper.assertPointEquals(leaf3D.getState().getEndPoint1().toPointValue(), geomEndPoint2);

        leaf3D.increaseSize();

        // test state
        endPoint1 = leaf3D.getState().getEndPoint1().toPointValue();
        expectedEndPoint1 = new Point3d(initialEndPoint1.getX() * 3, initialEndPoint1.getY() * 3, initialEndPoint1
                .getZ() * 3);
        PointTestHelper.assertPointEquals(expectedEndPoint1, endPoint1);
        endPoint2 = leaf3D.getState().getEndPoint2().toPointValue();
        expectedEndPoint2 = new Point3d(initialEndPoint2.getX() * 3, initialEndPoint2.getY() * 3, initialEndPoint2
                .getZ() * 3);
        PointTestHelper.assertPointEquals(expectedEndPoint2, endPoint2);
        // test geometry
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint1);
        PointTestHelper.assertPointEquals(leaf3D.getState().getEndPoint1().toPointValue(), geomEndPoint1);
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint2);
        PointTestHelper.assertPointEquals(leaf3D.getState().getEndPoint1().toPointValue(), geomEndPoint2);

        // increase up to the max size
        leaf3D.increaseSize();
        leaf3D.increaseSize();
        leaf3D.increaseSize();
        leaf3D.increaseSize();
        leaf3D.increaseSize();
        leaf3D.increaseSize();
        leaf3D.increaseSize();

        // test state
        assertTrue(leaf3D.isMaxSizeReached());
        endPoint1 = leaf3D.getState().getEndPoint1().toPointValue();
        expectedEndPoint1 = new Point3d(initialEndPoint1.getX() * 10, initialEndPoint1.getY() * 10, initialEndPoint1
                .getZ() * 10);
        PointTestHelper.assertPointEquals(expectedEndPoint1, endPoint1);
        endPoint2 = leaf3D.getState().getEndPoint2().toPointValue();
        expectedEndPoint2 = new Point3d(initialEndPoint2.getX() * 10, initialEndPoint2.getY() * 10, initialEndPoint2
                .getZ() * 10);
        PointTestHelper.assertPointEquals(expectedEndPoint2, endPoint2);
        // test geometry
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint1);
        PointTestHelper.assertPointEquals(leaf3D.getState().getEndPoint1().toPointValue(), geomEndPoint1);
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint2);
        PointTestHelper.assertPointEquals(leaf3D.getState().getEndPoint1().toPointValue(), geomEndPoint2);

        // nothing changes if we increase the leaf again
        leaf3D.increaseSize();

        // test state
        assertTrue(leaf3D.isMaxSizeReached());
        endPoint1 = leaf3D.getState().getEndPoint1().toPointValue();
        expectedEndPoint1 = new Point3d(initialEndPoint1.getX() * 10, initialEndPoint1.getY() * 10, initialEndPoint1
                .getZ() * 10);
        PointTestHelper.assertPointEquals(expectedEndPoint1, endPoint1);
        endPoint2 = leaf3D.getState().getEndPoint2().toPointValue();
        expectedEndPoint2 = new Point3d(initialEndPoint2.getX() * 10, initialEndPoint2.getY() * 10, initialEndPoint2
                .getZ() * 10);
        PointTestHelper.assertPointEquals(expectedEndPoint2, endPoint2);
        // test geometry
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint1);
        PointTestHelper.assertPointEquals(leaf3D.getState().getEndPoint1().toPointValue(), geomEndPoint1);
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint2);
        PointTestHelper.assertPointEquals(leaf3D.getState().getEndPoint1().toPointValue(), geomEndPoint2);
    }
}
