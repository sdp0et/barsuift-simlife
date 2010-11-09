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
package barsuift.simLife.universe;

import junit.framework.TestCase;


public class BasicUniverseContextTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testUnsetAxis() {
        UniverseContextStateFactory factory = new UniverseContextStateFactory();
        UniverseContextState state = factory.createRandomUniverseContextState();
        UniverseContext panel = new BasicUniverseContext(state);
        assertTrue(panel.isAxisShowing());
        panel.setAxisShowing(false);
        assertFalse(panel.isAxisShowing());
        panel.setAxisShowing(true);
        assertTrue(panel.isAxisShowing());
        panel.setAxisShowing(false);
        assertFalse(panel.isAxisShowing());
    }

    public void testGetState() throws Exception {
        UniverseContextStateFactory factory = new UniverseContextStateFactory();
        UniverseContextState state = factory.createRandomUniverseContextState();
        BasicUniverseContext context = new BasicUniverseContext(state);
        assertEquals(state, context.getState());
        assertSame(state, context.getState());
        assertTrue(context.getState().isAxisShowing());
        assertEquals(0, context.getState().getUniverseState().getDateHandler().getDate().getValue());
        context.setAxisShowing(false);
        context.getUniverse().getSynchronizer().start();
        context.getUniverse().getSynchronizer().stop();
        // wait a little bit to ensure the time controller ends its treatments
        Thread.sleep(600);
        assertEquals(state, context.getState());
        assertSame(state, context.getState());
        assertFalse(context.getState().isAxisShowing());
        assertEquals(500, context.getState().getUniverseState().getDateHandler().getDate().getValue());
    }


    public void testSetFpsShowing() {
        UniverseContextStateFactory factory = new UniverseContextStateFactory();
        UniverseContextState state = factory.createRandomUniverseContextState();
        BasicUniverseContext universeContext = new BasicUniverseContext(state);

        assertFalse(universeContext.isFpsShowing());
        assertFalse(universeContext.getCanvas3D().isFpsShowing());

        universeContext.setFpsShowing(true);
        assertTrue(universeContext.isFpsShowing());
        assertTrue(universeContext.getCanvas3D().isFpsShowing());
    }

}
