package barsuift.simLife.universe;

import java.util.Set;

import barsuift.simLife.LivingPart;
import barsuift.simLife.environment.Environment;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.time.TimeCounter;
import barsuift.simLife.tree.Tree;
import barsuift.simLife.tree.TreeLeaf;


public interface Universe extends LivingPart {

    /**
     * Return an unmodifiable Set of all living parts
     * 
     * @return all the universe living parts
     */
    public Set<LivingPart> getLivingParts();

    /**
     * Return an unmodifiable Set of trees
     * 
     * @return the trees
     */
    public Set<Tree> getTrees();

    public void addTree(Tree tree);

    /**
     * Return an unmodifiable Set of fallen leaves
     * 
     * @return the fallen leaves
     */
    public Set<TreeLeaf> getFallenLeaves();

    public void addFallenLeaf(TreeLeaf treeLeaf);

    public Environment getEnvironment();

    public TimeCounter getCounter();

    public Universe3D getUniverse3D();

    public UniverseState getState();

}
