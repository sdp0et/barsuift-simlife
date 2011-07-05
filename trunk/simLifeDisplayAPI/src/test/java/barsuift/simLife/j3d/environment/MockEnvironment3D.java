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

import barsuift.simLife.j3d.landscape.Landscape3D;
import barsuift.simLife.j3d.landscape.MockLandscape3D;


public class MockEnvironment3D implements Environment3D {

    private Environment3DState env3DState;

    private int synchronizedCalled;

    private Group group;

    private Sky3D sky3D;

    private Wind3D wind3D;

    private Landscape3D landscape3D;

    public MockEnvironment3D() {
        reset();
    }

    public void reset() {
        env3DState = new Environment3DState();
        synchronizedCalled = 0;
        group = new Group();
        sky3D = new MockSky3D();
        wind3D = new MockWind3D();
        landscape3D = new MockLandscape3D();
    }

    @Override
    public Environment3DState getState() {
        return env3DState;
    }

    public void setState(Environment3DState env3dState) {
        env3DState = env3dState;
    }

    @Override
    public void synchronize() {
        this.synchronizedCalled++;
    }

    public int getNbSynchronize() {
        return synchronizedCalled;
    }

    public void setNbSynchronize(int nbSynchronize) {
        this.synchronizedCalled = nbSynchronize;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public Sky3D getSky3D() {
        return sky3D;
    }

    public void setSky3D(Sky3D sky3d) {
        sky3D = sky3d;
    }

    @Override
    public Wind3D getWind3D() {
        return wind3D;
    }

    public void setWind3D(Wind3D wind3d) {
        wind3D = wind3d;
    }

    @Override
    public Landscape3D getLandscape3D() {
        return landscape3D;
    }

    public void setLandscape3D(Landscape3D landscape3D) {
        this.landscape3D = landscape3D;
    }

}
