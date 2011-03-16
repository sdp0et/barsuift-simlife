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

import barsuift.simLife.j3d.environment.BasicSun3D;
import barsuift.simLife.j3d.environment.Sun3D;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;

/**
 * Class representing the sun.
 */
public class BasicSun implements Sun {

    private final SunState state;

    private BigDecimal brightness;

    // FIXME 000. 003. remove zenithAngle
    private float earthRotation;

    private float earthRevolution;

    private float zenithAngle;

    private final Sun3D sun3D;

    private final Publisher publisher = new BasicPublisher(this);

    /**
     * Creates a Sun instance with given state
     * 
     * @throws IllegalArgumentException if the given sun state is null
     */
    public BasicSun(SunState state) throws IllegalArgumentException {
        if (state == null) {
            throw new IllegalArgumentException("Null sun state");
        }
        this.state = state;
        brightness = state.getBrightness();
        earthRotation = state.getEarthRotation();
        earthRevolution = state.getEarthRevolution();
        adjustEarthRotation();
        adjustEarthRevolution();
        zenithAngle = state.getZenithAngle();
        sun3D = new BasicSun3D(state.getSun3DState(), this);
    }

    /**
     * This method is to ensure the earthRotation is always comprised between 0 and 1
     */
    private void adjustEarthRotation() {
        while (earthRotation < 0) {
            earthRotation++;
        }
        earthRotation %= 1;
    }

    /**
     * This method is to ensure the earthRevolution is always comprised between 0 and 1
     */
    private void adjustEarthRevolution() {
        while (earthRevolution < 0) {
            earthRevolution++;
        }
        earthRevolution %= 1;
    }

    public BigDecimal getBrightness() {
        return brightness;
    }

    public void setBrightness(BigDecimal brightness) throws IllegalArgumentException {
        if (brightness == null) {
            throw new IllegalArgumentException("Sun brightness can not be null");
        }
        if (!this.brightness.equals(brightness)) {
            this.brightness = brightness;
            setChanged();
            notifySubscribers(SunUpdateCode.BRIGHTNESS);
        }
    }

    public float getEarthRotation() {
        return earthRotation;
    }

    public void setEarthRotation(float earthRotation) {
        this.earthRotation = earthRotation;
        adjustEarthRotation();
        // computeBrightness();
        setChanged();
        notifySubscribers(SunUpdateCode.EARTH_ROTATION);
    }

    public float getEarthRevolution() {
        return earthRevolution;
    }

    public void setEarthRevolution(float earthRevolution) {
        this.earthRevolution = earthRevolution;
        adjustEarthRevolution();
        // computeBrightness();
        setChanged();
        notifySubscribers(SunUpdateCode.EARTH_REVOLUTION);
    }

    // TODO temporary code (for reminder)
    private void computeBrightness() {
        // TODO ?? take into account the zenith angle also !!
        // ratio is the ratio between the sky perimeter and the sun diameter
        double ratio = 20;
        // the sun diameter is thus 2 Pi / ratio (with sky radius of 1 : unit circle)
        // here the angles ranges from 0 to 1 (not from 0 to 2*Pi)
        // so the sun diameter is 1 / ratio
        // and the sun radius is 1 / (2 * ratio)
        double sunRadius = 1 / (2 * ratio);

        double zenithAngleMin = sunRadius;
        double brightnessFromZenith;
        if (zenithAngle > zenithAngleMin) {
            brightnessFromZenith = 1;
        } else {
            brightnessFromZenith = (1 + Math.sin(Math.PI * ratio * zenithAngle)) / 2;
        }


        double earthRotationStartAngleStart = 0.25 - sunRadius;
        double earthRotationStartAngleEnd = 0.25 + sunRadius;
        double earthRotationEndAngleStart = 0.75 - sunRadius;
        double earthRotationEndAngleEnd = 0.75 + sunRadius;
        double brightnessFromEarthRotation;
        if (earthRotation < earthRotationStartAngleStart || earthRotation > earthRotationEndAngleEnd) {
            brightnessFromEarthRotation = 0;
        } else {
            if (earthRotation > earthRotationStartAngleEnd && earthRotation < earthRotationEndAngleStart) {
                brightnessFromEarthRotation = 1;
            } else {
                if (earthRotation < earthRotationStartAngleEnd) {
                    // earthRotation function
                    brightnessFromEarthRotation = (1 + Math.sin(Math.PI * ratio * (earthRotation - 0.25))) / 2;
                } else {
                    // earthRotation function
                    brightnessFromEarthRotation = (1 - Math.sin(Math.PI * ratio * (earthRotation - 0.75))) / 2;
                }
            }
        }
        double brightness = brightnessFromEarthRotation * brightnessFromZenith;
        System.out.println("earthRotation=" + earthRotation);
        System.out.println("zenithAngle=" + zenithAngle);
        // System.out.println("brightnessFromEarthRotation=" + brightnessFromEarthRotation);
        // System.out.println("brightnessFromZenith=" + brightnessFromZenith);
        // System.out.println("-------------------- brightness=" + brightness);
    }

    public float getZenithAngle() {
        return zenithAngle;
    }

    public void setZenithAngle(float zenithAngle) {
        this.zenithAngle = zenithAngle;
        // computeBrightness();
        setChanged();
        notifySubscribers(SunUpdateCode.ZENITH_ANGLE);
    }

    @Override
    public SunState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setBrightness(brightness);
        state.setEarthRotation(earthRotation);
        state.setEarthRevolution(earthRevolution);
        state.setZenithAngle(zenithAngle);
    }

    @Override
    public Sun3D getSun3D() {
        return sun3D;
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
