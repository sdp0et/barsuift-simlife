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
import barsuift.simLife.PlanetParameters;


public class PlanetParametersPanelTest extends TestCase {

    public void testReadWriteParameters() {
        PlanetParameters parameters = new PlanetParameters();
        parameters.random();
        PlanetParameters originalParameters = new PlanetParameters();
        originalParameters.setLatitude(parameters.getLatitude());
        originalParameters.setEclipticObliquity(parameters.getEclipticObliquity());

        PlanetParametersPanel panel = new PlanetParametersPanel(parameters);
        assertEquals(originalParameters, parameters);
        assertEquals(originalParameters.getLatitude(), parameters.getLatitude(), 0.0001);
        assertEquals(originalParameters.getEclipticObliquity(), parameters.getEclipticObliquity(), 0.0001);

        // due to conversions (from radian (float) to degree (int) and vice versa), the precision is low

        panel.writeIntoParameters();
        assertEquals(originalParameters.getLatitude(), parameters.getLatitude(), 0.01);
        assertEquals(originalParameters.getEclipticObliquity(), parameters.getEclipticObliquity(), 0.01);

        panel.writeIntoParameters();
        assertEquals(originalParameters.getLatitude(), parameters.getLatitude(), 0.01);
        assertEquals(originalParameters.getEclipticObliquity(), parameters.getEclipticObliquity(), 0.01);
    }
}
