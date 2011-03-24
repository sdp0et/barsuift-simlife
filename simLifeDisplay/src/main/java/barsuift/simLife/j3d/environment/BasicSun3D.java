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
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import barsuift.simLife.environment.Sun;
import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;

// FIXME 000. 004. implement in proper way the computeBrightness (should be easy now).
// FIXME 000. 006. make the sky color change from blue to dark at night
// FIXME 000. 007. make the earthRotation depends on the time of day (dynamic)
// FIXME 000. 008. make the earthRevolution depends on the time of day (dynamic)
// FIXME 000. 009. make the earthRotation depends on the hour of the day (at init time)
// FIXME 000. 010. make the earthRevolution depends on the time of year (at init time)
// FIXME 000. 011. be able to deactivate the earth rotation/revolution, sun brightness/color computation to switch to
// manual mode
public class BasicSun3D implements Subscriber, Sun3D {

    private final Sun3DState state;

    private final Sun sun;

    private final DirectionalLight light;

    private final BranchGroup group;

    private final Publisher publisher = new BasicPublisher(this);

    private final TransformGroup earthRotationTG;

    private final SunSphere3D sunSphere;

    private final Vector3d earthRotationVector;

    private final float latitude;

    private float sunHeight;

    public BasicSun3D(Sun3DState state, Sun sun) {
        super();
        this.state = state;
        this.sun = sun;
        sun.addSubscriber(this);
        this.latitude = state.getLatitude();

        earthRotationVector = new Vector3d(0, -Math.sin(latitude), -Math.cos(latitude));
        earthRotationTG = new TransformGroup();
        // this is to allow the sun disk to be rotated while live
        earthRotationTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        earthRotationTG.setTransform(computeEarthRotationTransform());
        sunSphere = new SunSphere3D(latitude, state.getEclipticObliquity(), sun.getEarthRevolution() * 2
                * (float) Math.PI);
        earthRotationTG.addChild(sunSphere.getGroup());
        group = new BranchGroup();
        group.addChild(earthRotationTG);
        computeSunHeight();

        light = new DirectionalLight(computeColor(), computeDirection());
        light.setInfluencingBounds(state.getBounds().toBoundingBox());
        light.setCapability(Light.ALLOW_COLOR_WRITE);
        light.setCapability(DirectionalLight.ALLOW_DIRECTION_WRITE);
    }

    @Override
    public void update(Publisher o, Object arg) {
        if (arg == SunUpdateCode.BRIGHTNESS) {
            light.setColor(computeColor());
        }
        if (arg == SunUpdateCode.EARTH_ROTATION) {
            computeSunHeight();
            light.setDirection(computeDirection());
            light.setColor(computeColor());
            earthRotationTG.setTransform(computeEarthRotationTransform());
        }
        if (arg == SunUpdateCode.EARTH_REVOLUTION) {
            sunSphere.updateForEclipticShift(sun.getEarthRevolution() * 2 * (float) Math.PI);
            computeSunHeight();
            light.setColor(computeColor());
        }
    }

    // FIXME 000. 001. unit test
    Vector3f computeDirection() {
        float x = computeXDirection();
        float y = computeYDirection();
        float z = computeZDirection();
        Vector3f direction = new Vector3f(x, y, z);
        direction.normalize();
        return direction;
    }

    private float computeZDirection() {
        return -(float) Math.cos(sun.getEarthRotation() * 2 * Math.PI) * (float) Math.sin(latitude);
    }

    private float computeYDirection() {
        return (float) Math.cos(sun.getEarthRotation() * 2 * Math.PI) * (float) Math.cos(latitude);
    }

    private float computeXDirection() {
        return (float) Math.sin(sun.getEarthRotation() * 2 * Math.PI);
    }

    private Transform3D computeEarthRotationTransform() {
        double earthRotation = sun.getEarthRotation() * Math.PI * 2;
        Transform3D result = new Transform3D();
        result.setRotation(new AxisAngle4d(earthRotationVector, earthRotation));
        return result;
    }

    // TODO temporary code (for reminder)
    // private void computeBrightness() {
    // // double ratio = 20;
    // double ratio = 7;
    // // the sun diameter is thus 2 Pi / ratio (with sky radius of 1 : unit circle)
    // // here the angles ranges from 0 to 1 (not from 0 to 2*Pi)
    // // so the sun diameter is 1 / ratio
    // // and the sun radius is 1 / (2 * ratio)
    // // double sunRadius = 1 / (2 * ratio); // = 1/40 = 0.025
    //
    // // here the ratio is 7 and represents the ratio between the sky radius and the sun diameter
    // double sunRadius = 1 / (2 * ratio); // = 1/14 = 0.075
    // System.out.println("sunRadius=" + sunRadius);
    //
    // double brightness;
    // double sunHeight = computeSunHeight();
    // if (sunHeight < -sunRadius) {
    // brightness = 0;
    // } else {
    // if (sunHeight > sunRadius) {
    // brightness = 1;
    // } else {
    // brightness = (1 + Math.sin(sunHeight / sunRadius * Math.PI / 2)) / 2;
    // }
    // }
    // System.out.println("sunHeight=" + sunHeight);
    // System.out.println("-------------------- brightness=" + brightness);
    // }

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
        return (float) Math.sqrt(Math.abs(sunHeight));
    }

    private void computeSunHeight() {
        Point3f transformedPoint = new Point3f();
        computeEarthRotationTransform().transform(sunSphere.getSunCenter(), transformedPoint);
        this.sunHeight = transformedPoint.y / SunSphere3D.SHIFT;
    }

    // FIXME 000. 003. add to interface
    public float getHeight() {
        return sunHeight;
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
