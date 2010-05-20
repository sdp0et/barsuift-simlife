package barsuift.simLife.tree;

import java.math.BigDecimal;

import barsuift.simLife.IObservable;
import barsuift.simLife.LivingPart;
import barsuift.simLife.Percent;
import barsuift.simLife.j3d.tree.TreeLeaf3D;

public interface TreeLeaf extends LivingPart, IObservable {

    public boolean isTooWeak();

    public TreeLeaf3D getTreeLeaf3D();

    public TreeLeafState getState();

    /**
     * Return the efficiency of the leaf. This efficiency impacts the amount of solar energy which can be collected.
     * 
     * @return the efficiency
     */
    public Percent getEfficiency();

    /**
     * Return the energy of the leaf
     * 
     * @return the energy
     */
    public BigDecimal getEnergy();

    /**
     * Return the free energy in the leaf, and set its value to 0
     * 
     * @return the free energy
     */
    public BigDecimal collectFreeEnergy();

}