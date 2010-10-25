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
package barsuift.simLife.tree;

import java.math.BigDecimal;
import java.util.List;

import barsuift.simLife.LivingPart;
import barsuift.simLife.j3d.tree.Tree3D;

public interface Tree extends LivingPart {

    public float getHeight();

    public int getNbBranches();

    public int getNbLeaves();

    public List<TreeBranch> getBranches();

    public TreeTrunk getTrunk();

    public Tree3D getTree3D();

    /**
     * Return the energy of the tree
     * 
     * @return the energy
     */
    public BigDecimal getEnergy();
}