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
        // if (1 == riseAngle) {
        // riseAngle = 0;
        // }
        zenithAngle = state.getZenithAngle();
        sun3D = new BasicSun3D(this);
    }

    /**
     * This method is to ensure the riseAngle is always comprised between 0 and 1
     */
    private void adjustRiseAngle() {
        // FIXME 001 unit test
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
        // if (1 == riseAngle) {
        // this.riseAngle = 0;
        // }
        setChanged();
        notifySubscribers(SunUpdateCode.riseAngle);
    }

    public float getZenithAngle() {
        return zenithAngle;
    }

    public void setZenithAngle(float zenithAngle) {
        this.zenithAngle = zenithAngle;
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
