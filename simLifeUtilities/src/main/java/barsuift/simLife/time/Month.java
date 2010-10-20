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
package barsuift.simLife.time;


public enum Month {

    /**
     * First month of the year
     */
    WIM(1, "Wim"),

    /**
     * Second month of the year
     */
    SPRIM(2, "Sprim"),

    /**
     * third month of the year
     */
    SUM(3, "Sum"),

    /**
     * Fourth and last month of the year
     */
    TOM(4, "Tom");

    private final int index;

    private final String name;

    private Month(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String geTName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
