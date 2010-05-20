package barsuift.simLife.tree;

import java.math.BigDecimal;
import java.util.Observable;

import barsuift.simLife.Percent;
import barsuift.simLife.j3d.tree.MockTreeLeaf3D;
import barsuift.simLife.j3d.tree.TreeLeaf3D;

public class MockTreeLeaf extends Observable implements TreeLeaf {

    private Long id = new Long(1);

    private Percent efficiency = new Percent();

    private int age;

    private BigDecimal energy = new BigDecimal(0);

    private BigDecimal freeEnergy = new BigDecimal(0);

    private int nbTilesSpendTimeCalled;

    private boolean isTooWeak;

    private TreeLeaf3D treeLeaf3D = new MockTreeLeaf3D();

    private TreeLeafState state = new TreeLeafState();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Percent getEfficiency() {
        return efficiency;
    }

    @Override
    public void spendTime() {
        nbTilesSpendTimeCalled++;
    }

    public int getNbTilesSpendTimeCalled() {
        return nbTilesSpendTimeCalled;
    }

    public void resetNbTilesSpendTimeCalled() {
        this.nbTilesSpendTimeCalled = 0;
    }

    public void setEfficiency(Percent efficiency) {
        this.efficiency = efficiency;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public BigDecimal getEnergy() {
        return energy;
    }

    public void setEnergy(BigDecimal energy) {
        this.energy = energy;
    }

    @Override
    public BigDecimal collectFreeEnergy() {
        return freeEnergy;
    }

    public void setFreeEnergy(BigDecimal freeEnergy) {
        this.freeEnergy = freeEnergy;
    }

    public boolean isTooWeak() {
        return isTooWeak;
    }

    public void setTooWeak(boolean isTooWeak) {
        this.isTooWeak = isTooWeak;
    }

    @Override
    public TreeLeaf3D getTreeLeaf3D() {
        return treeLeaf3D;
    }

    @Override
    public TreeLeafState getState() {
        return state;
    }

    public void setState(TreeLeafState state) {
        this.state = state;
    }

}
