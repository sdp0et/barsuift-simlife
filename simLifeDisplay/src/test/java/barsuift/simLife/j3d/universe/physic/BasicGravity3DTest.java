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
package barsuift.simLife.j3d.universe.physic;

import javax.media.j3d.Group;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.j3d.MockMobile;
import barsuift.simLife.j3d.universe.BasicUniverse3D;
import barsuift.simLife.j3d.universe.Universe3DState;
import barsuift.simLife.j3d.universe.Universe3DStateFactory;
import barsuift.simLife.universe.MockUniverse;

import static org.fest.assertions.Assertions.assertThat;


public class BasicGravity3DTest {

    private BasicUniverse3D universe3D;

    private Gravity3DState gravity3DState;

    @BeforeMethod
    protected void setUp() {
        Universe3DStateFactory universe3DStateFactory = new Universe3DStateFactory();
        Universe3DState universe3DState = universe3DStateFactory.createUniverse3DState();
        universe3D = new BasicUniverse3D(universe3DState);
        universe3D.init(new MockUniverse());

        Gravity3DStateFactory gravity3DStateFactory = new Gravity3DStateFactory();
        gravity3DState = gravity3DStateFactory.createGravity3DState();
    }

    @AfterMethod
    protected void tearDown() {
        universe3D = null;
        gravity3DState = null;
    }

    @Test
    public void testGroup() {
        BasicGravity3D gravity3D = new BasicGravity3D(gravity3DState);
        gravity3D.init(universe3D);
        // the group should be able to add new children
        assertThat(gravity3D.getGroup().getCapability(Group.ALLOW_CHILDREN_EXTEND)).isTrue();
        // the group should be able to remove children
        assertThat(gravity3D.getGroup().getCapability(Group.ALLOW_CHILDREN_WRITE)).isTrue();
    }

    @Test
    public void testFall() {
        BasicGravity3D gravity3D = new BasicGravity3D(gravity3DState);
        gravity3D.init(universe3D);
        assertThat(gravity3D.getGroup().numChildren()).isEqualTo(0);
        MockMobile mobile = new MockMobile();
        gravity3D.fall(mobile);
        assertThat(gravity3D.getGroup().numChildren()).isEqualTo(1);
        gravity3D.isFallen(mobile);
        assertThat(gravity3D.getGroup().numChildren()).isEqualTo(0);
    }

}
