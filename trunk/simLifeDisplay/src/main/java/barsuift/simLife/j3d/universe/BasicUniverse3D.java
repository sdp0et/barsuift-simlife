package barsuift.simLife.j3d.universe;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import barsuift.simLife.j3d.Axis;
import barsuift.simLife.j3d.tree.Tree3D;
import barsuift.simLife.j3d.tree.TreeLeaf3D;
import barsuift.simLife.j3d.universe.environment.BasicLights;
import barsuift.simLife.j3d.universe.environment.Lights;
import barsuift.simLife.j3d.universe.physic.BasicPhysics;
import barsuift.simLife.j3d.universe.physic.Physics;
import barsuift.simLife.j3d.util.TransformerHelper;
import barsuift.simLife.tree.Tree;
import barsuift.simLife.tree.TreeLeaf;
import barsuift.simLife.universe.Universe;


public class BasicUniverse3D implements Universe3D {

    private BranchGroup root;

    private Set<Node> elements3D;

    private Physics physics;

    private Lights lights;

    public BasicUniverse3D() {
        root = new BranchGroup();
        root.setCapability(Group.ALLOW_CHILDREN_EXTEND);

        elements3D = new HashSet<Node>();
        physics = new BasicPhysics(this);
    }

    public void initFromUniverse(Universe universe) {
        lights = new BasicLights(universe.getEnvironment().getSun().getSun3D());
        addElement3D(lights.getLightsGroup());

        for (Tree tree : universe.getTrees()) {
            addTree(tree);
        }

        for (TreeLeaf treeLeaf : universe.getFallenLeaves()) {
            addFallenLeaf(treeLeaf);
        }
    }

    private void addTree(Tree tree) {
        Tree3D tree3D = tree.getTree3D();
        Point3d treeOriginPoint = tree3D.getState().getTranslationVector().toPointValue();
        Transform3D translation = TransformerHelper.getTranslationTransform3D(new Vector3d(treeOriginPoint));
        TransformGroup transformGroup = new TransformGroup(translation);

        BranchGroup treeBranchGroup = new BranchGroup();
        treeBranchGroup.addChild(transformGroup);
        transformGroup.addChild(tree3D.getBranchGroup());

        addElement3D(treeBranchGroup);
    }

    private void addFallenLeaf(TreeLeaf treeLeaf) {
        TreeLeaf3D treeLeaf3D = treeLeaf.getTreeLeaf3D();
        Point3d treeLeafOriginPoint = treeLeaf3D.getState().getLeafAttachPoint().toPointValue();
        double treeLeafRotation = treeLeaf3D.getState().getRotation();
        Transform3D translation = TransformerHelper.getTranslationTransform3D(new Vector3d(treeLeafOriginPoint));
        Transform3D rotation = TransformerHelper.getRotationTransform3D(treeLeafRotation, Axis.Y);
        translation.mul(rotation);
        TransformGroup transformGroup = new TransformGroup(translation);

        BranchGroup treeLeafBranchGroup = new BranchGroup();
        treeLeafBranchGroup.addChild(transformGroup);
        transformGroup.addChild(treeLeaf3D.getBranchGroup());

        addElement3D(treeLeafBranchGroup);
    }

    public void addElement3D(Node element3D) {
        elements3D.add(element3D);
        root.addChild(element3D);
    }

    public Set<Node> getElements3D() {
        return Collections.unmodifiableSet(elements3D);
    }

    @Override
    public Physics getPhysics() {
        return physics;
    }

    @Override
    public Lights getLights() {
        return lights;
    }

    @Override
    public BranchGroup getUniverseRoot() {
        return root;
    }

}