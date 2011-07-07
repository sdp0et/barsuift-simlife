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
package barsuift.simLife.j3d.landscape;

import java.util.Arrays;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;


public class Landscape3DStateFactoryTest {

    private Landscape3DStateFactory factory;

    @BeforeMethod
    protected void setUp() {
        factory = new Landscape3DStateFactory();
    }

    @AfterMethod
    protected void tearDown() {
        factory = null;
    }

    @Test
    public void testGenerateCoordinatesIndices() {
        int coordIdx[];
        // The size is expected to be a power of 2 and > 0.
        // As the tested method is not public, it is expected this assertion is verified.
        // So we won't test non-power of 2 cases or 0.

        coordIdx = factory.generateCoordinatesIndices(2);
        assertThat(coordIdx.length).isEqualTo(6);
        assertThat(coordIdx).isEqualTo(new int[] { 0, 2, 1, 1, 2, 3 });

        coordIdx = factory.generateCoordinatesIndices(4);
        assertThat(coordIdx.length).isEqualTo(54);
        assertThat(coordIdx).isEqualTo(
                new int[] { 0, 4, 1, 1, 4, 5, 1, 5, 2, 2, 5, 6, 2, 6, 3, 3, 6, 7, 4, 8, 5, 5, 8, 9, 5, 9, 6, 6, 9, 10,
                        6, 10, 7, 7, 10, 11, 8, 12, 9, 9, 12, 13, 9, 13, 10, 10, 13, 14, 10, 14, 11, 11, 14, 15 });

        coordIdx = factory.generateCoordinatesIndices(8);
        assertThat(coordIdx.length).isEqualTo(294);
        // only testing the 54th first elements, because it is much too long
        assertThat(Arrays.copyOfRange(coordIdx, 0, 54)).isEqualTo(
                new int[] { 0, 8, 1, 1, 8, 9, 1, 9, 2, 2, 9, 10, 2, 10, 3, 3, 10, 11, 3, 11, 4, 4, 11, 12, 4, 12, 5, 5,
                        12, 13, 5, 13, 6, 6, 13, 14, 6, 14, 7, 7, 14, 15, 8, 16, 9, 9, 16, 17, 9, 17, 10, 10, 17, 18 });
    }

    @Test
    public void testGenerateStripCounts() {
        int[] stripCounts;
        // The size is expected to be a power of 2 and > 0.
        // As the tested method is not public, it is expected this assertion is verified.
        // So we won't test non-power of 2 cases or 0.

        stripCounts = factory.generateStripCounts(2);
        assertThat(stripCounts.length).isEqualTo(2);
        assertThat(stripCounts).isEqualTo(new int[] { 3, 3 });

        stripCounts = factory.generateStripCounts(4);
        assertThat(stripCounts.length).isEqualTo(18);
        assertThat(stripCounts).isEqualTo(new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });

        stripCounts = factory.generateStripCounts(8);
        assertThat(stripCounts.length).isEqualTo(98);
        assertThat(stripCounts).isEqualTo(
                new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                        3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                        3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                        3, 3, 3, 3 });

        stripCounts = factory.generateStripCounts(16);
        assertThat(stripCounts.length).isEqualTo(450);
        assertThat(stripCounts).isEqualTo(
                new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                        3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                        3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                        3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                        3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                        3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                        3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                        3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                        3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                        3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                        3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                        3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                        3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                        3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                        3, 3, 3, 3, });
    }

}
