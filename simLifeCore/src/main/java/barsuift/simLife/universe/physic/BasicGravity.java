package barsuift.simLife.universe.physic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import barsuift.simLife.j3d.universe.physic.BasicGravity3D;
import barsuift.simLife.j3d.universe.physic.Gravity3D;
import barsuift.simLife.tree.BasicTreeLeaf;
import barsuift.simLife.tree.TreeLeaf;
import barsuift.simLife.tree.TreeLeafState;
import barsuift.simLife.universe.Universe;


public class BasicGravity implements Gravity {

    private final GravityState state;

    private final Gravity3D gravity3D;

    private final Set<TreeLeaf> fallingLeaves;

    public BasicGravity(GravityState state, Universe universe) {
        this.state = state;
        this.fallingLeaves = new HashSet<TreeLeaf>();
        Set<TreeLeafState> fallingLeafStates = state.getFallingLeaves();
        for (TreeLeafState fallingLeafState : fallingLeafStates) {
            fallingLeaves.add(new BasicTreeLeaf(universe, fallingLeafState));
        }
        this.gravity3D = new BasicGravity3D(state.getGravity3D(), universe.getUniverse3D());
    }

    @Override
    public Set<TreeLeaf> getFallingLeaves() {
        return Collections.unmodifiableSet(fallingLeaves);
    }

    // FIXME find a way to add the leaf as a fallen leaf and no more as a falling leaf
    // universe.addFallenLeaf(treeLeaf);
    @Override
    public void addFallingLeaf(TreeLeaf treeLeaf) {
        fallingLeaves.add(treeLeaf);
    }

    @Override
    public GravityState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        Set<TreeLeafState> fallingLeaveStates = new HashSet<TreeLeafState>();
        for (TreeLeaf leaf : fallingLeaves) {
            fallingLeaveStates.add((TreeLeafState) leaf.getState());
        }
        state.setFallingLeaves(fallingLeaveStates);
        gravity3D.synchronize();
    }

    @Override
    public Gravity3D getGravity3D() {
        return gravity3D;
    }

}
