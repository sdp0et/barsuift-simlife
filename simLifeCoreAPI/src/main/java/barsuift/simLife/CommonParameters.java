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

    public static final int SIZE_DEFAULT_EXPONENT = 7;

    public static final int SIZE_DEFAULT = 1 << SIZE_DEFAULT_EXPONENT;

    public static final int SIZE_MIN_EXPONENT = 5;

    public static final int SIZE_MIN = 1 << SIZE_MIN_EXPONENT;

    public static final int SIZE_MAX_EXPONENT = 9;

    public static final int SIZE_MAX = 1 << SIZE_MAX_EXPONENT;

    private int size;

    /**
     * Empty constructor.
     */
    public CommonParameters() {
        resetToDefaults();
    }

    public int getSize() {
        return size;
    }

    /**
     * 
     * @param size the size must be a positive, greater than 0, power of 2
     * @throws IllegalArgumentException if the size is not valid
     */
    public void setSize(int size) {
        if (!MathHelper.isPowerOfTwo(size)) {
            throw new IllegalArgumentException("Size must be (2^N) sized and positive");
        }
        this.size = size;
    }

    @Override
    public void resetToDefaults() {
        this.size = SIZE_DEFAULT;
    }

    @Override
    public void random() {
        // size between 32 and 512
        this.size = 1 << Randomizer.randomBetween(SIZE_MIN_EXPONENT, SIZE_MAX_EXPONENT);
    }

    @Override
    public String toString() {
        return "CommonParameters [size=" + size + "]";
    }

}
