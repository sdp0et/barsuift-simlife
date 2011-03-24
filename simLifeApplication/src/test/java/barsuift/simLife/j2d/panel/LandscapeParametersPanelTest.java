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

}
