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

    private BigDecimal luminosity;

    private BigDecimal riseAngle;

    private BigDecimal zenithAngle;

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
        luminosity = state.getLuminosity();
        riseAngle = state.getRiseAngle();
        zenithAngle = state.getZenithAngle();
        sun3D = new BasicSun3D(this);
    }

    public BigDecimal getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(BigDecimal luminosity) throws IllegalArgumentException {
        if (luminosity == null) {
            throw new IllegalArgumentException("Sun luminosity can not be null");
        }
        if (!this.luminosity.equals(luminosity)) {
            this.luminosity = luminosity;
            setChanged();
            notifySubscribers(SunUpdateCode.luminosity);
        }
    }

    public BigDecimal getRiseAngle() {
        return riseAngle;
    }


    public void setRiseAngle(BigDecimal riseAngle) {
        if (riseAngle == null) {
            throw new IllegalArgumentException("Sun rise angle can not be null");
        }
        if (!this.riseAngle.equals(riseAngle)) {
            this.riseAngle = riseAngle;
            setChanged();
            notifySubscribers(SunUpdateCode.riseAngle);
        }
    }

    public BigDecimal getZenithAngle() {
        return zenithAngle;
    }

    public void setZenithAngle(BigDecimal zenithAngle) {
        if (zenithAngle == null) {
            throw new IllegalArgumentException("Sun zenith angle can not be null");
        }
        if (!this.zenithAngle.equals(zenithAngle)) {
            this.zenithAngle = zenithAngle;
            setChanged();
            notifySubscribers(SunUpdateCode.zenithAngle);
        }
    }

    @Override
    public SunState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setLuminosity(luminosity);
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
