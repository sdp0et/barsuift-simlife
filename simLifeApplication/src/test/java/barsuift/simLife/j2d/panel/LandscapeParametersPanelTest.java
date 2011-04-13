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
package barsuift.simLife.j2d.panel;

import junit.framework.TestCase;
import barsuift.simLife.MathHelper;
import barsuift.simLife.landscape.LandscapeParameters;


public class LandscapeParametersPanelTest extends TestCase {

    public void testReadWriteParameters() {
        LandscapeParameters parameters = new LandscapeParameters();
        parameters.random();
        LandscapeParameters originalParameters = new LandscapeParameters();
        originalParameters.setSize(parameters.getSize());
        originalParameters.setMaximumHeight(parameters.getMaximumHeight());
        originalParameters.setRoughness(parameters.getRoughness());
        originalParameters.setErosion(parameters.getErosion());

        LandscapeParametersPanel panel = new LandscapeParametersPanel(parameters);
        assertEquals(originalParameters, parameters);
        assertEquals(originalParameters.getSize(), parameters.getSize(), 0.0001);
        assertEquals(originalParameters.getMaximumHeight(), parameters.getMaximumHeight(), 0.0001);
        assertEquals(originalParameters.getRoughness(), parameters.getRoughness(), 0.0001);
        assertEquals(originalParameters.getErosion(), parameters.getErosion(), 0.0001);

        // due to conversions (from float to int and vice versa), the precision is low

        panel.writeIntoParameters();
        assertEquals(originalParameters.getSize(), parameters.getSize(), 0.01);
        assertEquals(originalParameters.getMaximumHeight(), parameters.getMaximumHeight(), 0.01);
        assertEquals(originalParameters.getRoughness(), parameters.getRoughness(), 0.01);
        assertEquals(originalParameters.getErosion(), parameters.getErosion(), 0.01);

        panel.writeIntoParameters();
        assertEquals(originalParameters.getSize(), parameters.getSize(), 0.01);
        assertEquals(originalParameters.getMaximumHeight(), parameters.getMaximumHeight(), 0.01);
        assertEquals(originalParameters.getRoughness(), parameters.getRoughness(), 0.01);
        assertEquals(originalParameters.getErosion(), parameters.getErosion(), 0.01);
    }

    public void testInit() {
        LandscapeParameters parameters = new LandscapeParameters();
        LandscapeParametersPanel panel = new LandscapeParametersPanel(parameters);

        assertEquals("Size (128 meters)", panel.getSizeText());
        assertEquals(parameters.getSize(), 1 << panel.getSizeSlider().getValue());
        assertEquals("Maximum height (20 meters)", panel.getMaxHeightText());
        assertEquals(parameters.getMaximumHeight(), panel.getMaxHeightSlider().getValue(), 0.000001);
        assertEquals("Roughness (50%)", panel.getRoughnessText());
        assertEquals(parameters.getRoughness(), panel.getRoughnessSlider().getValue() / 100f, 0.0051);
        assertEquals("Erosion (50%)", panel.getErosionText());
        assertEquals(parameters.getErosion(), panel.getErosionSlider().getValue() / 100f, 0.0051);


        parameters = new LandscapeParameters();
        parameters.setSize(32);
        parameters.setMaximumHeight(30);
        parameters.setRoughness(0.2f);
        parameters.setErosion(0.7f);
        panel = new LandscapeParametersPanel(parameters);

        assertEquals("Size (32 meters)", panel.getSizeText());
        assertEquals(parameters.getSize(), 1 << panel.getSizeSlider().getValue());
        assertEquals("Maximum height (30 meters)", panel.getMaxHeightText());
        assertEquals(parameters.getMaximumHeight(), panel.getMaxHeightSlider().getValue(), 0.000001);
        assertEquals("Roughness (20%)", panel.getRoughnessText());
        assertEquals(parameters.getRoughness(), panel.getRoughnessSlider().getValue() / 100f, 0.0051);
        assertEquals("Erosion (70%)", panel.getErosionText());
        assertEquals(parameters.getErosion(), panel.getErosionSlider().getValue() / 100f, 0.0051);
    }

    public void testStateChanged() {
        LandscapeParameters parameters = new LandscapeParameters();
        LandscapeParametersPanel panel = new LandscapeParametersPanel(parameters);

        // initial value
        assertEquals("Size (128 meters)", panel.getSizeText());
        assertEquals("Maximum height (20 meters)", panel.getMaxHeightText());
        assertEquals("Roughness (50%)", panel.getRoughnessText());
        assertEquals("Erosion (50%)", panel.getErosionText());

        // stateChanged is automatically called
        panel.getSizeSlider().setValue(MathHelper.getPowerOfTwoExponent(32));
        assertEquals("Size (32 meters)", panel.getSizeText());
        assertEquals("Maximum height (20 meters)", panel.getMaxHeightText());
        assertEquals("Roughness (50%)", panel.getRoughnessText());
        assertEquals("Erosion (50%)", panel.getErosionText());

        // stateChanged is automatically called
        panel.getMaxHeightSlider().setValue(30);
        assertEquals("Size (32 meters)", panel.getSizeText());
        assertEquals("Maximum height (30 meters)", panel.getMaxHeightText());
        assertEquals("Roughness (50%)", panel.getRoughnessText());
        assertEquals("Erosion (50%)", panel.getErosionText());

        // stateChanged is automatically called
        panel.getRoughnessSlider().setValue(45);
        assertEquals("Size (32 meters)", panel.getSizeText());
        assertEquals("Maximum height (30 meters)", panel.getMaxHeightText());
        assertEquals("Roughness (45%)", panel.getRoughnessText());
        assertEquals("Erosion (50%)", panel.getErosionText());

        // stateChanged is automatically called
        panel.getErosionSlider().setValue(98);
        assertEquals("Size (32 meters)", panel.getSizeText());
        assertEquals("Maximum height (30 meters)", panel.getMaxHeightText());
        assertEquals("Roughness (45%)", panel.getRoughnessText());
        assertEquals("Erosion (98%)", panel.getErosionText());
    }

}
