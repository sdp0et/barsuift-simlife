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
import javax.media.j3d.BranchGroup;

import barsuift.simLife.j3d.environment.Lights;
import barsuift.simLife.j3d.environment.Sun3D;


public class MockLights implements Lights {

    private AmbientLight ambientLight = new AmbientLight();

    private BranchGroup lightsGroup = new BranchGroup();

    private Sun3D sun3D = new MockSun3D();

    @Override
    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
    }

    @Override
    public BranchGroup getLightsGroup() {
        return lightsGroup;
    }

    public void setLightsGroup(BranchGroup lightsGroup) {
        this.lightsGroup = lightsGroup;
    }

    @Override
    public Sun3D getSun3D() {
        return sun3D;
    }

    public void setSun3D(Sun3D sun3D) {
        this.sun3D = sun3D;
    }

}
