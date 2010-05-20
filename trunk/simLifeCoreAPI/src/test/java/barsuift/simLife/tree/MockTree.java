package barsuift.simLife.tree;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import barsuift.simLife.j3d.tree.MockTree3D;
import barsuift.simLife.j3d.tree.Tree3D;

public class MockTree implements Tree {

    private Long id = new Long(1);

    private int age = 0;

    private BigDecimal energy = new BigDecimal(0);

    private List<TreeBranch> branches = new ArrayList<TreeBranch>();

    private float height = 0;

    private int nbBranches = 0;

    private int nbLeaves = 0;

    private int spendTimeCalled = 0;

    private TreeState state = new TreeState();

    private Tree3D tree3D = new MockTree3D();

    private TreeTrunk trunk = new MockTreeTrunk();

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

    public void removeBranch(TreeBranch branch) {
        this.branches.remove(branch);
    }

    public void addBranch(TreeBranch branch) {
        this.branches.add(branch);
    }

    @Override
    public List<TreeBranch> getBranches() {
        return branches;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public float getHeight() {
        return height;
    }

    public void setNbBranches(int nbBranches) {
        this.nbBranches = nbBranches;
    }

    @Override
    public int getNbBranches() {
        return nbBranches;
    }

    public void setNbLeaves(int nbLeaves) {
        this.nbLeaves = nbLeaves;
    }

    @Override
    public int getNbLeaves() {
        return nbLeaves;
    }

    @Override
    public BigDecimal getEnergy() {
        return energy;
    }

    public void setEnergy(BigDecimal energy) {
        this.energy = energy;
    }

    public int howManyTimesSpendTimeCalled() {
        return spendTimeCalled;
    }

    public void resetSpendTimeCalled() {
        this.spendTimeCalled = 0;
    }

    @Override
    public void spendTime() {
        spendTimeCalled++;
    }

    @Override
    public TreeState getState() {
        return state;
    }

    public void setState(TreeState state) {
        this.state = state;
    }

    @Override
    public Tree3D getTree3D() {
        return tree3D;
    }

    public void setTree3D(Tree3D tree3D) {
        this.tree3D = tree3D;
    }

    @Override
    public TreeTrunk getTrunk() {
        return trunk;
    }

    public void setTreeTrunk(TreeTrunk trunk) {
        this.trunk = trunk;
    }

}
