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

import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Light;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import barsuift.simLife.environment.Sun;
import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;

// FIXME double check the computeDirection methods and sinus/cosinus computations for sun light direction.
public class BasicSun3D implements Subscriber, Sun3D {

    private final Sun3DState state;

    private final Sun sun;

    private float cosinusEarthRotation;

    private float sinusEarthRotation;

    private float cosinusZenithAngle;

    private float sinusZenithAngle;

    private final DirectionalLight light;

    private final BranchGroup group;

    private final Publisher publisher = new BasicPublisher(this);

    private final TransformGroup earthRotationTG;

    private final SunSphere3D sunSphere;

    private final Vector3d earthRotationVector;

    public BasicSun3D(Sun3DState state, Sun sun) {
        super();
        this.state = state;
        this.sun = sun;
        sun.addSubscriber(this);
        computeEarthRotationData();
        computeZenithAngleData();
        light = new DirectionalLight(computeColor(), computeDirection());
        light.setInfluencingBounds(state.getBounds().toBoundingBox());
        light.setCapability(Light.ALLOW_COLOR_WRITE);
        light.setCapability(DirectionalLight.ALLOW_DIRECTION_WRITE);

        earthRotationVector = new Vector3d(0, -Math.sin(state.getLatitude()), -Math.cos(state.getLatitude()));
        earthRotationTG = new TransformGroup();
        // this is to allow the sun disk to be rotated while live
        earthRotationTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        earthRotationTG.setTransform(computeEarthRotationTransform());
        sunSphere = new SunSphere3D(state.getLatitude(), state.getEclipticObliquity());
        earthRotationTG.addChild(sunSphere.getGroup());
        group = new BranchGroup();
        group.addChild(earthRotationTG);

        sunSphere.updateForEclipticShift(sun.getEarthRevolution() * 2 * (float) Math.PI);
    }

    @Override
    public void update(Publisher o, Object arg) {
        if (arg == SunUpdateCode.BRIGHTNESS) {
            light.setColor(computeColor());
        }
        if (arg == SunUpdateCode.EARTH_ROTATION) {
            computeEarthRotationData();
            // TODO test to put the light in the same TG as the disk, instead of updating its direction
            light.setDirection(computeDirection());
            light.setColor(computeColor());
            earthRotationTG.setTransform(computeEarthRotationTransform());
        }
        if (arg == SunUpdateCode.ZENITH_ANGLE) {
            computeZenithAngleData();
            light.setDirection(computeDirection());
            light.setColor(computeColor());
        }
        if (arg == SunUpdateCode.EARTH_REVOLUTION) {
            sunSphere.updateForEclipticShift(sun.getEarthRevolution() * 2 * (float) Math.PI);
        }
    }

    private Vector3f computeDirection() {
        Vector3f direction = new Vector3f(cosinusEarthRotation, -(sinusEarthRotation * sinusZenithAngle),
                -cosinusZenithAngle * sinusEarthRotation);
        direction.normalize();
        // computeBrightness();
        return direction;
    }

    // TODO the rotation should also depend on the zenith angle !
    // FIXME store the rotation in state!!
    private Transform3D computeEarthRotationTransform() {
        double earthRotation = sun.getEarthRotation() * Math.PI * 2;
        Transform3D result = new Transform3D();
        result.setRotation(new AxisAngle4d(earthRotationVector, earthRotation));
        return result;
    }

    // TODO temporary code (for reminder)
    private void computeBrightness() {
        // double ratio = 20;
        double ratio = 7;
        // the sun diameter is thus 2 Pi / ratio (with sky radius of 1 : unit circle)
        // here the angles ranges from 0 to 1 (not from 0 to 2*Pi)
        // so the sun diameter is 1 / ratio
        // and the sun radius is 1 / (2 * ratio)
        // double sunRadius = 1 / (2 * ratio); // = 1/40 = 0.025

        // here the ratio is 7 and represents the ratio between the sky radius and the sun diameter
        double sunRadius = 1 / (2 * ratio); // = 1/14 = 0.075
        System.out.println("sunRadius=" + sunRadius);

        double brightness;
        double sunHeight = sinusEarthRotation * sinusZenithAngle;
        if (sunHeight < -sunRadius) {
            brightness = 0;
        } else {
            if (sunHeight > sunRadius) {
                brightness = 1;
            } else {
                brightness = (1 + Math.sin(sunHeight / sunRadius * Math.PI / 2)) / 2;
            }
        }
        System.out.println("sunHeight=" + sunHeight);
        System.out.println("-------------------- brightness=" + brightness);
    }

    private void computeZenithAngleData() {
        double zenithAngle = sun.getZenithAngle() * Math.PI / 2;
        cosinusZenithAngle = (float) Math.cos(zenithAngle);
        sinusZenithAngle = (float) Math.sin(zenithAngle);
    }

    private void computeEarthRotationData() {
        double azimuthAngle = sun.getEarthRotation() * Math.PI * 2;
        double earthRotation = azimuthAngle - Math.PI / 2;
        cosinusEarthRotation = (float) Math.cos(earthRotation);
        sinusEarthRotation = (float) Math.sin(earthRotation);
    }

    private Color3f computeColor() {
        float brightness = sun.getBrightness().floatValue();
        float whiteFactor = getWhiteFactor();
        Color3f color = new Color3f(brightness, brightness * whiteFactor, brightness * whiteFactor);
        setChanged();
        notifySubscribers(SunUpdateCode.COLOR);
        return color;
    }

    @Override
    public float getWhiteFactor() {
        return (float) Math.sqrt(Math.abs(sinusEarthRotation * sinusZenithAngle));
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
        // nothing to do
    }

}
