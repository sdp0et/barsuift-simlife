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
package barsuift.simLife.j3d;

import junit.framework.TestCase;
import barsuift.simLife.time.FpsCounter;


public class BasicSimLifeCanvas3DTest extends TestCase {

    private BasicSimLifeCanvas3D canvas;

    private SimLifeCanvas3DState state;

    protected void setUp() throws Exception {
        super.setUp();
        FpsCounter coreFpsCounter = new FpsCounter();
        state = DisplayDataCreatorForTests.createSpecificCanvasState();
        canvas = new BasicSimLifeCanvas3D(coreFpsCounter, state);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        state = null;
        canvas = null;
    }

    public void testIsFpsShowing() {
        assertFalse(canvas.isFpsShowing());
        canvas.setFpsShowing(true);
        assertTrue(canvas.isFpsShowing());
    }

    public void testGetState() {
        assertEquals(state, canvas.getState());
        assertSame(state, canvas.getState());
        assertFalse(canvas.getState().isFpsShowing());
        canvas.setFpsShowing(true);
        assertEquals(state, canvas.getState());
        assertSame(state, canvas.getState());
        assertTrue(canvas.getState().isFpsShowing());
    }

}
