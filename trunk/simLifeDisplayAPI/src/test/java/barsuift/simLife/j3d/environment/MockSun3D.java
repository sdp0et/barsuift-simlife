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

import javax.media.j3d.DirectionalLight;

import barsuift.simLife.message.BasicPublisher;


public class MockSun3D extends BasicPublisher implements Sun3D {

    private float whiteFactor = 1f;

    private DirectionalLight light = new DirectionalLight();

    private Sun3DState state = new Sun3DState();

    private int synchronizedCalled;

    public MockSun3D() {
        super(null);
    }

    @Override
    public float getWhiteFactor() {
        return whiteFactor;
    }

    public void setWhiteFactor(float whiteFactor) {
        this.whiteFactor = whiteFactor;
    }

    @Override
    public DirectionalLight getLight() {
        return light;
    }

    public void setLight(DirectionalLight light) {
        this.light = light;
    }

    @Override
    public Sun3DState getState() {
        return state;
    }

    public void setState(Sun3DState state) {
        this.state = state;
    }

    @Override
    public void synchronize() {
        this.synchronizedCalled++;
    }

    public int getNbSynchronize() {
        return synchronizedCalled;
    }

}
