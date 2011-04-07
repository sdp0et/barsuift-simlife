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

import java.math.BigDecimal;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;

import barsuift.simLife.Automatable;
import barsuift.simLife.MockAutomatable;
import barsuift.simLife.PercentHelper;
import barsuift.simLife.message.BasicPublisher;


public class MockSun3D extends BasicPublisher implements Sun3D {

    private BigDecimal brightness;

    private float earthRotation;

    private float earthRevolution;

    private float height;

    private float whiteFactor;

    private DirectionalLight light;

    private Sun3DState state;

    private int synchronizedCalled;

    private BranchGroup group;

    private Automatable earthRotationTask;

    private boolean earthRotationTaskAutomatic;

    private Automatable earthRevolutionTask;

    private boolean earthRevolutionTaskAutomatic;

    public MockSun3D() {
        super(null);
        reset();
    }

    private void reset() {
        brightness = PercentHelper.getDecimalValue(100);
        earthRotation = (float) (3 * Math.PI / 4);
        earthRevolution = (float) Math.PI;
        height = 0.5f;
        whiteFactor = 1f;
        light = new DirectionalLight();
        state = new Sun3DState();
        synchronizedCalled = 0;
        group = new BranchGroup();
        earthRotationTask = new MockAutomatable();
        earthRotationTaskAutomatic = true;
        earthRevolutionTask = new MockAutomatable();
        earthRevolutionTaskAutomatic = true;
    }

    @Override
    public BigDecimal getBrightness() {
        return brightness;
    }

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
    public float getEarthRevolution() {
        return earthRevolution;
    }

    @Override
    public void setEarthRevolution(float earthRevolution) {
        this.earthRevolution = earthRevolution;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
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

    @Override
    public BranchGroup getGroup() {
        return group;
    }

    public void setGroup(BranchGroup group) {
        this.group = group;
    }

    @Override
    public Automatable getEarthRotationTask() {
        return earthRotationTask;
    }

    public void setEarthRotationTask(Automatable earthRotationTask) {
        this.earthRotationTask = earthRotationTask;
    }

    @Override
    public void setEarthRotationTaskAutomatic(boolean automatic) {
        this.earthRotationTaskAutomatic = automatic;
    }

    @Override
    public boolean isEarthRotationTaskAutomatic() {
        return earthRotationTaskAutomatic;
    }

    @Override
    public Automatable getEarthRevolutionTask() {
        return earthRevolutionTask;
    }

    public void setEarthRevolutionTask(Automatable earthRevolutionTask) {
        this.earthRevolutionTask = earthRevolutionTask;
    }

    @Override
    public void setEarthRevolutionTaskAutomatic(boolean automatic) {
        this.earthRevolutionTaskAutomatic = automatic;
    }

    @Override
    public boolean isEarthRevolutionTaskAutomatic() {
        return earthRevolutionTaskAutomatic;
    }

}
