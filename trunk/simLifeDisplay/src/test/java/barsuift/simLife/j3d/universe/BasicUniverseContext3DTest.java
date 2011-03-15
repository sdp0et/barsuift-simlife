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
import barsuift.simLife.landscape.LandscapeParameters;
import barsuift.simLife.universe.MockUniverseContext;


public class BasicUniverseContext3DTest extends TestCase {

    private UniverseContext3DState state;

    private UniverseContext3D universeContext3D;

    protected void setUp() throws Exception {
        super.setUp();
        LandscapeParameters parameters = new LandscapeParameters();
        parameters.random();
        UniverseContext3DStateFactory factory = new UniverseContext3DStateFactory();
        state = factory.createUniverseContext3DState(parameters);
        universeContext3D = new BasicUniverseContext3D(state, new MockUniverseContext());
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        state = null;
        universeContext3D = null;
    }

    public void testUnsetAxis() {
        assertTrue(universeContext3D.isAxisShowing());
        universeContext3D.setAxisShowing(false);
        assertFalse(universeContext3D.isAxisShowing());
        universeContext3D.setAxisShowing(true);
        assertTrue(universeContext3D.isAxisShowing());
        universeContext3D.setAxisShowing(false);
        assertFalse(universeContext3D.isAxisShowing());
    }

    public void testGetState() {
        assertEquals(state, universeContext3D.getState());
        assertSame(state, universeContext3D.getState());

        assertTrue(universeContext3D.getState().isAxisShowing());
        assertFalse(universeContext3D.getState().getCanvas().isFpsShowing());
        universeContext3D.setAxisShowing(false);
        universeContext3D.setFpsShowing(true);

        assertEquals(state, universeContext3D.getState());
        assertSame(state, universeContext3D.getState());
        assertFalse(universeContext3D.getState().isAxisShowing());
        assertTrue(universeContext3D.getState().getCanvas().isFpsShowing());
    }


    public void testSetFpsShowing() {
        assertFalse(universeContext3D.isFpsShowing());
        assertFalse(universeContext3D.getCanvas3D().isFpsShowing());

        universeContext3D.setFpsShowing(true);
        assertTrue(universeContext3D.isFpsShowing());
        assertTrue(universeContext3D.getCanvas3D().isFpsShowing());
    }

}
