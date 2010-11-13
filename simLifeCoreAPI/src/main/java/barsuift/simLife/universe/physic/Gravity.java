package barsuift.simLife.universe.physic;

import java.util.Set;

import barsuift.simLife.Persistent;
import barsuift.simLife.j3d.universe.physic.Gravity3D;
import barsuift.simLife.tree.TreeLeaf;


public interface Gravity extends Persistent<GravityState> {

    public Gravity3D getGravity3D();

    /**
     * Return an unmodifiable Set of falling leaves
     * 
     * @return the falling leaves
     */
    public Set<TreeLeaf> getFallingLeaves();

    public void addFallingLeaf(TreeLeaf treeLeaf);

}
