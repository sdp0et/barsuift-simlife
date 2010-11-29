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
package barsuift.simLife.j3d.universe;

import junit.framework.TestCase;
import barsuift.simLife.j3d.SimLifeCanvas3DState;
import barsuift.simLife.j3d.terrain.NavigatorState;


public class UniverseContext3DStateFactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateRandomUniverseContext3DState() {
        UniverseContext3DStateFactory factory = new UniverseContext3DStateFactory();
        UniverseContext3DState universeContext3DState = factory.createUniverseContext3DState();

        assertTrue(universeContext3DState.isAxisShowing());

        SimLifeCanvas3DState canvasState = universeContext3DState.getCanvas();
        assertNotNull(canvasState);
        assertFalse(canvasState.isFpsShowing());

        NavigatorState navigatorState = universeContext3DState.getNavigator();
        assertNotNull(navigatorState);
        assertEquals(0.0, navigatorState.getRotationX());
        assertEquals(0.0, navigatorState.getRotationY());
        assertEquals(4.0, navigatorState.getTranslation().getX());
        assertEquals(2.0, navigatorState.getTranslation().getY());
        assertEquals(20.0, navigatorState.getTranslation().getZ());

    }

}
