/**
 * barsuift-simlife is a life simulator program
 * 
 * Copyright (C) 2010 Cyrille GACHOT
 * 
 * This file is part of barsuift-simlife. It was originally part of Ardor3D, and published according its LICENCE which
 * can be found at <http://www.ardor3d.com/LICENSE>
 * 
 * This file is a modified version of the original one, from Ardor3D.
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

import java.util.Random;
import java.util.logging.Logger;

import barsuift.simLife.MathHelper;

// TODO 001. introduce a parameter object
public class MidPointHeightMapGenerator {

    private static final Logger logger = Logger.getLogger(MidPointHeightMapGenerator.class.getName());

    /**
     * Roughness determines how chaotic the terrain will be.
     * <ul>
     * <li>0 means very smooth terrain</li>
     * <li>0.5 means standard terrain</li>
     * <li>1 means absolutely chaotic terrain</li>
     * </ul>
     */
    private final float roughness;

    /** The size of the height map's width. */
    private final int size;

    /**
     * The filter is used to erode the terrain.
     * <ul>
     * <li>0 means no erosion, creating a sharp environment</li>
     * <li>0.5 is a smooth erosion, creating nice hills and valleys</li>
     * <li>1 means 100% erosion : the land is flat</li>
     * </ul>
     * It can be thought of as the age of the terrain. The older the terrain, the more eroded.
     */
    private final float erosionFilter;

    /** The maximum height used to normalize terrain */
    private final float maximumHeight;

    /**
     * Constructor builds a new heightmap using the midpoint displacement algorithm.
     * 
     * 
     * @param size the size of the terrain, must be a power of 2.
     * @param roughness how chaotic to make the terrain.
     * @throws Exception if roughness is negative of size is negative or not a power of 2
     */
    public MidPointHeightMapGenerator(final int size, final float roughness, float maximumHeight, float erosionFilter)
            throws Exception {
        if (roughness < 0) {
            throw new Exception("size and roughness must be greater than 0");
        }
        if (!MathHelper.isPowerOfTwo(size)) {
            throw new Exception("Size must be (2^N) sized and positive");
        }
        this.roughness = roughness;
        this.size = size;
        this.maximumHeight = maximumHeight;
        // limit the erosion between 0 and 0.6 as it is already a good erosion
        // moreover it depends on the roughness because a smooth terrain can not and should not be eroded too much
        this.erosionFilter = erosionFilter * (0.2f + 0.4f * roughness);
    }

    /**
     * Generates the heightfield using the Midpoint Displacement algorithm.
     */
    public float[] generateHeightData() {
        Random random = new Random();
        float height = 1f;
        // when height reducer is big (between 2 and 3), first iterations have a disproportionately large effect
        // creating smooth terrain
        // when it is small (between 1 and 2), late iterations have a disproportionately large effect creating chaotic
        // terrain
        // double heightReducer = Math.pow(2, -1 * roughness);
        double heightReducer = (1 - roughness) * 2 + 1;

        float[] heightData = new float[size * size * 3];
        float[][] tempBuffer = new float[size][size];

        // holds the points of the square.
        int ni, nj;
        int mi, mj;
        int pmi, pmj;

        int counter = size;
        while (counter > 0) {
            // displace the center of the square.
            for (int i = 0; i < size; i += counter) {
                for (int j = 0; j < size; j += counter) {
                    // (0,0) point of the local square
                    ni = (i + counter) % size;
                    nj = (j + counter) % size;
                    // middle point of the local square
                    mi = i + counter / 2;
                    mj = j + counter / 2;

                    // displace the middle point by the average of the
                    // corners, and a random value.
                    tempBuffer[mi][mj] = (tempBuffer[i][j] + tempBuffer[ni][j] + tempBuffer[i][nj] + tempBuffer[ni][nj])
                            / 4 + random.nextFloat() * height - height / 2;
                }
            }

            // next calculate the new midpoints of the line segments.
            for (int i = 0; i < size; i += counter) {
                for (int j = 0; j < size; j += counter) {
                    // (0,0) of the local square
                    ni = (i + counter) % size;
                    nj = (j + counter) % size;

                    // middle point of the local square.
                    mi = i + counter / 2;
                    mj = j + counter / 2;

                    // middle point on the line in the x-axis direction.
                    pmi = (i - counter / 2 + size) % size;
                    // middle point on the line in the y-axis direction.
                    pmj = (j - counter / 2 + size) % size;

                    // Calculate the square value for the top side of the rectangle
                    tempBuffer[mi][j] = (tempBuffer[i][j] + tempBuffer[ni][j] + tempBuffer[mi][pmj] + tempBuffer[mi][mj])
                            / 4 + random.nextFloat() * height - height / 2;

                    // Calculate the square value for the left side of the rectangle
                    tempBuffer[i][mj] = (tempBuffer[i][j] + tempBuffer[i][nj] + tempBuffer[pmi][mj] + tempBuffer[mi][mj])
                            / 4 + random.nextFloat() * height - height / 2;

                }
            }

            counter /= 2;
            // height *= heightReducer;
            height /= heightReducer;
        }

        erodeTerrain(tempBuffer);

        normalizeTerrain(tempBuffer);

        // transfer the new terrain into the height map.
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                setHeightAtPoint(heightData, tempBuffer[i][j], i, j);
            }
        }

        logger.info("Created Heightmap using Mid Point");

        return heightData;
    }

    /**
     * <code>setHeightAtPoint</code> sets the height value for a given coordinate.
     * 
     * @param heightData the result array in which to place coordinates
     * @param height the new height for the coordinate.
     * @param x the x (east/west) coordinate.
     * @param z the z (north/south) coordinate.
     */
    private void setHeightAtPoint(final float[] heightData, final float height, final int x, final int z) {
        int baseIndex = (x + z * size) * 3;
        heightData[baseIndex] = x;
        heightData[baseIndex + 1] = height;
        heightData[baseIndex + 2] = z;
    }

    /**
     * <code>normalizeTerrain</code> takes the current terrain data and converts it to values between 0 and
     * NORMALIZE_RANGE.
     * 
     * @param tempBuffer the terrain to normalize.
     */
    private void normalizeTerrain(final float[][] tempBuffer) {
        float currentMin, currentMax;
        float height;

        currentMin = tempBuffer[0][0];
        currentMax = tempBuffer[0][0];

        // find the min/max values of the height tempBuffer
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tempBuffer[i][j] > currentMax) {
                    currentMax = tempBuffer[i][j];
                } else
                    if (tempBuffer[i][j] < currentMin) {
                        currentMin = tempBuffer[i][j];
                    }
            }
        }

        // find the range of the altitude
        if (currentMax <= currentMin) {
            return;
        }

        height = currentMax - currentMin;

        // scale the values to a range of 0-255
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tempBuffer[i][j] = (tempBuffer[i][j] - currentMin) / height * maximumHeight;
            }
        }
    }

    /**
     * Convenience method that applies the FIR filter to the given height map. This simulates water erosion.
     * 
     * @param tempBuffer the terrain to filter.
     */
    protected void erodeTerrain(final float[][] tempBuffer) {
        float v;

        // erode left to right
        for (int i = 0; i < size; i++) {
            v = tempBuffer[i][0];
            for (int j = 1; j < size; j++) {
                tempBuffer[i][j] = erosionFilter * v + (1 - erosionFilter) * tempBuffer[i][j];
                v = tempBuffer[i][j];
            }
        }

        // erode right to left
        for (int i = size - 1; i >= 0; i--) {
            v = tempBuffer[i][0];
            for (int j = 0; j < size; j++) {
                tempBuffer[i][j] = erosionFilter * v + (1 - erosionFilter) * tempBuffer[i][j];
                v = tempBuffer[i][j];
                // erodeBand(tempBuffer[size * i + size - 1], -1);
            }
        }

        // erode top to bottom
        for (int i = 0; i < size; i++) {
            v = tempBuffer[0][i];
            for (int j = 0; j < size; j++) {
                tempBuffer[j][i] = erosionFilter * v + (1 - erosionFilter) * tempBuffer[j][i];
                v = tempBuffer[j][i];
            }
        }

        // erode from bottom to top
        for (int i = size - 1; i >= 0; i--) {
            v = tempBuffer[0][i];
            for (int j = 0; j < size; j++) {
                tempBuffer[j][i] = erosionFilter * v + (1 - erosionFilter) * tempBuffer[j][i];
                v = tempBuffer[j][i];
            }
        }
    }

}
