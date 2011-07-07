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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.time.FpsCounter;

import static org.fest.assertions.Assertions.assertThat;


public class BasicSimLifeCanvas3DTest {

    private BasicSimLifeCanvas3D canvas;

    private SimLifeCanvas3DState state;

    @BeforeMethod
    protected void setUp() {
        FpsCounter coreFpsCounter = new FpsCounter();
        state = DisplayDataCreatorForTests.createSpecificCanvasState();
        canvas = new BasicSimLifeCanvas3D(state);
        canvas.init(coreFpsCounter);
    }

    @AfterMethod
    protected void tearDown() {
        state = null;
        canvas = null;
    }

    @Test
    public void testIsFpsShowing() {
        assertThat(canvas.isFpsShowing()).isFalse();
        canvas.setFpsShowing(true);
        assertThat(canvas.isFpsShowing()).isTrue();
    }

    @Test
    public void testGetState() {
        assertThat(canvas.getState()).isEqualTo(state);
        assertThat(canvas.getState()).isSameAs(state);
        assertThat(canvas.getState().isFpsShowing()).isFalse();
        canvas.setFpsShowing(true);
        assertThat(canvas.getState()).isEqualTo(state);
        assertThat(canvas.getState()).isSameAs(state);
        assertThat(canvas.getState().isFpsShowing()).isTrue();
    }

}
