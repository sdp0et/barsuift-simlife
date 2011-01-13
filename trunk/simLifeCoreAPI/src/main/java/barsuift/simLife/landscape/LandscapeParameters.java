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

import barsuift.simLife.Parameters;


/**
 * The class holds the parameters required to create a landscape, especially for the {@link MidPointHeightMapGenerator}
 * class.
 * 
 * Those parameters are :
 * <ul>
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

    public static final float ROUGHNESS_DEFAULT = 0.5f;

    public static final float ROUGHNESS_MIN = 0f;

    public static final float ROUGHNESS_MAX = 1f;


    public static final float EROSION_DEFAULT = 0.5f;

    public static final float EROSION_MIN = 0f;

    public static final float EROSION_MAX = 1f;



    private float roughness;

    private float erosion;

    /**
     * Empty constructor.
     */
    public LandscapeParameters() {
        resetToDefaults();
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
        this.roughness = ROUGHNESS_DEFAULT;
        this.erosion = EROSION_DEFAULT;
    }

    @Override
    public void random() {
        // roughness between 0.25 and 0.75
        this.roughness = (float) (Math.random() + 0.5) / 2;
        // erosion between 0.25 and 0.75
        this.erosion = (float) (Math.random() + 0.5) / 2;
    }

    @Override
    public String toString() {
        return "LandscapeParameters [roughness=" + roughness + ", erosion=" + erosion + "]";
    }

}
