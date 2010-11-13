package barsuift.simLife.universe.physic;

import java.util.HashSet;
import java.util.Set;

import barsuift.simLife.j3d.universe.physic.Gravity3D;
import barsuift.simLife.j3d.universe.physic.MockGravity3D;
import barsuift.simLife.tree.TreeLeaf;


public class MockGravity implements Gravity {

    private GravityState state;

    private int synchronizedCalled;

    private Gravity3D gravity3D;

    private Set<TreeLeaf> fallingLeaves;

    public MockGravity() {
        reset();
    }

    public void reset() {
        this.state = new GravityState();
        this.synchronizedCalled = 0;
        this.gravity3D = new MockGravity3D();
        this.fallingLeaves = new HashSet<TreeLeaf>();
    }

    @Override
    public GravityState getState() {
        return state;
    }

    public void setState(GravityState state) {
        this.state = state;
    }

    @Override
    public void synchronize() {
        this.synchronizedCalled++;
    }

    public int getNbSynchronize() {
        return synchronizedCalled;
    }

    @Override
    public Gravity3D getGravity3D() {
        return gravity3D;
    }

    public void setGravity3D(Gravity3D gravity3D) {
        this.gravity3D = gravity3D;
    }

    @Override
    public Set<TreeLeaf> getFallingLeaves() {
        return fallingLeaves;
    }

    @Override
    public void addFallingLeaf(TreeLeaf treeLeaf) {
        fallingLeaves.add(treeLeaf);
    }

}
