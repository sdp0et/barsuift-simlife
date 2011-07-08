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
package barsuift.simLife.landscape;

import barsuift.simLife.MathHelper;
import barsuift.simLife.Parameters;
import barsuift.simLife.Randomizer;


/**
 * The class holds the parameters required to create a landscape, especially for the {@link MidPointHeightMapGenerator}
 * class.
 * 
 * Those parameters are :
 * <ul>
 * <li>size : The size of the map's width (width = length = size)</li>
 * <li>maximumHeight : The maximum height used to normalize landscape. After normalization, the landscape heights will
 * be between 0 and this value</li>
 * <li>roughness : The roughness determines how chaotic the landscape will be.
 * <ul>
 * <li>0 means very smooth landscape</li>
 * <li>0.5 means standard landscape</li>
 * <li>1 means absolutely chaotic landscape</li>
 * </ul>
 * </li>
 * <li>erosion : The erosion factor is used to erode the landscape.
 * <ul>
 * <li>0 means no erosion, creating a sharp environment</li>
 * <li>0.5 is a smooth erosion, creating nice hills and valleys</li>
 * <li>1 means 100% erosion : the land is perfectly flat</li>
 * </ul>
 * It can be thought of as the age of the landscape. The older the landscape, the more eroded.</li>
 * </ul>
 * 
 */
public class LandscapeParameters implements Parameters {

    public static final int SIZE_DEFAULT_EXPONENT = 7;

    public static final int SIZE_DEFAULT = 1 << SIZE_DEFAULT_EXPONENT;

    public static final int SIZE_MIN_EXPONENT = 5;

    public static final int SIZE_MIN = 1 << SIZE_MIN_EXPONENT;

    public static final int SIZE_MAX_EXPONENT = 9;

    public static final int SIZE_MAX = 1 << SIZE_MAX_EXPONENT;


    public static final float MAX_HEIGHT_DEFAULT = 20;

    public static final int MAX_HEIGHT_MIN = 0;

    public static final int MAX_HEIGHT_MAX = 50;


    public static final float ROUGHNESS_DEFAULT = 0.5f;

    public static final float ROUGHNESS_MIN = 0f;

    public static final float ROUGHNESS_MAX = 1f;


    public static final float EROSION_DEFAULT = 0.5f;

    public static final float EROSION_MIN = 0f;

    public static final float EROSION_MAX = 1f;



    private int size;

    private float maximumHeight;

    private float roughness;

    private float erosion;

    /**
     * Empty constructor.
     */
    public LandscapeParameters() {
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
        if (size < SIZE_MIN) {
            throw new IllegalArgumentException("Size must be greater than " + SIZE_MIN);
        }
        if (size > SIZE_MAX) {
            throw new IllegalArgumentException("Size must be less than " + SIZE_MAX);
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

    public float getRoughness() {
        return roughness;
    }

    /**
     * 
     * @param roughness must be between {@link #ROUGHNESS_MIN} and {@link #ROUGHNESS_MAX}
     * @throws IllegalArgumentException if the roughness is not valid
     */
    public void setRoughness(float roughness) {
        if (roughness < ROUGHNESS_MIN) {
            throw new IllegalArgumentException("roughness must be greater than " + ROUGHNESS_MIN);
        }
        if (roughness > ROUGHNESS_MAX) {
            throw new IllegalArgumentException("roughness must be less than " + ROUGHNESS_MAX);
        }
        this.roughness = roughness;
    }

    public float getErosion() {
        return erosion;
    }

    /**
     * 
     * @param erosion must be between {@link #EROSION_MIN} and {@link #EROSION_MAX}
     * @throws IllegalArgumentException if the erosion is not valid
     */
    public void setErosion(float erosion) {
        if (erosion < EROSION_MIN) {
            throw new IllegalArgumentException("erosion must be greater than " + EROSION_MIN);
        }
        if (erosion > EROSION_MAX) {
            throw new IllegalArgumentException("erosion must be less than " + EROSION_MAX);
        }
        this.erosion = erosion;
    }

    @Override
    public void resetToDefaults() {
        this.size = SIZE_DEFAULT;
        this.maximumHeight = MAX_HEIGHT_DEFAULT;
        this.roughness = ROUGHNESS_DEFAULT;
        this.erosion = EROSION_DEFAULT;
    }

    @Override
    public void random() {
        this.size = 1 << Randomizer.randomBetween(SIZE_MIN_EXPONENT, SIZE_MAX_EXPONENT);
        this.maximumHeight = Randomizer.randomBetween(MAX_HEIGHT_MIN, MAX_HEIGHT_MAX);
        // roughness between 0.25 and 0.75
        this.roughness = (float) (Math.random() + 0.5) / 2;
        // erosion between 0.25 and 0.75
        this.erosion = (float) (Math.random() + 0.5) / 2;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(erosion);
        result = prime * result + Float.floatToIntBits(maximumHeight);
        result = prime * result + Float.floatToIntBits(roughness);
        result = prime * result + size;
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
        LandscapeParameters other = (LandscapeParameters) obj;
        if (Float.floatToIntBits(erosion) != Float.floatToIntBits(other.erosion))
            return false;
        if (Float.floatToIntBits(maximumHeight) != Float.floatToIntBits(other.maximumHeight))
            return false;
        if (Float.floatToIntBits(roughness) != Float.floatToIntBits(other.roughness))
            return false;
        if (size != other.size)
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "LandscapeParameters [size=" + size + ", maximumHeight=" + maximumHeight + ", roughness=" + roughness
                + ", erosion=" + erosion + "]";
    }

}
