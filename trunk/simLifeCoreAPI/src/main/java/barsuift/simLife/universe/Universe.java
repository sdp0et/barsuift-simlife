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
package barsuift.simLife.universe;

import java.util.List;

import barsuift.simLife.LivingPart;
import barsuift.simLife.Persistent;
import barsuift.simLife.environment.Environment;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.process.SynchronizerCore;
import barsuift.simLife.time.SimLifeDate;
import barsuift.simLife.tree.Tree;
import barsuift.simLife.tree.TreeLeaf;
import barsuift.simLife.universe.physic.Physics;


public interface Universe extends Persistent<UniverseState> {

    /**
     * Return an unmodifiable Set of all living parts
     * 
     * @return all the universe living parts
     */
    public List<LivingPart> getLivingParts();

    /**
     * Return an unmodifiable Set of trees
     * 
     * @return the trees
     */
    public List<Tree> getTrees();

    public void addTree(Tree tree);

    /**
     * Return an unmodifiable Set of fallen leaves
     * 
     * @return the fallen leaves
     */
    public List<TreeLeaf> getFallenLeaves();

    public void addFallenLeaf(TreeLeaf treeLeaf);

    public Environment getEnvironment();

    public Physics getPhysics();

    public Universe3D getUniverse3D();

    public SynchronizerCore getSynchronizer();

    public SimLifeDate getDate();

}
