package barsuift.simLife.tree;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3d;

import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.Point3dState;
import barsuift.simLife.j3d.tree.TreeBranchPart3DState;
import barsuift.simLife.j3d.tree.TreeBranchPart3DStateFactory;
import barsuift.simLife.j3d.util.BarycentreHelper;
import barsuift.simLife.j3d.util.DistanceHelper;

public class TreeBranchPartStateFactory {

    private static long BRANCH_PART_COUNT = 1;

    public TreeBranchPartState createRandomBranchPartState(Point3d branchPartEndPoint) {
        int age = Randomizer.randomBetween(0, 100);
        BigDecimal energy = new BigDecimal(Randomizer.randomBetween(0, 100));
        BigDecimal freeEnergy = new BigDecimal(Randomizer.randomBetween(0, 50));
        TreeLeafStateFactory leafStateFactory = new TreeLeafStateFactory();
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>();
        // TODO 080. the number of leaves should be related to the length of the branch
        int nbLeaves = Randomizer.randomBetween(2, 4);
        double maxDistance = DistanceHelper.distanceFromOrigin(branchPartEndPoint);
        double shift = maxDistance / nbLeaves;
        for (int index = 0; index < nbLeaves; index++) {
            Point3d leafAttachPoint = BarycentreHelper.getBarycentre(new Point3d(0, 0, 0), branchPartEndPoint,
                    (index + Randomizer.random2()) * shift);
            leaveStates.add(leafStateFactory.createRandomTreeLeafState(leafAttachPoint));
        }

        TreeBranchPart3DStateFactory branchPart3DStateFactory = new TreeBranchPart3DStateFactory();
        TreeBranchPart3DState branch3DState = branchPart3DStateFactory
                .createRandomTreeBranchPart3DState(new Point3dState(branchPartEndPoint));

        return new TreeBranchPartState(BRANCH_PART_COUNT++, age, energy, freeEnergy, leaveStates, branch3DState);
    }

}
