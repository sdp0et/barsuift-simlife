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




/**
 * The class holds the common parameters required to create a universe.
 * 
 * Those parameters are :
 * <ul>
 * <li>size : The size of the map's width (width = length = size)</li>
 * </ul>
 * 
 */
public class CommonParameters implements Parameters {

    private final int size;

    /**
     * Constructor.
     * 
     * For information about the parameters, see class comments.
     * 
     * @param size the size must be a positive, greater than 0, power of 2
     * @throws IllegalArgumentException if one parameter is not valid
     */
    public CommonParameters(int size) {
        checkParameters(size);
        this.size = size;
    }

    /**
     * Check the given parameters and throw {@link IllegalArgumentException} if one parameter is not correct
     * 
     * @param size the size must be a positive, greater than 0, power of 2
     * @throws IllegalArgumentException if one parameter is not valid
     */
    private void checkParameters(int size) {
        if (!MathHelper.isPowerOfTwo(size)) {
            throw new IllegalArgumentException("Size must be (2^N) sized and positive");
        }
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "CommonParameters [size=" + size + "]";
    }

}
