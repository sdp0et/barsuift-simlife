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
package barsuift.simLife;

// TODO 011. create a TreeGrowth class which manage tree growth (create new leaves, create new branches, ...)
// TODO 013. remove the LivingPart#spendTime method
// TODO 015. remove the TimeMessenger class
public interface LivingPart extends Persistent<State> {

    /**
     * Make the living part spend some time.
     * <p>
     * Depending on the living part, the consequences of this method may differ widely.
     * </p>
     * 
     */
    public void spendTime();

    public long getCreationMillis();

}
