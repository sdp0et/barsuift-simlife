package barsuift.simLife.tree;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import barsuift.simLife.MockObserver;
import barsuift.simLife.j3d.tree.MockTreeBranch3D;
import barsuift.simLife.j3d.tree.TreeBranch3D;

public class MockTreeBranch extends MockObserver implements TreeBranch {

    private Long id = new Long(1);

    private int age = 0;

    private BigDecimal energy = new BigDecimal(0);

    private BigDecimal freeEnergy = new BigDecimal(0);

    private int spendTimeCalled = 0;

    private int nbLeaves = 0;

    private TreeBranch3D branch3D = new MockTreeBranch3D();

    private int nbParts = 0;

    private List<TreeBranchPart> parts = new ArrayList<TreeBranchPart>();

    private TreeBranchState state = new TreeBranchState();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int getNbLeaves() {
        return nbLeaves;
    }

    public void setNbLeaves(int nbLeaves) {
        this.nbLeaves = nbLeaves;
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

    @Override
    public void spendTime() {
        spendTimeCalled++;
    }

    public int howManyTimesSpendTimeCalled() {
        return spendTimeCalled;
    }

    public void resetSpendTimeCalled() {
        this.spendTimeCalled = 0;
    }

    @Override
    public TreeBranch3D getBranch3D() {
        return branch3D;
    }

    public void set(TreeBranch3D branch3D) {
        this.branch3D = branch3D;
    }

    @Override
    public int getNbParts() {
        return nbParts;
    }

    public void setNbParts(int nbParts) {
        this.nbParts = nbParts;
    }

    @Override
    public List<TreeBranchPart> getParts() {
        return parts;
    }

    public void addPart(TreeBranchPart part) {
        parts.add(part);
    }

    public void removePart(TreeBranchPart part) {
        parts.remove(part);
    }

    @Override
    public TreeBranchState getState() {
        return state;
    }

    public void setState(TreeBranchState state) {
        this.state = state;
    }
}