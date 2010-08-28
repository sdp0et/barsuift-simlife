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
package barsuift.simLife.environment;

import java.util.Observable;

import barsuift.simLife.Percent;
import barsuift.simLife.j3d.universe.environment.MockSun3D;
import barsuift.simLife.j3d.universe.environment.Sun3D;


public class MockSun extends Observable implements Sun {

    private Percent luminosity = new Percent(100);

    private Percent riseAngle = new Percent(25);

    private Percent zenithAngle = new Percent(50);

    private Sun3D sunLight = new MockSun3D();

    private SunState state = new SunState();


    @Override
    public Percent getLuminosity() {
        return luminosity;
    }

    @Override
    public void setLuminosity(Percent luminosity) {
        this.luminosity = luminosity;
    }

    @Override
    public Percent getRiseAngle() {
        return riseAngle;
    }

    @Override
    public void setRiseAngle(Percent riseAngle) {
        this.riseAngle = riseAngle;
    }

    @Override
    public Percent getZenithAngle() {
        return zenithAngle;
    }

    @Override
    public void setZenithAngle(Percent zenithAngle) {
        this.zenithAngle = zenithAngle;
    }

    @Override
    public Sun3D getSun3D() {
        return sunLight;
    }

    public void setSunLight(Sun3D sunLight) {
        this.sunLight = sunLight;
    }

    @Override
    public SunState getState() {
        return state;
    }

    public void setSunState(SunState state) {
        this.state = state;
    }

}
