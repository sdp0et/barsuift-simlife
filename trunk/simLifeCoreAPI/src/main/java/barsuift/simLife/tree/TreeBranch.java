package barsuift.simLife.tree;

import java.math.BigDecimal;
import java.util.List;

import barsuift.simLife.LivingPart;
import barsuift.simLife.j3d.tree.TreeBranch3D;

public interface TreeBranch extends LivingPart {

    public int getNbLeaves();

    public List<TreeBranchPart> getParts();

    public int getNbParts();

    public TreeBranchState getState();

    public TreeBranch3D getBranch3D();

    /**
     * Return the energy of the branch
     * 
     * @return the energy
     */
    public BigDecimal getEnergy();

    /**
     * Return the free energy in the branch, and set its value to 0
     * 
     * @return the free energy
     */
    public BigDecimal collectFreeEnergy();

}
