package barsuift.simLife.j3d.tree;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.fest.assertions.Delta;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static barsuift.simLife.j3d.assertions.Point3fAssert.assertThat;

import static org.fest.assertions.Assertions.assertThat;


public class TreeLeavesOrganizerTest {

    private TreeLeavesOrganizer leavesOrganizer;

    @BeforeMethod
    protected void setUp() {
        leavesOrganizer = new TreeLeavesOrganizer();
    }

    @AfterMethod
    protected void tearDown() {
        leavesOrganizer = null;
    }

    @Test
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
        assertThat(translation1.getX()).isEqualTo(0f, Delta.delta(0.0001));
        assertThat(translation1.getY()).isGreaterThanOrEqualTo(0);
        assertThat(translation1.getY()).isLessThan(3);
        assertThat(translation1.getZ()).isEqualTo(0f, Delta.delta(0.0001));

        Vector3f translation2 = new Vector3f();
        leaf2.getTransform().toTransform3D().get(translation2);
        assertThat(translation2.getX()).isEqualTo(0f, Delta.delta(0.0001));
        assertThat(translation2.getY()).isGreaterThanOrEqualTo(3);
        assertThat(translation2.getY()).isLessThan(6);
        assertThat(translation2.getZ()).isEqualTo(0f, Delta.delta(0.0001));

    }


    @Test
    public void testComputeNewLeafTranslation1() {
        // create objects
        MockTreeLeaf3D leaf3D1 = new MockTreeLeaf3D();
        leaf3D1.setPosition(new Point3f(0, 2, 0));
        MockTreeLeaf3D leaf3D2 = new MockTreeLeaf3D();
        leaf3D2.setPosition(new Point3f(0, 3, 0));
        List<TreeLeaf3D> leaves3D = new ArrayList<TreeLeaf3D>(2);
        leaves3D.add(leaf3D1);
        leaves3D.add(leaf3D2);
        float branchLength = 3.5f;
        // the branch is along the Y axis
        // it starts at 0
        // the leaves are at 2 and 3
        // it ends at 3.5
        // 0 ...... ...... 2 ...... 3 ... 3.5
        // the new leaf should be created around 1 (+/- 10% * distance -> 0.2)
        Point3f boundsStartPoint = new Point3f(0, 0.8f, 0);
        Point3f boundsEndPoint = new Point3f(0, 1.2f, 0);
        TreeLeavesOrganizer leavesOrganizer = new TreeLeavesOrganizer();
        Vector3f newLeafTranslation = leavesOrganizer.computeNewLeafTranslation(leaves3D, branchLength);
        assertThat(new Point3f(newLeafTranslation)).isWithin(boundsStartPoint, boundsEndPoint);
    }

    @Test
    public void testComputeNewLeafTranslation2() {
        // create objects
        MockTreeLeaf3D leaf3D1 = new MockTreeLeaf3D();
        leaf3D1.setPosition(new Point3f(0, 1, 0));
        MockTreeLeaf3D leaf3D2 = new MockTreeLeaf3D();
        leaf3D2.setPosition(new Point3f(0, 5, 0));
        List<TreeLeaf3D> leaves3D = new ArrayList<TreeLeaf3D>(2);
        leaves3D.add(leaf3D1);
        leaves3D.add(leaf3D2);
        float branchLength = 7f;
        // the branch is along the Y axis
        // it starts at 0
        // the leaves are at 1 and 5
        // it ends at 7
        // 0 ...... 1 ...... ...... ...... ...... 5 ...... ...... 7
        // the new leaf should be created around 3 (+/- 10% * distance -> 0.4)
        Point3f boundsStartPoint = new Point3f(0, 2.6f, 0);
        Point3f boundsEndPoint = new Point3f(0, 3.4f, 0);
        TreeLeavesOrganizer leavesOrganizer = new TreeLeavesOrganizer();
        Vector3f newLeafTranslation = leavesOrganizer.computeNewLeafTranslation(leaves3D, branchLength);
        assertThat(new Point3f(newLeafTranslation)).isWithin(boundsStartPoint, boundsEndPoint);
    }

    @Test
    public void testComputeNewLeafTranslation3() {
        // create objects
        MockTreeLeaf3D leaf3D1 = new MockTreeLeaf3D();
        leaf3D1.setPosition(new Point3f(0, 2, 0));
        MockTreeLeaf3D leaf3D2 = new MockTreeLeaf3D();
        leaf3D2.setPosition(new Point3f(0, 3, 0));
        List<TreeLeaf3D> leaves3D = new ArrayList<TreeLeaf3D>(2);
        leaves3D.add(leaf3D1);
        leaves3D.add(leaf3D2);
        float branchLength = 6f;
        // the branch is along the Y axis
        // it starts at 0
        // the leaves are at 2 and 3
        // it ends at 6
        // 0 ...... ...... 2 ...... 3 ...... ...... ...... 6
        // the new leaf should be created around 4.5 (+/- 10% * distance -> 0.3)
        Point3f boundsStartPoint = new Point3f(0, 4.2f, 0);
        Point3f boundsEndPoint = new Point3f(0, 4.8f, 0);
        TreeLeavesOrganizer leavesOrganizer = new TreeLeavesOrganizer();
        Vector3f newLeafTranslation = leavesOrganizer.computeNewLeafTranslation(leaves3D, branchLength);
        assertThat(new Point3f(newLeafTranslation)).isWithin(boundsStartPoint, boundsEndPoint);
    }

    /**
     * Test if the leaves are not created in the order based on the distance to the branch start.
     */
    @Test
    public void testComputeNewLeafTranslation4() {
        // create objects
        MockTreeLeaf3D leaf3D1 = new MockTreeLeaf3D();
        leaf3D1.setPosition(new Point3f(0, 2, 0));
        MockTreeLeaf3D leaf3D2 = new MockTreeLeaf3D();
        leaf3D2.setPosition(new Point3f(0, 3, 0));
        List<TreeLeaf3D> leaves3D = new ArrayList<TreeLeaf3D>(2);
        // add the leaves in the "wrong" order
        leaves3D.add(leaf3D2);
        leaves3D.add(leaf3D1);
        float branchLength = 3.5f;
        // the branch is along the Y axis
        // it starts at 0
        // the leaves are at 2 and 3
        // it ends at 3.5
        // 0 ...... ...... 2 ...... 3 ... 3.5
        // the new leaf should be created around 1 (+/- 10% * distance -> 0.2)
        Point3f boundsStartPoint = new Point3f(0, 0.8f, 0);
        Point3f boundsEndPoint = new Point3f(0, 1.2f, 0);
        TreeLeavesOrganizer leavesOrganizer = new TreeLeavesOrganizer();
        Vector3f newLeafTranslation = leavesOrganizer.computeNewLeafTranslation(leaves3D, branchLength);
        assertThat(new Point3f(newLeafTranslation)).isWithin(boundsStartPoint, boundsEndPoint);
    }

}
