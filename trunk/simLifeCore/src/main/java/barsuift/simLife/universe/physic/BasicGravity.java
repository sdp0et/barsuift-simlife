package barsuift.simLife.universe.physic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import barsuift.simLife.j3d.universe.physic.BasicGravity3D;
import barsuift.simLife.j3d.universe.physic.Gravity3D;
import barsuift.simLife.message.Publisher;
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
        this.gravity3D = new BasicGravity3D(state.getGravity3D(), universe.getUniverse3D());
        Set<TreeLeafState> fallingLeafStates = state.getFallingLeaves();
        for (TreeLeafState fallingLeafState : fallingLeafStates) {
            addFallingLeaf(new BasicTreeLeaf(universe, fallingLeafState));
        }
    }

    @Override
    public Set<TreeLeaf> getFallingLeaves() {
        return Collections.unmodifiableSet(fallingLeaves);
    }

    @Override
    public void addFallingLeaf(TreeLeaf treeLeaf) {
        treeLeaf.addSubscriber(this);
        fallingLeaves.add(treeLeaf);
        gravity3D.fall(treeLeaf.getTreeLeaf3D());
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        // TODO 001. 006. update method : transfer the leaf from Gravity to Universe
        // universe.addFallenLeaf(treeLeaf);
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
