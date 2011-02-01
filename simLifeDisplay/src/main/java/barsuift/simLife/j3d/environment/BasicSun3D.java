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
import javax.media.j3d.Light;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import barsuift.simLife.environment.Sun;
import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;

public class BasicSun3D implements Subscriber, Sun3D {

    private final Sun3DState state;

    private final Sun sun;

    private float cosinusRiseAngle;

    private float sinusRiseAngle;

    private float cosinusZenithAngle;

    private float sinusZenithAngle;

    private final DirectionalLight light;

    private final Publisher publisher = new BasicPublisher(this);

    public BasicSun3D(Sun3DState state, Sun sun) {
        super();
        this.state = state;
        this.sun = sun;
        sun.addSubscriber(this);
        computeRiseAngleData();
        computeZenithAngleData();
        light = new DirectionalLight(computeColor(), computeDirection());
        light.setInfluencingBounds(state.getBounds().toBoundingBox());
        light.setCapability(Light.ALLOW_COLOR_WRITE);
        light.setCapability(DirectionalLight.ALLOW_DIRECTION_WRITE);
    }

    @Override
    public void update(Publisher o, Object arg) {
        if (arg == SunUpdateCode.brightness) {
            light.setColor(computeColor());
        }
        if (arg == SunUpdateCode.riseAngle) {
            computeRiseAngleData();
            light.setDirection(computeDirection());
            light.setColor(computeColor());
        }
        if (arg == SunUpdateCode.zenithAngle) {
            computeZenithAngleData();
            light.setDirection(computeDirection());
            light.setColor(computeColor());
        }
    }

    private Vector3f computeDirection() {
        Vector3f direction = new Vector3f(cosinusRiseAngle, -(sinusRiseAngle * sinusZenithAngle), -cosinusZenithAngle
                * sinusRiseAngle);
        direction.normalize();
        // computeBrightness();
        return direction;
    }

    //TODO temporary code (for reminder)
    private void computeBrightness() {
//        double ratio = 20;
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
        double sunHeight = sinusRiseAngle * sinusZenithAngle;
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

    private void computeRiseAngleData() {
        double azimuthAngle = sun.getRiseAngle() * Math.PI * 2;
        double riseAngle = azimuthAngle - Math.PI / 2;
        cosinusRiseAngle = (float) Math.cos(riseAngle);
        sinusRiseAngle = (float) Math.sin(riseAngle);
    }

    private Color3f computeColor() {
        float brightness = sun.getBrightness().floatValue();
        float whiteFactor = getWhiteFactor();
        Color3f color = new Color3f(brightness, brightness * whiteFactor, brightness * whiteFactor);
        setChanged();
        notifySubscribers(SunUpdateCode.color);
        return color;
    }

    @Override
    public float getWhiteFactor() {
        return (float) Math.sqrt(Math.abs(sinusRiseAngle * sinusZenithAngle));
    }

    public DirectionalLight getLight() {
        return light;
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
