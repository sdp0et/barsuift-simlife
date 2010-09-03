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
import java.util.Observable;

import barsuift.simLife.j3d.universe.environment.BasicSun3D;
import barsuift.simLife.j3d.universe.environment.Sun3D;

/**
 * Class representing the sun.
 */
public class BasicSun extends Observable implements Sun {

    private BigDecimal luminosity;

    private BigDecimal riseAngle;

    private BigDecimal zenithAngle;

    private final Sun3D sun3D;

    /**
     * Creates a Sun instance with given state
     * 
     * @throws IllegalArgumentException if the given sun state is null
     */
    public BasicSun(SunState state) throws IllegalArgumentException {
        if (state == null) {
            throw new IllegalArgumentException("Null sun state");
        }
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
            notifyObservers(SunUpdateCode.luminosity);
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
            notifyObservers(SunUpdateCode.riseAngle);
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
            notifyObservers(SunUpdateCode.zenithAngle);
        }
    }

    @Override
    public SunState getState() {
        return new SunState(luminosity, riseAngle, zenithAngle);
    }

    @Override
    public Sun3D getSun3D() {
        return sun3D;
    }

    @Override
    public String toString() {
        return "BasicSun [luminosity=" + luminosity + ", riseAngle=" + riseAngle + ", zenithAngle=" + zenithAngle + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((luminosity == null) ? 0 : luminosity.hashCode());
        result = prime * result + ((riseAngle == null) ? 0 : riseAngle.hashCode());
        result = prime * result + ((zenithAngle == null) ? 0 : zenithAngle.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BasicSun other = (BasicSun) obj;
        if (luminosity == null) {
            if (other.luminosity != null)
                return false;
        } else
            if (!luminosity.equals(other.luminosity))
                return false;
        if (riseAngle == null) {
            if (other.riseAngle != null)
                return false;
        } else
            if (!riseAngle.equals(other.riseAngle))
                return false;
        if (zenithAngle == null) {
            if (other.zenithAngle != null)
                return false;
        } else
            if (!zenithAngle.equals(other.zenithAngle))
                return false;
        return true;
    }

}
