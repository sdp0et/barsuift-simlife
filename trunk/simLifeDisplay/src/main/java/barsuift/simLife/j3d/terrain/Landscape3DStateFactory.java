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

import barsuift.simLife.Randomizer;

public class Landscape3DStateFactory {

    public Landscape3DState createRandomLandscape3DState() {
        // size between 32 and 512
        int size = (int) Math.pow(2, Randomizer.randomBetween(5, 9));
        // roughness between 0.25 and 0.75
        float roughness = (float) (Math.random() + 0.5) / 2;
        // erosion between 0.25 and 0.75
        float erosion = (float) (Math.random() + 0.5) / 2;
        // max height between 10 and 50
        float maximumHeight = Randomizer.randomBetween(10, 50);
        MidPointHeightMapParameters parameters = new MidPointHeightMapParameters(size, roughness, erosion,
                maximumHeight);

        return createRandomLandscape3DStateWithParameters(parameters);
    }

    public Landscape3DState createRandomLandscape3DStateWithParameters(MidPointHeightMapParameters parameters) {
        MidPointHeightMapGenerator generator = new MidPointHeightMapGenerator(parameters);
        float[] coordinates = generator.generateHeightData();
        int[] coordinatesIndices = generateCoordinatesIndices(parameters.getSize());
        int[] stripCounts = generateStripCounts(parameters.getSize());
        return new Landscape3DState(parameters.getSize(), coordinates, coordinatesIndices, stripCounts);
    }

    /**
     * Generates coordinates indices for triangles. It considers a square of points aligned from left to right, and from
     * top to bottom. It considers each minimal square of four points, and split it into 2 triangles. It then add the
     * coordinates of these 2 triangles (6 coordinates indices) to the result array .
     * 
     * @param size the size of the landscape square array (size = number of lines = number of columns = 2^n)
     * @return an array of triangle points indices
     */
    int[] generateCoordinatesIndices(int size) {
        // 2 triangles * 3 points * (size-1) on each line * (size-1) lines
        int resultSize = 2 * 3 * (size - 1) * (size - 1);
        int[] result = new int[resultSize];
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1; j++) {
                result[(i * (size - 1) + j) * 6 + 0] = i * size + j;
                result[(i * (size - 1) + j) * 6 + 1] = i * size + j + size;
                result[(i * (size - 1) + j) * 6 + 2] = i * size + j + 1;

                result[(i * (size - 1) + j) * 6 + 3] = i * size + j + 1;
                result[(i * (size - 1) + j) * 6 + 4] = i * size + j + size;
                result[(i * (size - 1) + j) * 6 + 5] = i * size + j + size + 1;
            }
        }
        return result;
    }

    /**
     * Consider 2 triangles for each minimal square of the landscape.
     * 
     * @param size the size of the landscape square array (size = number of lines = number of columns = 2^n)
     * @return the strip counts array
     */
    int[] generateStripCounts(int size) {
        // 2 triangles * (size-1) on each line * (size-1) lines
        int stripCountSize = 2 * (size - 1) * (size - 1);
        int[] stripCounts = new int[stripCountSize];
        Arrays.fill(stripCounts, 3);
        return stripCounts;
    }

}
