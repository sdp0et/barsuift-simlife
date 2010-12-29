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
package barsuift.simLife.j3d.terrain;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;

@XmlRootElement
public class Landscape3DState implements State {

    private int size;

    private float[] coordinates;

    private int[] coordinatesIndices;

    private int[] stripCounts;

    public Landscape3DState() {
        this.size = 0;
        this.coordinates = new float[0];
        this.coordinatesIndices = new int[0];
        this.stripCounts = new int[0];
    }

    public Landscape3DState(int size, float[] coordinates, int[] coordinatesIndices, int[] stripCounts) {
        this.size = size;
        this.coordinates = coordinates;
        this.coordinatesIndices = coordinatesIndices;
        this.stripCounts = stripCounts;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public float[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(float[] coordinates) {
        this.coordinates = coordinates;
    }

    public int[] getCoordinatesIndices() {
        return coordinatesIndices;
    }

    public void setCoordinatesIndices(int[] coordinatesIndices) {
        this.coordinatesIndices = coordinatesIndices;
    }

    public int[] getStripCounts() {
        return stripCounts;
    }

    public void setStripCounts(int[] stripCounts) {
        this.stripCounts = stripCounts;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(coordinates);
        result = prime * result + Arrays.hashCode(coordinatesIndices);
        result = prime * result + size;
        result = prime * result + Arrays.hashCode(stripCounts);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Landscape3DState other = (Landscape3DState) obj;
        if (!Arrays.equals(coordinates, other.coordinates))
            return false;
        if (!Arrays.equals(coordinatesIndices, other.coordinatesIndices))
            return false;
        if (size != other.size)
            return false;
        if (!Arrays.equals(stripCounts, other.stripCounts))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Landscape3DState [size=" + size + ", coordinates=" + Arrays.toString(coordinates)
                + ", coordinatesIndices=" + Arrays.toString(coordinatesIndices) + ", stripCounts="
                + Arrays.toString(stripCounts) + "]";
    }

}
