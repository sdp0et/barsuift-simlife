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
import javax.media.j3d.Light;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;
import barsuift.simLife.process.EarthRotationTask;

// FIXME 000. 008. make the earthRevolution depends on the time of day (dynamic)
// FIXME 000. 010. make the earthRevolution depends on the time of year (at init time)
// FIXME 000. 011. be able to deactivate the earth rotation/revolution, sun brightness/color computation to switch to
// manual mode
public class BasicSun3D implements Sun3D {

    private final Sun3DState state;

    private final DirectionalLight light;

    private final BranchGroup group;

    private final Publisher publisher = new BasicPublisher(this);

    private final TransformGroup earthRotationTG;

    private final SunSphere3D sunSphere;

    private final Vector3d earthRotationVector;

    private final Transform3D earthRotationTransform;

    private final float latitude;

    private float sunHeight;

    // angle in radian, from 0 to 2*Pi
    private float earthRotation;

    private final EarthRotationTask earthRotationTask;

    // angle in radian, from 0 to 2*Pi
    private float earthRevolution;

    private BigDecimal brightness;

    public BasicSun3D(Sun3DState state, Universe3D universe3D) {
        super();
        this.state = state;
        this.latitude = state.getLatitude();

        earthRevolution = state.getEarthRevolution();
        adjustEarthRevolution();
        sunSphere = new SunSphere3D(latitude, state.getEclipticObliquity(), earthRevolution);

        earthRotationVector = new Vector3d(0, -Math.sin(latitude), -Math.cos(latitude));
        earthRotationTransform = new Transform3D();
        earthRotation = state.getEarthRotation();
        adjustEarthRotation();
        earthRotationTransform.setRotation(new AxisAngle4d(earthRotationVector, earthRotation));
        earthRotationTG = new TransformGroup();
        // this is to allow the sun disk to be rotated while live
        earthRotationTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        earthRotationTG.setTransform(earthRotationTransform);


        earthRotationTG.addChild(sunSphere.getGroup());
        group = new BranchGroup();
        group.addChild(earthRotationTG);


        light = new DirectionalLight();
        light.setInfluencingBounds(state.getBounds().toBoundingBox());
        light.setCapability(Light.ALLOW_COLOR_WRITE);
        light.setCapability(DirectionalLight.ALLOW_DIRECTION_WRITE);

        updateSunHeight();
        // no need to update brightness because it is already updated with the sun height
        updateLightDirection();
        // no need to update light color because it is already updated with the sun height

        this.earthRotationTask = new EarthRotationTask(state.getEarthRotationTask(), this, universe3D.getDate());
        universe3D.getSynchronizer().schedule(earthRotationTask);
    }

    /**
     * This method is to ensure the earthRotation is always comprised between 0 and 2*Pi
     */
    private void adjustEarthRotation() {
        while (earthRotation < 0) {
            earthRotation += 2 * Math.PI;
        }
        earthRotation %= 2 * Math.PI;
    }

    /**
     * This method is to ensure the earthRevolution is always comprised between 0 and 2*Pi
     */
    private void adjustEarthRevolution() {
        while (earthRevolution < 0) {
            earthRevolution += 2 * Math.PI;
        }
        earthRevolution %= 2 * Math.PI;
    }

    public float getEarthRotation() {
        return earthRotation;
    }

    public void setEarthRotation(float earthRotation) {
        this.earthRotation = earthRotation;
        adjustEarthRotation();
        earthRotationTransform.setRotation(new AxisAngle4d(earthRotationVector, earthRotation));
        earthRotationTG.setTransform(earthRotationTransform);
        setChanged();
        notifySubscribers(SunUpdateCode.EARTH_ROTATION);
        updateLightDirection();
        updateSunHeight();
        // no need to update brightness and color because they are already updated with sun height
    }

    public float getEarthRevolution() {
        return earthRevolution;
    }

    public void setEarthRevolution(float earthRevolution) {
        this.earthRevolution = earthRevolution;
        adjustEarthRevolution();
        sunSphere.updateForEclipticShift(earthRevolution);
        setChanged();
        notifySubscribers(SunUpdateCode.EARTH_REVOLUTION);
        updateSunHeight();
        // no need to update brightness and color because they are already updated with sun height

    }

    private void updateLightDirection() {
        float x = computeXDirection();
        float y = computeYDirection();
        float z = computeZDirection();
        Vector3f direction = new Vector3f(x, y, z);
        direction.normalize();
        light.setDirection(direction);
    }

    private float computeZDirection() {
        return -(float) Math.cos(earthRotation) * (float) Math.sin(latitude);
    }

    private float computeYDirection() {
        return (float) Math.cos(earthRotation) * (float) Math.cos(latitude);
    }

    private float computeXDirection() {
        return (float) Math.sin(earthRotation);
    }

    private void updateBrightness() {
        if (sunHeight < -SunSphere3D.RADIUS) {
            brightness = new BigDecimal(0);
        } else {
            if (sunHeight > SunSphere3D.RADIUS) {
                brightness = new BigDecimal(1);
            } else {
                brightness = new BigDecimal((1 + (float) Math.sin(sunHeight / SunSphere3D.RADIUS * Math.PI / 2)) / 2);
            }
        }
        setChanged();
        notifySubscribers(SunUpdateCode.BRIGHTNESS);
        updateLightColor();
    }

    private void updateLightColor() {
        float brightnessFloat = brightness.floatValue();
        float whiteFactor = getWhiteFactor();
        Color3f color = new Color3f(brightnessFloat, brightnessFloat * whiteFactor, brightnessFloat * whiteFactor);
        light.setColor(color);
        setChanged();
        notifySubscribers(SunUpdateCode.COLOR);
    }

    @Override
    public float getWhiteFactor() {
        return (float) Math.sqrt(Math.abs(sunHeight));
    }

    private void updateSunHeight() {
        Point3f transformedPoint = new Point3f();
        earthRotationTransform.transform(sunSphere.getSunCenter(), transformedPoint);
        this.sunHeight = transformedPoint.y / SunSphere3D.SHIFT;
        updateBrightness();
        // no need to recompute the sun color, as it is recomputed after the brightness anyway
    }

    public float getHeight() {
        return sunHeight;
    }

    @Override
    public BigDecimal getBrightness() {
        return brightness;
    }

    @Override
    public DirectionalLight getLight() {
        return light;
    }

    @Override
    public BranchGroup getGroup() {
        return group;
    }

    public void addSubscriber(Subscriber subscriber) {
        publisher.addSubscriber(subscriber);
    }

    public void deleteSubscriber(Subscriber subscriber) {
        publisher.deleteSubscriber(subscriber);
    }

    public void notifySubscribers() {
        publisher.notifySubscribers();
    }

    public void notifySubscribers(Object arg) {
        publisher.notifySubscribers(arg);
    }

    public void deleteSubscribers() {
        publisher.deleteSubscribers();
    }

    public boolean hasChanged() {
        return publisher.hasChanged();
    }

    public int countSubscribers() {
        return publisher.countSubscribers();
    }

    public void setChanged() {
        publisher.setChanged();
    }

    public void clearChanged() {
        publisher.clearChanged();
    }

    @Override
    public Sun3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setEarthRotation(earthRotation);
        state.setEarthRevolution(earthRevolution);
    }

}
