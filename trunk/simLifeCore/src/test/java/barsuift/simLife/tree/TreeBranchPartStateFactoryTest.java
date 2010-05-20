package barsuift.simLife.tree;

import java.math.BigDecimal;
import java.util.List;

import javax.vecmath.Point3d;

import junit.framework.TestCase;
import barsuift.simLife.j3d.Point3dState;
import barsuift.simLife.j3d.helper.PointTestHelper;


public class TreeBranchPartStateFactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateRandomBranchPartState() {

        TreeBranchPartStateFactory factory = new TreeBranchPartStateFactory();
        Point3d branchPartEndPoint = new Point3d(Math.random(), Math.random(), Math.random());
        TreeBranchPartState branchPartState = factory.createRandomBranchPartState(branchPartEndPoint);
        assertNotNull(branchPartState.getBranchPart3DState());

        List<TreeLeafState> leaveStates = branchPartState.getLeaveStates();
        int nbLeaves = leaveStates.size();
        assertTrue(nbLeaves >= 2);
        assertTrue(nbLeaves <= 4);
        for (int index = 0; index < nbLeaves; index++) {
            TreeLeafState leafState = leaveStates.get(index);
            Point3dState leafAttachPoint = leafState.getLeaf3DState().getLeafAttachPoint();
            PointTestHelper.assertPointIsWithinBounds(leafAttachPoint.toPointValue(), new Point3d(0, 0, 0),
                    branchPartEndPoint);
        }

        Long id1 = branchPartState.getId();
        assertNotNull(id1);
        assertTrue(id1.longValue() > 0);
        assertTrue(branchPartState.getAge() >= 0);
        assertTrue(branchPartState.getAge() <= 100);
        assertTrue(branchPartState.getEnergy().compareTo(new BigDecimal(0)) >= 0);
        assertTrue(branchPartState.getEnergy().compareTo(new BigDecimal(100)) <= 0);
        assertTrue(branchPartState.getFreeEnergy().compareTo(new BigDecimal(0)) >= 0);
        assertTrue(branchPartState.getFreeEnergy().compareTo(new BigDecimal(50)) <= 0);

        branchPartState = factory.createRandomBranchPartState(branchPartEndPoint);
        Long id2 = branchPartState.getId();
        assertEquals(id1.longValue() + 1, id2.longValue());
    }

}
