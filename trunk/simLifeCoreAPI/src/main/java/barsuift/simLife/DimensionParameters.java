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
 * The class holds the dimension parameters required to create a universe.
 * 
 * Those parameters are :
 * <ul>
 * <li>size : The size of the map's width (width = length = size)</li>
 * <li>maximumHeight : The maximum height used to normalize landscape. After normalization, the landscape heights will
 * be between 0 and this value</li>
 * <li>latitude : the map latitude on the planet.</li>
 * </ul>
 * 
 */
public class DimensionParameters implements Parameters {

    public static final int SIZE_DEFAULT_EXPONENT = 7;

    public static final int SIZE_DEFAULT = 1 << SIZE_DEFAULT_EXPONENT;

    public static final int SIZE_MIN_EXPONENT = 5;

    public static final int SIZE_MIN = 1 << SIZE_MIN_EXPONENT;

    public static final int SIZE_MAX_EXPONENT = 9;

    public static final int SIZE_MAX = 1 << SIZE_MAX_EXPONENT;


    public static final float MAX_HEIGHT_DEFAULT = 20;

    public static final int MAX_HEIGHT_MIN = 0;

    public static final int MAX_HEIGHT_MAX = 50;


    public static final float LATITUDE_DEFAULT = 45;

    public static final int LATITUDE_MIN = 0;

    public static final int LATITUDE_MAX = 90;



    private int size;

    private float maximumHeight;

    private float latitude;

    /**
     * Empty constructor.
     */
    public DimensionParameters() {
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

    public float getMaximumHeight() {
        return maximumHeight;
    }

    /**
     * 
     * @param maximumHeight must be between {@link #MAX_HEIGHT_MIN} and {@link #MAX_HEIGHT_MAX}
     * @throws IllegalArgumentException if the maximum height is not valid
     */
    public void setMaximumHeight(float maximumHeight) {
        if (maximumHeight < MAX_HEIGHT_MIN) {
            throw new IllegalArgumentException("maximumHeight must be greater than " + MAX_HEIGHT_MIN);
        }
        if (maximumHeight > MAX_HEIGHT_MAX) {
            throw new IllegalArgumentException("maximumHeight must be less than " + MAX_HEIGHT_MAX);
        }
        this.maximumHeight = maximumHeight;
    }

    public float getLatitude() {
        return latitude;
    }

    /**
     * 
     * @param latitude the latitude must be between {@link #LATITUDE_MIN} and {@link #LATITUDE_MAX}
     * @throws IllegalArgumentException if the latitude is not valid
     */
    public void setLatitude(float latitude) {
        if (latitude > LATITUDE_MAX || latitude < LATITUDE_MIN) {
            throw new IllegalArgumentException("latitude must be comprised between " + LATITUDE_MIN + " and "
                    + LATITUDE_MAX);
        }
        this.latitude = latitude;
    }

    @Override
    public void resetToDefaults() {
        this.size = SIZE_DEFAULT;
        this.maximumHeight = MAX_HEIGHT_DEFAULT;
        this.latitude = LATITUDE_DEFAULT;
    }

    @Override
    public void random() {
        // size between 32 and 512
        this.size = 1 << Randomizer.randomBetween(SIZE_MIN_EXPONENT, SIZE_MAX_EXPONENT);
        // max height between 10 and 50
        this.maximumHeight = Randomizer.randomBetween(10, MAX_HEIGHT_MAX);
        this.latitude = Randomizer.randomBetween(LATITUDE_MIN, LATITUDE_MAX);
    }

    @Override
    public String toString() {
        return "DimensionParameters [size=" + size + ", maximumHeight=" + maximumHeight + ", latitude=" + latitude
                + "]";
    }

}
