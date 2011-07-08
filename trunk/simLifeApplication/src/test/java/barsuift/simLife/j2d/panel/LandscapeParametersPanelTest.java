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

import org.fest.assertions.Delta;
import org.testng.annotations.Test;

import barsuift.simLife.MathHelper;
import barsuift.simLife.landscape.LandscapeParameters;

import static org.fest.assertions.Assertions.assertThat;


public class LandscapeParametersPanelTest {

    @Test
    public void testReadWriteParameters() {
        LandscapeParameters parameters = new LandscapeParameters();
        parameters.random();
        LandscapeParameters originalParameters = new LandscapeParameters();
        originalParameters.setSize(parameters.getSize());
        originalParameters.setMaximumHeight(parameters.getMaximumHeight());
        originalParameters.setRoughness(parameters.getRoughness());
        originalParameters.setErosion(parameters.getErosion());

        LandscapeParametersPanel panel = new LandscapeParametersPanel(parameters);
        assertThat(parameters).isEqualTo(originalParameters);
        assertThat(parameters.getSize()).isEqualTo(originalParameters.getSize());
        assertThat(parameters.getMaximumHeight()).isEqualTo(originalParameters.getMaximumHeight(), Delta.delta(0.0001));
        assertThat(parameters.getRoughness()).isEqualTo(originalParameters.getRoughness(), Delta.delta(0.0001));
        assertThat(parameters.getErosion()).isEqualTo(originalParameters.getErosion(), Delta.delta(0.0001));

        // due to conversions (from float to int and vice versa), the precision is low

        panel.writeIntoParameters();
        assertThat(parameters.getSize()).isEqualTo(originalParameters.getSize());
        assertThat(parameters.getMaximumHeight()).isEqualTo(originalParameters.getMaximumHeight(), Delta.delta(0.01));
        assertThat(parameters.getRoughness()).isEqualTo(originalParameters.getRoughness(), Delta.delta(0.01));
        assertThat(parameters.getErosion()).isEqualTo(originalParameters.getErosion(), Delta.delta(0.01));

        panel.writeIntoParameters();
        assertThat(parameters.getSize()).isEqualTo(originalParameters.getSize());
        assertThat(parameters.getMaximumHeight()).isEqualTo(originalParameters.getMaximumHeight(), Delta.delta(0.01));
        assertThat(parameters.getRoughness()).isEqualTo(originalParameters.getRoughness(), Delta.delta(0.01));
        assertThat(parameters.getErosion()).isEqualTo(originalParameters.getErosion(), Delta.delta(0.01));
    }

    @Test
    public void testInit() {
        LandscapeParameters parameters = new LandscapeParameters();
        LandscapeParametersPanel panel = new LandscapeParametersPanel(parameters);

        assertThat(panel.getSizeText()).isEqualTo("Size (128 meters)");
        assertThat(1 << panel.getSizeSlider().getValue()).isEqualTo(parameters.getSize());
        assertThat(panel.getMaxHeightText()).isEqualTo("Maximum height (20 meters)");
        assertThat((float) panel.getMaxHeightSlider().getValue()).isEqualTo(parameters.getMaximumHeight(),
                Delta.delta(0.000001));
        assertThat(panel.getRoughnessText()).isEqualTo("Roughness (50%)");
        assertThat(panel.getRoughnessSlider().getValue() / 100f).isEqualTo(parameters.getRoughness(),
                Delta.delta(0.0051));
        assertThat(panel.getErosionText()).isEqualTo("Erosion (50%)");
        assertThat(panel.getErosionSlider().getValue() / 100f).isEqualTo(parameters.getErosion(), Delta.delta(0.0051));


        parameters = new LandscapeParameters();
        parameters.setSize(32);
        parameters.setMaximumHeight(30);
        parameters.setRoughness(0.2f);
        parameters.setErosion(0.7f);
        panel = new LandscapeParametersPanel(parameters);

        assertThat(panel.getSizeText()).isEqualTo("Size (32 meters)");
        assertThat(1 << panel.getSizeSlider().getValue()).isEqualTo(parameters.getSize());
        assertThat(panel.getMaxHeightText()).isEqualTo("Maximum height (30 meters)");
        assertThat((float) panel.getMaxHeightSlider().getValue()).isEqualTo(parameters.getMaximumHeight(),
                Delta.delta(0.000001));
        assertThat(panel.getRoughnessText()).isEqualTo("Roughness (20%)");
        assertThat(panel.getRoughnessSlider().getValue() / 100f).isEqualTo(parameters.getRoughness(),
                Delta.delta(0.0051));
        assertThat(panel.getErosionText()).isEqualTo("Erosion (70%)");
        assertThat(panel.getErosionSlider().getValue() / 100f).isEqualTo(parameters.getErosion(), Delta.delta(0.0051));
    }

    @Test
    public void testStateChanged() {
        LandscapeParameters parameters = new LandscapeParameters();
        LandscapeParametersPanel panel = new LandscapeParametersPanel(parameters);

        // initial value
        assertThat(panel.getSizeText()).isEqualTo("Size (128 meters)");
        assertThat(panel.getMaxHeightText()).isEqualTo("Maximum height (20 meters)");
        assertThat(panel.getRoughnessText()).isEqualTo("Roughness (50%)");
        assertThat(panel.getErosionText()).isEqualTo("Erosion (50%)");

        // stateChanged is automatically called
        panel.getSizeSlider().setValue(MathHelper.getPowerOfTwoExponent(32));
        assertThat(panel.getSizeText()).isEqualTo("Size (32 meters)");
        assertThat(panel.getMaxHeightText()).isEqualTo("Maximum height (20 meters)");
        assertThat(panel.getRoughnessText()).isEqualTo("Roughness (50%)");
        assertThat(panel.getErosionText()).isEqualTo("Erosion (50%)");

        // stateChanged is automatically called
        panel.getMaxHeightSlider().setValue(30);
        assertThat(panel.getSizeText()).isEqualTo("Size (32 meters)");
        assertThat(panel.getMaxHeightText()).isEqualTo("Maximum height (30 meters)");
        assertThat(panel.getRoughnessText()).isEqualTo("Roughness (50%)");
        assertThat(panel.getErosionText()).isEqualTo("Erosion (50%)");

        // stateChanged is automatically called
        panel.getRoughnessSlider().setValue(45);
        assertThat(panel.getSizeText()).isEqualTo("Size (32 meters)");
        assertThat(panel.getMaxHeightText()).isEqualTo("Maximum height (30 meters)");
        assertThat(panel.getRoughnessText()).isEqualTo("Roughness (45%)");
        assertThat(panel.getErosionText()).isEqualTo("Erosion (50%)");

        // stateChanged is automatically called
        panel.getErosionSlider().setValue(98);
        assertThat(panel.getSizeText()).isEqualTo("Size (32 meters)");
        assertThat(panel.getMaxHeightText()).isEqualTo("Maximum height (30 meters)");
        assertThat(panel.getRoughnessText()).isEqualTo("Roughness (45%)");
        assertThat(panel.getErosionText()).isEqualTo("Erosion (98%)");
    }

}
