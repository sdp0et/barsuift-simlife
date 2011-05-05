/**
 * barsuift-simlife is a life simulator program
 * 
 * Copyright (C) 2010 Cyrille GACHOT
 * 
 * This file is part of barsuift-simlife.
 * 
 * barsuift-simlife is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * barsuift-simlife is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with barsuift-simlife. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package barsuift.simLife.j3d.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Group;
import javax.media.j3d.LineArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;

import barsuift.simLife.j3d.AppearanceFactory;
import barsuift.simLife.j3d.Transform3DState;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.j3d.util.ColorConstants;
import barsuift.simLife.tree.TreeBranch;
import barsuift.simLife.tree.TreeLeaf;

public class BasicTreeBranch3D implements TreeBranch3D {

    private final TreeBranch3DState state;

    private final TreeBranch treeBranch;

    private final Transform3D transform;

    private final float length;

    private final float radius;

    private final BranchGroup bg;

    private final TransformGroup tg;

    /**
     * Creates a 3D tree branch, with data from the model one, and given state.
     * 
     * @param universe3D the 3D universe
     * @param state the branch 3D state
     * @param treeBranch tree branch data
     */
    public BasicTreeBranch3D(Universe3D universe3D, TreeBranch3DState state, TreeBranch treeBranch) {
        if (universe3D == null) {
            throw new IllegalArgumentException("Null universe 3D");
        }
        if (state == null) {
            throw new IllegalArgumentException("Null tree branch 3D state");
        }
        if (treeBranch == null) {
            throw new IllegalArgumentException("Null tree branch");
        }
        this.state = state;
        this.transform = state.getTransform().toTransform3D();
        this.length = state.getLength();
        this.radius = state.getRadius();
        this.treeBranch = treeBranch;

        this.tg = new TransformGroup(transform);
        tg.setCapability(Group.ALLOW_CHILDREN_WRITE);
        tg.setCapability(Group.ALLOW_CHILDREN_EXTEND);

        this.bg = new BranchGroup();
        bg.addChild(tg);

        createFullTreeBranch();
    }

    private void createFullTreeBranch() {
        addBranchShape();
        for (TreeLeaf treeLeaf : treeBranch.getLeaves()) {
            addLeaf(treeLeaf.getTreeLeaf3D());
        }
    }

    private void addBranchShape() {
        Shape3D branchShape = new Shape3D();
        LineArray branchLine = createBranchLine();
        Appearance branchAppearance = new Appearance();
        AppearanceFactory.setColorWithColoringAttributes(branchAppearance, ColorConstants.brown);
        branchShape.setGeometry(branchLine);
        branchShape.setAppearance(branchAppearance);
        tg.addChild(branchShape);
    }

    private LineArray createBranchLine() {
        LineArray branchLine = new LineArray(2, GeometryArray.COORDINATES);
        branchLine.setCoordinate(0, new Point3f(0, 0, 0));
        branchLine.setCoordinate(1, new Point3f(length, 0, 0));
        return branchLine;
    }

    public void addLeaf(TreeLeaf3D leaf) {
        tg.addChild(leaf.getBranchGroup());
    }

    @Override
    public float getLength() {
        return length;
    }

    @Override
    public float getRadius() {
        return radius;
    }

    public Transform3D getTransform() {
        return transform;
    }

    public void increaseOneLeafSize() {
        TreeLeaf3D leaf3D = getRandomLeafToIncrease();
        leaf3D.increaseSize();
    }

    /**
     * Returns one random leaf, with max odd to smallest leaf
     */
    protected TreeLeaf3D getRandomLeafToIncrease() {
        List<TreeLeaf3D> leaves3D = getLeaves();

        // get applicable leaves
        List<TreeLeaf3D> leaves3DToIncrease = new ArrayList<TreeLeaf3D>(leaves3D.size());
        for (TreeLeaf3D leaf3D : leaves3D) {
            if (!leaf3D.isMaxSizeReached()) {
                leaves3DToIncrease.add(leaf3D);
            }
        }
        if (leaves3DToIncrease.size() == 0) {
            return null;
        }
        if (leaves3DToIncrease.size() == 1) {
            return leaves3DToIncrease.get(0);
        }

        // compute areas
        Map<TreeLeaf3D, Float> areas = new HashMap<TreeLeaf3D, Float>(leaves3DToIncrease.size());
        for (TreeLeaf3D leaf3D : leaves3DToIncrease) {
            areas.put(leaf3D, leaf3D.getArea());
        }

        // compute area sum
        float sumArea = 0;
        for (Float area : areas.values()) {
            sumArea += area;
        }

        // compute diffArea sum
        float sumDiffArea = (leaves3DToIncrease.size() - 1) * sumArea;

        // compute ratios
        // thanks to the use of sumDiffArea, the sum of ratios is equals to 1 (100%)
        Map<TreeLeaf3D, Float> ratios = new HashMap<TreeLeaf3D, Float>(areas.size());
        for (Entry<TreeLeaf3D, Float> entry : areas.entrySet()) {
            ratios.put(entry.getKey(), (sumArea - entry.getValue()) / sumDiffArea);
        }

        // select one leaf
        float random = (float) Math.random();
        float previousMinBound = 0;
        for (Entry<TreeLeaf3D, Float> entry : ratios.entrySet()) {
            TreeLeaf3D leaf3D = entry.getKey();
            Float ratio = entry.getValue();
            if (random < (previousMinBound + ratio)) {
                return leaf3D;
            }
            previousMinBound += ratio;
        }

        // return last leaf
        return null;
    }

    @Override
    public TreeBranch3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setTransform(new Transform3DState(transform));
        state.setLength(length);
        state.setRadius(radius);
    }

    @Override
    public BranchGroup getBranchGroup() {
        return bg;
    }

    @Override
    public List<TreeLeaf3D> getLeaves() {
        List<TreeLeaf3D> result = new ArrayList<TreeLeaf3D>();
        Collection<TreeLeaf> leaves = treeBranch.getLeaves();
        for (TreeLeaf leaf : leaves) {
            result.add(leaf.getTreeLeaf3D());
        }
        return result;
    }

}
