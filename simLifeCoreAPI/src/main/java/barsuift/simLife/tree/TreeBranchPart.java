package barsuift.simLife.tree;

import java.math.BigDecimal;
import java.util.List;
import java.util.Observer;

import barsuift.simLife.LivingPart;
import barsuift.simLife.j3d.tree.TreeBranchPart3D;

public interface TreeBranchPart extends LivingPart, Observer {

    public int getNbLeaves();

    public List<TreeLeaf> getLeaves();

    public TreeBranchPartState getState();

    public TreeBranchPart3D getBranchPart3D();

    /**
     * Return the energy of the branch part
     * 
     * @return the energy
     */
    public BigDecimal getEnergy();

    /**
     * Return the free energy in the branch part, and set its value to 0
     * 
     * @return the free energy
     */
    public BigDecimal collectFreeEnergy();

}
