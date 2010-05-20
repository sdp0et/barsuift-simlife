package barsuift.simLife.universe;

import java.util.HashSet;
import java.util.Set;

import barsuift.simLife.LivingPart;
import barsuift.simLife.environment.Environment;
import barsuift.simLife.environment.MockEnvironment;
import barsuift.simLife.j3d.universe.MockUniverse3D;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.time.TimeCounter;
import barsuift.simLife.time.TimeCounterState;
import barsuift.simLife.tree.Tree;
import barsuift.simLife.tree.TreeLeaf;


public class MockUniverse implements Universe {

    private Set<LivingPart> livingParts = new HashSet<LivingPart>();

    private Set<Tree> trees = new HashSet<Tree>();

    private Set<TreeLeaf> fallenLeaves = new HashSet<TreeLeaf>();

    private Long id = new Long(1);

    private int age = 0;

    private int timeSpent = 0;

    private Environment environment = new MockEnvironment();

    private Universe3D universe3D = new MockUniverse3D();

    private UniverseState state = new UniverseState();

    private TimeCounter timeCounter = new TimeCounter(new TimeCounterState());

    @Override
    public Set<LivingPart> getLivingParts() {
        return livingParts;
    }

    public void addLivingPart(LivingPart livingPart) {
        livingParts.add(livingPart);
    }

    public void removeLivingPart(LivingPart livingPart) {
        livingParts.remove(livingPart);
    }

    @Override
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
    public void spendTime() {
        timeSpent++;
    }

    public int getNbTimeSpent() {
        return timeSpent;
    }

    public void resetNbTimeSpent() {
        timeSpent = 0;
    }

    @Override
    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Set<Tree> getTrees() {
        return trees;
    }

    @Override
    public void addTree(Tree tree) {
        trees.add(tree);
    }

    public void removeTree(Tree tree) {
        trees.remove(tree);
    }

    @Override
    public Set<TreeLeaf> getFallenLeaves() {
        return fallenLeaves;
    }

    public void addFallenLeaf(TreeLeaf treeLeaf) {
        fallenLeaves.add(treeLeaf);
    }

    public void removeFallenLeaf(TreeLeaf treeLeaf) {
        fallenLeaves.remove(treeLeaf);
    }

    @Override
    public Universe3D getUniverse3D() {
        return universe3D;
    }

    public void setUniverse3D(Universe3D universe3D) {
        this.universe3D = universe3D;
    }

    @Override
    public UniverseState getState() {
        return state;
    }

    public void setState(UniverseState state) {
        this.state = state;
    }

    @Override
    public TimeCounter getCounter() {
        return timeCounter;
    }

    public void setCounter(TimeCounter timeCounter) {
        this.timeCounter = timeCounter;
    }

}
