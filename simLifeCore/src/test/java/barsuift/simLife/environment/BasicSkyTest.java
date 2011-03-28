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
package barsuift.simLife.environment;

import junit.framework.TestCase;
import barsuift.simLife.CoreDataCreatorForTests;


public class BasicSkyTest extends TestCase {

    public void testGetState() {
        SkyState state = CoreDataCreatorForTests.createRandomSkyState();
        BasicSky sky = new BasicSky(state);
        assertEquals(state, sky.getState());
        assertSame(state, sky.getState());
        assertEquals(state.getSunState().getSun3DState().getEarthRevolution(), sky.getSun().getSun3D()
                .getEarthRevolution());

        sky.getSun().getSun3D().setEarthRevolution(sky.getSun().getSun3D().getEarthRevolution() / 2);
        assertEquals(state, sky.getState());
        assertSame(state, sky.getState());
        assertEquals(state.getSunState().getSun3DState().getEarthRevolution(), sky.getSun().getSun3D()
                .getEarthRevolution());
    }

}
