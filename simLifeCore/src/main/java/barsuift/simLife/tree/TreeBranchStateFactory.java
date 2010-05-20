package barsuift.simLife.tree;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.tree.TreeBranch3DState;
import barsuift.simLife.j3d.tree.TreeBranch3DStateFactory;
import barsuift.simLife.j3d.util.BarycentreHelper;
import barsuift.simLife.j3d.util.PointHelper;

public class TreeBranchStateFactory {

    private static long BRANCH_COUNT = 1;

    public TreeBranchState createRandomBranchState(Vector3d translationVector, Point3d branchEndPoint) {
        int age = Randomizer.randomBetween(0, 100);
        BigDecimal energy = new BigDecimal(Randomizer.randomBetween(0, 100));
        BigDecimal freeEnergy = new BigDecimal(Randomizer.randomBetween(0, 50));
        TreeBranchPartStateFactory treeBranchPartStateFactory = new TreeBranchPartStateFactory();
        List<TreeBranchPartState> treeBranchPartStates = new ArrayList<TreeBranchPartState>();
        // TODO 080. the number of parts should be related to the length of the branch
        // TODO 050. the parts should be removed entirely and branches should be added to branches
        int nbParts = 3;
        for (int i = 0; i < nbParts; i++) {
            Point3d branchPartEndPoint = computeBranchPartEndPoint(branchEndPoint, nbParts);
            treeBranchPartStates.add(treeBranchPartStateFactory.createRandomBranchPartState(branchPartEndPoint));
        }

        TreeBranch3DStateFactory branch3DStateFactory = new TreeBranch3DStateFactory();
        TreeBranch3DState branch3DState = branch3DStateFactory.createRandomTreeBranch3DState(translationVector);

        return new TreeBranchState(BRANCH_COUNT++, age, energy, freeEnergy, treeBranchPartStates, branch3DState);
    }

    protected Point3d computeBranchPartEndPoint(Point3d branchEndPoint, int nbParts) {
        Point3d startPoint = new Point3d(0, 0, 0);
        double maxDistance = startPoint.distance(branchEndPoint);
        double averagePartLength = maxDistance / nbParts;
        Point3d partEndPoint = BarycentreHelper.getBarycentre(new Point3d(0, 0, 0), branchEndPoint, (Randomizer
                .random2() + 1)
                * averagePartLength);
        partEndPoint = PointHelper.shiftPoint(partEndPoint, maxDistance / 10);
        return partEndPoint;
    }

}
