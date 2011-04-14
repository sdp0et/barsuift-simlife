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

/**
 * A photosynthetic element can collect solar energy. This collected energy is then divided in two parts : energy for
 * the element itself, and free energy that can be collected by another element, typically, the one containing the
 * photosynthetic element.
 * 
 */
public interface Photosynthetic {

    /**
     * Return the free energy, and reset its value to 0. Thus, the free energy is entirely collected, and be be
     * collected only once.
     * 
     * @return the free energy
     */
    public BigDecimal collectFreeEnergy();

    /**
     * Collect solar energy.
     * <p>
     * If this instance contains other photosynthetic elements, it calls the {@code collectSolarEnergy()} method of
     * these elements, immediately followed by the {@code collectFreeEnergy()} method.
     * </p>
     * 
     * @return the collected energy
     */
    public void collectSolarEnergy();

}
