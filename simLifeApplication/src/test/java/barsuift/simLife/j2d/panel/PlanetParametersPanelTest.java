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
import barsuift.simLife.PlanetParameters;

import static org.fest.assertions.Assertions.assertThat;


public class PlanetParametersPanelTest {

    @Test
    public void testReadWriteParameters() {
        PlanetParameters parameters = new PlanetParameters();
        parameters.random();
        PlanetParameters originalParameters = new PlanetParameters();
        originalParameters.setLatitude(parameters.getLatitude());
        originalParameters.setEclipticObliquity(parameters.getEclipticObliquity());

        PlanetParametersPanel panel = new PlanetParametersPanel(parameters);
        assertThat(parameters).isEqualTo(originalParameters);
        assertThat(parameters.getLatitude()).isEqualTo(originalParameters.getLatitude(), Delta.delta(0.0001));
        assertThat(parameters.getEclipticObliquity()).isEqualTo(originalParameters.getEclipticObliquity(),
                Delta.delta(0.0001));

        // due to conversions (from radian (float) to degree (int) and vice versa), the precision is low

        panel.writeIntoParameters();
        assertThat(parameters.getLatitude()).isEqualTo(originalParameters.getLatitude(), Delta.delta(0.01));
        assertThat(parameters.getEclipticObliquity()).isEqualTo(originalParameters.getEclipticObliquity(),
                Delta.delta(0.01));

        panel.writeIntoParameters();
        assertThat(parameters.getLatitude()).isEqualTo(originalParameters.getLatitude(), Delta.delta(0.01));
        assertThat(parameters.getEclipticObliquity()).isEqualTo(originalParameters.getEclipticObliquity(),
                Delta.delta(0.01));
    }

    @Test
    public void testInit() {
        PlanetParameters parameters = new PlanetParameters();
        PlanetParametersPanel panel = new PlanetParametersPanel(parameters);

        assertThat(panel.getLatitudeText()).isEqualTo("Latitude (45°)");
        assertThat(MathHelper.toRadian(panel.getLatitudeSlider().getValue())).isEqualTo(parameters.getLatitude(),
                Delta.delta(0.01));
        assertThat(panel.getEclipticObliquityText()).isEqualTo("Planet Ecliptic Obliquity (23°)");
        assertThat(MathHelper.toRadian(panel.getEclipticObliquitySlider().getValue())).isEqualTo(
                parameters.getEclipticObliquity(), Delta.delta(0.01));


        parameters = new PlanetParameters();
        parameters.setLatitude((float) (Math.PI / 3));
        parameters.setEclipticObliquity((float) (Math.PI / 4));
        panel = new PlanetParametersPanel(parameters);

        assertThat(panel.getLatitudeText()).isEqualTo("Latitude (60°)");
        assertThat(MathHelper.toRadian(panel.getLatitudeSlider().getValue())).isEqualTo(parameters.getLatitude(),
                Delta.delta(0.01));
        assertThat(panel.getEclipticObliquityText()).isEqualTo("Planet Ecliptic Obliquity (45°)");
        assertThat(MathHelper.toRadian(panel.getEclipticObliquitySlider().getValue())).isEqualTo(
                parameters.getEclipticObliquity(), Delta.delta(0.01));
    }

    @Test
    public void testStateChanged() {
        PlanetParameters parameters = new PlanetParameters();
        PlanetParametersPanel panel = new PlanetParametersPanel(parameters);

        // initial value
        assertThat(panel.getLatitudeText()).isEqualTo("Latitude (45°)");
        assertThat(panel.getEclipticObliquityText()).isEqualTo("Planet Ecliptic Obliquity (23°)");

        // stateChanged is automatically called
        panel.getLatitudeSlider().setValue(60);
        assertThat(panel.getLatitudeText()).isEqualTo("Latitude (60°)");
        assertThat(panel.getEclipticObliquityText()).isEqualTo("Planet Ecliptic Obliquity (23°)");

        // stateChanged is automatically called
        panel.getEclipticObliquitySlider().setValue(88);
        assertThat(panel.getLatitudeText()).isEqualTo("Latitude (60°)");
        assertThat(panel.getEclipticObliquityText()).isEqualTo("Planet Ecliptic Obliquity (88°)");
    }

}
