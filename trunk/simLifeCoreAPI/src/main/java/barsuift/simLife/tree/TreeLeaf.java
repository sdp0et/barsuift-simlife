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

import barsuift.simLife.LivingPart;
import barsuift.simLife.j3d.tree.TreeLeaf3D;
import barsuift.simLife.message.Publisher;

public interface TreeLeaf extends LivingPart, Publisher {

    public boolean isTooWeak();

    public TreeLeaf3D getTreeLeaf3D();

    /**
     * Return the efficiency of the leaf. This efficiency impacts the amount of solar energy which can be collected.
     * 
     * @return the efficiency
     */
    public BigDecimal getEfficiency();

    /**
     * Return the energy of the leaf
     * 
     * @return the energy
     */
    public BigDecimal getEnergy();

    /**
     * Return the free energy in the leaf, and set its value to 0
     * 
     * @return the free energy
     */
    public BigDecimal collectFreeEnergy();

    /**
     * Compute the new leaf energy. It is the old energy + the collected energy.
     * <code>collectedEnergy= sunLuminosity * leafEfficiency * energyDensity * leaf Area</code>
     * 
     * @return the collected energy
     */
    public void collectSolarEnergy();

}