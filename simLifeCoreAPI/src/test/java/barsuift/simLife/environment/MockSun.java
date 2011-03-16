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

import java.math.BigDecimal;

import barsuift.simLife.PercentHelper;
import barsuift.simLife.j3d.environment.MockSun3D;
import barsuift.simLife.j3d.environment.Sun3D;
import barsuift.simLife.message.BasicPublisher;


public class MockSun extends BasicPublisher implements Sun {

    public MockSun() {
        super(null);
    }

    private BigDecimal brightness = PercentHelper.getDecimalValue(100);

    private float earthRotation = 0.375f;

    private float zenithAngle = 0.5f;

    private Sun3D sunLight = new MockSun3D();

    private SunState state = new SunState();

    private int synchronizedCalled;


    @Override
    public BigDecimal getBrightness() {
        return brightness;
    }

    @Override
    public void setBrightness(BigDecimal brightness) {
        this.brightness = brightness;
    }

    @Override
    public float getEarthRotation() {
        return earthRotation;
    }

    @Override
    public void setEarthRotation(float earthRotation) {
        this.earthRotation = earthRotation;
    }

    @Override
    public float getZenithAngle() {
        return zenithAngle;
    }

    @Override
    public void setZenithAngle(float zenithAngle) {
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

    @Override
    public void synchronize() {
        this.synchronizedCalled++;
    }

    public int getNbSynchronize() {
        return synchronizedCalled;
    }

}
