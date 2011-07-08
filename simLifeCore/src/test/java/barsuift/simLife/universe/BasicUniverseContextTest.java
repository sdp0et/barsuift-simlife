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

import org.testng.annotations.Test;

import barsuift.simLife.CoreDataCreatorForTests;

import static org.fest.assertions.Assertions.assertThat;


public class BasicUniverseContextTest {

    @Test
    public void testGetState() throws Exception {
        UniverseContextState state = CoreDataCreatorForTests.createSpecificUniverseContextState();
        BasicUniverseContext context = new BasicUniverseContext(state);
        context.init();
        assertThat(context.getState()).isEqualTo(state);
        assertThat(context.getState()).isSameAs(state);

        assertThat(context.getState().isFpsShowing()).isFalse();
        assertThat(context.getState().getUniverse().getDateHandler().getDate().getValue()).isEqualTo(100000);
        context.setFpsShowing(true);
        context.getUniverse().getUniverse3D().getSynchronizer().start();
        context.getUniverse().getUniverse3D().getSynchronizer().stop();
        // wait a little bit to ensure the time controller ends its treatments
        Thread.sleep(1500);

        assertThat(context.getState()).isEqualTo(state);
        assertThat(context.getState()).isSameAs(state);
        assertThat(context.getState().isFpsShowing()).isTrue();
        assertThat(context.getState().getUniverse().getDateHandler().getDate().getValue()).isEqualTo(100500);
    }


    @Test
    public void testSetFpsShowing() {
        UniverseContextState state = CoreDataCreatorForTests.createSpecificUniverseContextState();
        BasicUniverseContext universeContext = new BasicUniverseContext(state);
        universeContext.init();

        assertThat(universeContext.isFpsShowing()).isFalse();
        assertThat(universeContext.getUniverseContext3D().isFpsShowing()).isFalse();

        universeContext.setFpsShowing(true);
        assertThat(universeContext.isFpsShowing()).isTrue();
        assertThat(universeContext.getUniverseContext3D().isFpsShowing()).isTrue();
    }

}
