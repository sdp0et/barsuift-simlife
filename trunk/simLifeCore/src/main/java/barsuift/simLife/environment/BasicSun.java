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

    private float riseAngle;

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
        riseAngle = state.getRiseAngle();
        adjustRiseAngle();
        zenithAngle = state.getZenithAngle();
        sun3D = new BasicSun3D(state.getSun3DState(), this);
    }

    /**
     * This method is to ensure the riseAngle is always comprised between 0 and 1
     */
    private void adjustRiseAngle() {
        while (riseAngle < 0) {
            riseAngle++;
        }
        riseAngle %= 1;
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
            notifySubscribers(SunUpdateCode.brightness);
        }
    }

    public float getRiseAngle() {
        return riseAngle;
    }


    public void setRiseAngle(float riseAngle) {
        this.riseAngle = riseAngle;
        adjustRiseAngle();
        // computeBrightness();
        setChanged();
        notifySubscribers(SunUpdateCode.riseAngle);
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


        double riseStartAngleStart = 0.25 - sunRadius;
        double riseStartAngleEnd = 0.25 + sunRadius;
        double riseEndAngleStart = 0.75 - sunRadius;
        double riseEndAngleEnd = 0.75 + sunRadius;
        double brightnessFromRise;
        if (riseAngle < riseStartAngleStart || riseAngle > riseEndAngleEnd) {
            brightnessFromRise = 0;
        } else {
            if (riseAngle > riseStartAngleEnd && riseAngle < riseEndAngleStart) {
                brightnessFromRise = 1;
            } else {
                if (riseAngle < riseStartAngleEnd) {
                    // sunrise function
                    brightnessFromRise = (1 + Math.sin(Math.PI * ratio * (riseAngle - 0.25))) / 2;
                } else {
                    // sunset function
                    brightnessFromRise = (1 - Math.sin(Math.PI * ratio * (riseAngle - 0.75))) / 2;
                }
            }
        }
        double brightness = brightnessFromRise * brightnessFromZenith;
        System.out.println("riseAngle=" + riseAngle);
        System.out.println("zenithAngle=" + zenithAngle);
        // System.out.println("brightnessFromRise=" + brightnessFromRise);
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
        notifySubscribers(SunUpdateCode.zenithAngle);
    }

    @Override
    public SunState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setBrightness(brightness);
        state.setRiseAngle(riseAngle);
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
