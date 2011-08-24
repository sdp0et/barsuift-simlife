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
package barsuift.simLife.j3d.environment;

import javax.media.j3d.Group;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.j3d.DisplayDataCreatorForTests;
import barsuift.simLife.j3d.MockMobile;
import barsuift.simLife.j3d.universe.MockUniverse3D;
import static barsuift.simLife.j3d.assertions.GroupAssert.assertThat;

import static org.fest.assertions.Assertions.assertThat;


public class BasicWind3DTest {

    private Wind3DState wind3DState;

    private BasicWind3D wind3D;

    private MockUniverse3D universe3D;

    @BeforeMethod
    public void init() {
        wind3DState = DisplayDataCreatorForTests.createRandomWind3DState();
        universe3D = new MockUniverse3D();
        wind3D = new BasicWind3D(wind3DState);
        wind3D.init(universe3D);
    }

    @AfterMethod
    public void clean() {
        wind3DState = null;
        wind3D = null;
        universe3D = null;
    }

    @Test
    public void basicWind3D() {
        assertThat(wind3D.getGroup()).isNotNull();
        assertThat(wind3D.getState()).isNotNull();
    }

    @Test
    public void testGetState() {
        assertThat(wind3D.getState()).isEqualTo(wind3DState);
        assertThat(wind3D.getState()).isSameAs(wind3DState);
        assertThat(wind3D.getState().getWindTask()).isNotNull();
        // TODO uncomment
        // assertThat(wind3D.getState().isWindTaskAutomatic()).isTrue();
    }

    @Test
    public void testGroup() {
        // the group should be able to add new children
        assertThat(wind3D.getGroup()).hasCapability(Group.ALLOW_CHILDREN_EXTEND);
        // the group should be able to remove children
        assertThat(wind3D.getGroup()).hasCapability(Group.ALLOW_CHILDREN_WRITE);
    }

    @Test
    public void move() {
        MockMobile mobile = new MockMobile();
        assertThat(wind3D.getGroup()).hasSize(0);
        wind3D.move(mobile);
        assertThat(wind3D.getGroup()).hasSize(1);
        wind3D.isGrounded(mobile);
        assertThat(wind3D.getGroup()).hasSize(0);
    }

    // TODO uncomment
    // @Test
    // public void testSetWindTaskAutomatic() {
    // wind3DState = DisplayDataCreatorForTests.createSpecificWind3DState();
    // wind3D = new BasicWind3D(wind3DState);
    // wind3D.init(universe3D);
    // assertThat(wind3D.isWindTaskAutomatic()).isTrue();
    // assertThat(wind3D.getWindTask().isAutomatic()).isTrue();
    //
    // wind3D.setWindTaskAutomatic(true);
    // assertThat(wind3D.isWindTaskAutomatic()).isTrue();
    // assertThat(wind3D.getWindTask().isAutomatic()).isTrue();
    //
    // wind3D.setWindTaskAutomatic(false);
    // assertThat((wind3D.isWindTaskAutomatic())).isFalse();
    // assertThat((wind3D.getWindTask().isAutomatic())).isFalse();
    // }

}
