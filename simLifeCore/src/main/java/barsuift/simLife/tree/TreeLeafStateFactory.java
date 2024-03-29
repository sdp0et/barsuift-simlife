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

import barsuift.simLife.PercentHelper;
import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.tree.TreeLeaf3DState;
import barsuift.simLife.j3d.tree.TreeLeaf3DStateFactory;


public class TreeLeafStateFactory {

    /**
     * Create a random tree leaf state with following values :
     * <ul>
     * <li>efficiency between 90 and 100</li>
     * <li>energy between 0 and 100</li>
     * <li>freeEnergy between 0 and 50</li>
     * <li>creationMillis between 0 and 100 000</li>
     * <li>random 3D state</li>
     * </ul>
     */
    public TreeLeafState createRandomTreeLeafState() {
        BigDecimal efficiency = PercentHelper.getDecimalValue(Randomizer.randomBetween(90, 100));
        long creationMillis = Randomizer.randomBetween(0, 100) * 1000;
        BigDecimal energy = new BigDecimal(Randomizer.randomBetween(0, 100));
        BigDecimal freeEnergy = new BigDecimal(Randomizer.randomBetween(0, 50));
        TreeLeaf3DStateFactory leaf3DStateFactory = new TreeLeaf3DStateFactory();
        TreeLeaf3DState leaf3dState = leaf3DStateFactory.createRandomTreeLeaf3DState();
        return new TreeLeafState(creationMillis, energy, freeEnergy, efficiency, leaf3dState);
    }

    /**
     * Create a new tree leaf state with following values :
     * <ul>
     * <li>efficiency between 90 and 100</li>
     * <li>freeEnergy = 0</li>
     * <li>new 3D state</li>
     * </ul>
     */
    public TreeLeafState createNewTreeLeafState(BigDecimal energy, long creationMillis) {
        BigDecimal efficiency = PercentHelper.getDecimalValue(Randomizer.randomBetween(90, 100));
        BigDecimal freeEnergy = new BigDecimal(0);
        TreeLeaf3DStateFactory leaf3DStateFactory = new TreeLeaf3DStateFactory();
        TreeLeaf3DState leaf3dState = leaf3DStateFactory.createNewTreeLeaf3DState();
        return new TreeLeafState(creationMillis, energy, freeEnergy, efficiency, leaf3dState);
    }

}
