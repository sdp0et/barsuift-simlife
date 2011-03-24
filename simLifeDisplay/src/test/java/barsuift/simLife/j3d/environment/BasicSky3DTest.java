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

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Group;

import junit.framework.TestCase;
import barsuift.simLife.environment.MockSky;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;


public class BasicSky3DTest extends TestCase {

    public void testGetGroup() {
        Sky3DState state = DisplayDataCreatorForTests.createRandomSky3DState();
        BasicSky3D sky3D = new BasicSky3D(state, new MockSky());

        Group group = sky3D.getGroup();
        assertEquals(3, group.numChildren());
        assertTrue(group.getChild(0) instanceof AmbientLight);
        assertTrue(group.getChild(1) instanceof DirectionalLight);
        assertTrue(group.getChild(2) instanceof Background);
    }

    public void testGetState() {
        Sky3DState state = DisplayDataCreatorForTests.createRandomSky3DState();
        BasicSky3D sky3D = new BasicSky3D(state, new MockSky());
        assertEquals(state, sky3D.getState());
        assertSame(state, sky3D.getState());
    }

}
