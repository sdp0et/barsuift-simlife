package barsuift.simLife.tree;

import java.math.BigDecimal;
import java.util.List;

import barsuift.simLife.LivingPart;
import barsuift.simLife.j3d.tree.Tree3D;

public interface Tree extends LivingPart {

    public float getHeight();

    public int getNbBranches();

    public int getNbLeaves();

    public List<TreeBranch> getBranches();

    public TreeTrunk getTrunk();

    public Tree3D getTree3D();

    public TreeState getState();

    /**
     * Return the energy of the tree
     * 
     * @return the energy
     */
    public BigDecimal getEnergy();
}
