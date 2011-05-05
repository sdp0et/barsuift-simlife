package barsuift.simLife.j3d.tree;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import junit.framework.TestCase;
import barsuift.simLife.j3d.helper.PointTestHelper;


public class TreeLeavesOrganizerTest extends TestCase {

    private TreeLeavesOrganizer leavesOrganizer;

    protected void setUp() throws Exception {
        super.setUp();
        leavesOrganizer = new TreeLeavesOrganizer();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        leavesOrganizer = null;
    }

    public void testOrganizeLeaves() {
        float branchLength = 6;
        List<TreeLeaf3DState> leavesStates = new ArrayList<TreeLeaf3DState>();
        TreeLeaf3DState leaf1 = new TreeLeaf3DState();
        leavesStates.add(leaf1);
        TreeLeaf3DState leaf2 = new TreeLeaf3DState();
        leavesStates.add(leaf2);
        leavesOrganizer.organizeLeaves(leavesStates, branchLength);

        Vector3f translation1 = new Vector3f();
        leaf1.getTransform().toTransform3D().get(translation1);
        assertTrue(translation1.getX() >= 0);
        assertTrue(translation1.getX() < 3);
        assertEquals(0f, translation1.getY(), 0.0001);
        assertEquals(0f, translation1.getZ(), 0.0001);

        Vector3f translation2 = new Vector3f();
        leaf2.getTransform().toTransform3D().get(translation2);
        assertTrue(translation2.getX() >= 3);
        assertTrue(translation2.getX() < 6);
        assertEquals(0f, translation2.getY(), 0.0001);
        assertEquals(0f, translation2.getZ(), 0.0001);

    }


    public void testComputeNewLeafTranslation1() {
        // create objects
        MockTreeLeaf3D leaf3D1 = new MockTreeLeaf3D();
        leaf3D1.setPosition(new Point3f(2, 0, 0));
        MockTreeLeaf3D leaf3D2 = new MockTreeLeaf3D();
        leaf3D2.setPosition(new Point3f(3, 0, 0));
        List<TreeLeaf3D> leaves3D = new ArrayList<TreeLeaf3D>(2);
        leaves3D.add(leaf3D1);
        leaves3D.add(leaf3D2);
        float branchLength = 3.5f;
        // the branch is along the X axis
        // it starts at 0
        // the leaves are at 2 and 3
        // it ends at 3.5
        // 0 ...... ...... 2 ...... 3 ... 3.5
        // the new leaf should be created around 1 (+/- 10% * distance -> 0.2)
        Point3f boundsStartPoint = new Point3f(0.8f, 0, 0);
        Point3f boundsEndPoint = new Point3f(1.2f, 0, 0);
        TreeLeavesOrganizer leavesOrganizer = new TreeLeavesOrganizer();
        Vector3f newLeafTranslation = leavesOrganizer.computeNewLeafTranslation(leaves3D, branchLength);
        PointTestHelper.assertPointIsWithinBounds(new Point3f(newLeafTranslation), boundsStartPoint, boundsEndPoint);
    }

    public void testComputeNewLeafTranslation2() {
        // create objects
        MockTreeLeaf3D leaf3D1 = new MockTreeLeaf3D();
        leaf3D1.setPosition(new Point3f(1, 0, 0));
        MockTreeLeaf3D leaf3D2 = new MockTreeLeaf3D();
        leaf3D2.setPosition(new Point3f(5, 0, 0));
        List<TreeLeaf3D> leaves3D = new ArrayList<TreeLeaf3D>(2);
        leaves3D.add(leaf3D1);
        leaves3D.add(leaf3D2);
        float branchLength = 7f;
        // the branch is along the X axis
        // it starts at 0
        // the leaves are at 1 and 5
        // it ends at 7
        // 0 ...... 1 ...... ...... ...... ...... 5 ...... ...... 7
        // the new leaf should be created around 3 (+/- 10% * distance -> 0.4)
        Point3f boundsStartPoint = new Point3f(2.6f, 0, 0);
        Point3f boundsEndPoint = new Point3f(3.4f, 0, 0);
        TreeLeavesOrganizer leavesOrganizer = new TreeLeavesOrganizer();
        Vector3f newLeafTranslation = leavesOrganizer.computeNewLeafTranslation(leaves3D, branchLength);
        PointTestHelper.assertPointIsWithinBounds(new Point3f(newLeafTranslation), boundsStartPoint, boundsEndPoint);
    }

    public void testComputeNewLeafTranslation3() {
        // create objects
        MockTreeLeaf3D leaf3D1 = new MockTreeLeaf3D();
        leaf3D1.setPosition(new Point3f(2, 0, 0));
        MockTreeLeaf3D leaf3D2 = new MockTreeLeaf3D();
        leaf3D2.setPosition(new Point3f(3, 0, 0));
        List<TreeLeaf3D> leaves3D = new ArrayList<TreeLeaf3D>(2);
        leaves3D.add(leaf3D1);
        leaves3D.add(leaf3D2);
        float branchLength = 6f;
        // the branch is along the X axis
        // it starts at 0
        // the leaves are at 2 and 3
        // it ends at 6
        // 0 ...... ...... 2 ...... 3 ...... ...... ...... 6
        // the new leaf should be created around 4.5 (+/- 10% * distance -> 0.3)
        Point3f boundsStartPoint = new Point3f(4.2f, 0, 0);
        Point3f boundsEndPoint = new Point3f(4.8f, 0, 0);
        TreeLeavesOrganizer leavesOrganizer = new TreeLeavesOrganizer();
        Vector3f newLeafTranslation = leavesOrganizer.computeNewLeafTranslation(leaves3D, branchLength);
        PointTestHelper.assertPointIsWithinBounds(new Point3f(newLeafTranslation), boundsStartPoint, boundsEndPoint);
    }

    /**
     * Test if the leaves are not created in the order based on the distance to the branch start.
     */
    public void testComputeNewLeafTranslation4() {
        // create objects
        MockTreeLeaf3D leaf3D1 = new MockTreeLeaf3D();
        leaf3D1.setPosition(new Point3f(2, 0, 0));
        MockTreeLeaf3D leaf3D2 = new MockTreeLeaf3D();
        leaf3D2.setPosition(new Point3f(3, 0, 0));
        List<TreeLeaf3D> leaves3D = new ArrayList<TreeLeaf3D>(2);
        // add the leaves in the "wrong" order
        leaves3D.add(leaf3D2);
        leaves3D.add(leaf3D1);
        float branchLength = 3.5f;
        // the branch is along the X axis
        // it starts at 0
        // the leaves are at 2 and 3
        // it ends at 3.5
        // 0 ...... ...... 2 ...... 3 ... 3.5
        // the new leaf should be created around 1 (+/- 10% * distance -> 0.2)
        Point3f boundsStartPoint = new Point3f(0.8f, 0, 0);
        Point3f boundsEndPoint = new Point3f(1.2f, 0, 0);
        TreeLeavesOrganizer leavesOrganizer = new TreeLeavesOrganizer();
        Vector3f newLeafTranslation = leavesOrganizer.computeNewLeafTranslation(leaves3D, branchLength);
        PointTestHelper.assertPointIsWithinBounds(new Point3f(newLeafTranslation), boundsStartPoint, boundsEndPoint);
    }

}
