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

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Light;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import barsuift.simLife.environment.Sun;
import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;

public class BasicSun3D implements Subscriber, Sun3D {

    private static BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0, 0), 100);

    private final Sun sun;

    private float cosinusRiseAngle;

    private float sinusRiseAngle;

    private float cosinusZenithAngle;

    private float sinusZenithAngle;

    private final DirectionalLight light;

    private final Publisher publisher = new BasicPublisher(this);

    public BasicSun3D(Sun sun) {
        super();
        this.sun = sun;
        sun.addSubscriber(this);
        computeRiseAngleData();
        computeZenithAngleData();
        light = new DirectionalLight(computeColor(), computeDirection());
        light.setInfluencingBounds(bounds);
        light.setCapability(Light.ALLOW_COLOR_WRITE);
        light.setCapability(DirectionalLight.ALLOW_DIRECTION_WRITE);
    }

    @Override
    public void update(Publisher o, Object arg) {
        if (arg == SunUpdateCode.luminosity) {
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
        return direction;
    }

    private void computeZenithAngleData() {
        double zenithAngle = sun.getZenithAngle().doubleValue() * Math.PI / 2;
        cosinusZenithAngle = (float) Math.cos(zenithAngle);
        sinusZenithAngle = (float) Math.sin(zenithAngle);
    }

    private void computeRiseAngleData() {
        double riseAngle = sun.getRiseAngle().doubleValue() * Math.PI;
        cosinusRiseAngle = (float) Math.cos(riseAngle);
        sinusRiseAngle = (float) Math.sin(riseAngle);
    }

    private Color3f computeColor() {
        float luminosity = sun.getLuminosity().floatValue();
        float whiteFactor = getWhiteFactor().floatValue();
        Color3f color = new Color3f(luminosity, luminosity * whiteFactor, luminosity * whiteFactor);
        setChanged();
        notifySubscribers(SunUpdateCode.color);
        return color;
    }

    @Override
    public BigDecimal getWhiteFactor() {
        return new BigDecimal(Math.sqrt(Math.abs(sinusRiseAngle * sinusZenithAngle)));
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

}