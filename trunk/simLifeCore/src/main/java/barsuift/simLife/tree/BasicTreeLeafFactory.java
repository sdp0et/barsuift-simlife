package barsuift.simLife.tree;

import java.math.BigDecimal;

import javax.vecmath.Point3d;

import barsuift.simLife.universe.Universe;

public class BasicTreeLeafFactory {

    private final Universe universe;

    public BasicTreeLeafFactory(Universe universe) {
        this.universe = universe;
    }

    public TreeLeaf createRandom(Point3d leafAttachPoint) {
        TreeLeafStateFactory treeLeafStateFactory = new TreeLeafStateFactory();
        TreeLeafState treeLeafState = treeLeafStateFactory.createRandomTreeLeafState(leafAttachPoint);
        TreeLeaf treeLeaf = new BasicTreeLeaf(universe, treeLeafState);
        return treeLeaf;
    }

    public TreeLeaf createNew(Point3d leafAttachPoint, BigDecimal energy) {
        TreeLeafStateFactory treeLeafStateFactory = new TreeLeafStateFactory();
        TreeLeafState treeLeafState = treeLeafStateFactory.createNewTreeLeafState(leafAttachPoint, energy);
        TreeLeaf treeLeaf = new BasicTreeLeaf(universe, treeLeafState);
        return treeLeaf;
    }

}
