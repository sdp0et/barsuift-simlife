/**
 * barsuift-simlife is a life simulator programm
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

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.PercentState;

@XmlRootElement
public class SunState {

    private PercentState luminosity;

    private PercentState riseAngle;

    private PercentState zenithAngle;

    public SunState() {
        super();
        this.luminosity = new PercentState();
        this.riseAngle = new PercentState();
        this.zenithAngle = new PercentState();
    }

    public SunState(PercentState luminosity, PercentState riseAngle, PercentState zenithAngle) {
        super();
        this.luminosity = luminosity;
        this.riseAngle = riseAngle;
        this.zenithAngle = zenithAngle;
    }

    public SunState(SunState copy) {
        super();
        this.luminosity = new PercentState(copy.getLuminosity());
        this.riseAngle = new PercentState(copy.getRiseAngle());
        this.zenithAngle = new PercentState(copy.getZenithAngle());
    }

    public PercentState getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(PercentState luminosity) {
        this.luminosity = luminosity;
    }

    public PercentState getRiseAngle() {
        return riseAngle;
    }

    public void setRiseAngle(PercentState riseAngle) {
        this.riseAngle = riseAngle;
    }

    public PercentState getZenithAngle() {
        return zenithAngle;
    }

    public void setZenithAngle(PercentState zenithAngle) {
        this.zenithAngle = zenithAngle;
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
        SunState other = (SunState) obj;
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

    @Override
    public String toString() {
        return "BasicSunState [luminosity=" + luminosity + ", riseAngle=" + riseAngle + ", zenithAngle=" + zenithAngle
                + "]";
    }

}
