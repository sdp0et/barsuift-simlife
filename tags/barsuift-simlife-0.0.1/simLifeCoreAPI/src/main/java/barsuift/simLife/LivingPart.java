/**
 * barsuift-simlife is a life simulator programm
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

public interface LivingPart {

    /**
     * Make the living part spend some time.
     * <p>
     * Depending on the living part, the consequences of this method may differ widely.
     * </p>
     * <p>
     * Usually, it means :
     * <ul>
     * <li>the living part is older than before</li>
     * <li>the living part gains 1 "action point", allowing it to do some stuff</li>
     * </ul>
     * </p>
     * 
     */
    public void spendTime();

    public Long getId();

    public int getAge();

}
