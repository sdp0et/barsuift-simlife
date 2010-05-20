package barsuift.simLife.tree;

import java.math.BigDecimal;
import java.util.List;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import junit.framework.TestCase;
import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.helper.PointTestHelper;


public class TreeStateFactoryTest extends TestCase {

    private TreeStateFactory factory;

    protected void setUp() throws Exception {
        super.setUp();
        factory = new TreeStateFactory();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        factory = null;
    }

    public void testCreateRandomTreeState() {
        Point3d translationVector = new Point3d(Math.random(), Math.random(), Math.random());
        int nbBranches = Randomizer.randomBetween(40, 60);
        float height = Randomizer.randomBetween(8, 12);
        TreeState treeState = factory.createRandomTreeState(translationVector, nbBranches, height);
        List<TreeBranchState> branches = treeState.getBranches();
        assertEquals(nbBranches, branches.size());

        assertEquals(height, treeState.getHeight());
        TreeTrunkState trunkState = treeState.getTrunkState();
        assertEquals(height / 8, trunkState.getRadius());
        assertNotNull(treeState.getTree3DState());

        Long id1 = treeState.getId();
        assertNotNull(id1);
        assertTrue(id1.longValue() > 0);
        assertTrue(treeState.getAge() >= 0);
        assertTrue(treeState.getAge() <= 100);
        assertTrue(treeState.getEnergy().compareTo(new BigDecimal(0)) >= 0);
        assertTrue(treeState.getEnergy().compareTo(new BigDecimal(100)) <= 0);

        treeState = factory.createRandomTreeState(translationVector, nbBranches, height);
        Long id2 = treeState.getId();
        assertEquals(id1.longValue() + 1, id2.longValue());
    }

    public void testComputeBranchTranslationVector() {
        float treeRadius = 6;
        float treeHeight = 8;
        Vector3d translationVector = factory.computeBranchTranslationVector(treeRadius, treeHeight);
        PointTestHelper.assertPointIsWithinBounds(new Point3d(translationVector), new Point3d(-6, 8, -6), new Point3d(
                6, 8, 6));
        treeRadius = 0.2f;
        treeHeight = 15;
        translationVector = factory.computeBranchTranslationVector(treeRadius, treeHeight);
        PointTestHelper.assertPointIsWithinBounds(new Point3d(translationVector), new Point3d(-0.2, 15, -0.2),
                new Point3d(0.2, 15, 0.2));
    }

    public void testComputeBranchEndPoint() {
        double treeHeight = 12;
        boolean goingToPositiveX = true;
        boolean goingToPositiveZ = false;
        Point3d branchEndPoint = factory.computeBranchEndPoint(treeHeight, goingToPositiveX, goingToPositiveZ);
        PointTestHelper.assertPointIsWithinBounds(branchEndPoint, new Point3d(0, 0, 0), new Point3d(6, 12, -6));
        treeHeight = 2.4;
        goingToPositiveX = false;
        goingToPositiveZ = true;
        branchEndPoint = factory.computeBranchEndPoint(treeHeight, goingToPositiveX, goingToPositiveZ);
        PointTestHelper.assertPointIsWithinBounds(branchEndPoint, new Point3d(0, 0, 0), new Point3d(-1.2, 2.4, 1.2));
    }

}
