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

import barsuift.simLife.MathHelper;


/**
 * The class holds the parameters required for the {@link MidPointHeightMapGenerator} class.
 * 
 * Those parameters are :
 * <ul>
 * <li>size : The size of the map's width (width = length = size)</li>
 * <li>roughness : The roughness determines how chaotic the terrain will be.
 * <ul>
 * <li>0 means very smooth terrain</li>
 * <li>0.5 means standard terrain</li>
 * <li>1 means absolutely chaotic terrain</li>
 * </ul>
 * </li>
 * <li>erosion : The erosion factor is used to erode the terrain.
 * <ul>
 * <li>0 means no erosion, creating a sharp environment</li>
 * <li>0.5 is a smooth erosion, creating nice hills and valleys</li>
 * <li>1 means 100% erosion : the land is perfectly flat</li>
 * </ul>
 * It can be thought of as the age of the terrain. The older the terrain, the more eroded.</li>
 * <li>maximumHeight : The maximum height used to normalize terrain. After normalization, the terrain heights will be
 * between 0 and this value</li>
 * </ul>
 * 
 */
public class MidPointHeightMapParameters {

    private final int size;

    private final float roughness;

    private final float erosion;

    private final float maximumHeight;

    /**
     * Constructor.
     * 
     * For information about the parameters, see class comments.
     * 
     * @param size the size must be a positive, greater than 0, power of 2
     * @param roughness must be between 0 and 1
     * @param erosion must be between 0 and 1
     * @param maximumHeight must be positive
     * @throws IllegalArgumentException if one parameter is not valid
     */
    public MidPointHeightMapParameters(int size, float roughness, float erosion, float maximumHeight) {
        checkParameters(size, roughness, erosion, maximumHeight);

        this.roughness = roughness;
        this.size = size;
        this.maximumHeight = maximumHeight;
        this.erosion = erosion;
    }

    /**
     * Check the given parameters and throw {@link IllegalArgumentException} if one parameter is not correct
     * 
     * @param size the size must be a positive, greater than 0, power of 2
     * @param roughness must be between 0 and 1
     * @param erosion must be between 0 and 1
     * @param maximumHeight must be positive
     * @throws IllegalArgumentException if one parameter is not valid
     */
    private void checkParameters(int size, float roughness, float erosion, float maximumHeight) {
        if (!MathHelper.isPowerOfTwo(size)) {
            throw new IllegalArgumentException("Size must be (2^N) sized and positive");
        }

        if (roughness < 0) {
            throw new IllegalArgumentException("roughness must be greater than 0");
        }
        if (roughness > 1) {
            throw new IllegalArgumentException("roughness must be less than 1");
        }

        if (erosion < 0) {
            throw new IllegalArgumentException("erosion must be greater than 0");
        }
        if (erosion > 1) {
            throw new IllegalArgumentException("erosion must be less than 1");
        }

        if (maximumHeight < 0) {
            throw new IllegalArgumentException("maximumHeight must be greater than 0");
        }
    }

    public int getSize() {
        return size;
    }

    public float getRoughness() {
        return roughness;
    }

    public float getErosion() {
        return erosion;
    }

    public float getMaximumHeight() {
        return maximumHeight;
    }

    @Override
    public String toString() {
        return "MidPointHeightMapParameters [size=" + size + ", roughness=" + roughness + ", erosion=" + erosion
                + ", maximumHeight=" + maximumHeight + "]";
    }

}
