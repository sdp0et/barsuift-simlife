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

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.j3d.environment.Sun3DState;

@XmlRootElement
public class SunState implements State {

    private BigDecimal brightness;

    private BigDecimal riseAngle;

    private BigDecimal zenithAngle;

    private Sun3DState sun3DState;

    public SunState() {
        super();
        this.brightness = new BigDecimal(0);
        this.riseAngle = new BigDecimal(0);
        this.zenithAngle = new BigDecimal(0);
        this.sun3DState = new Sun3DState();
    }

    public SunState(BigDecimal brightness, BigDecimal riseAngle, BigDecimal zenithAngle, Sun3DState sun3DState) {
        super();
        this.brightness = brightness;
        this.riseAngle = riseAngle;
        this.zenithAngle = zenithAngle;
        this.sun3DState = sun3DState;
    }

    public BigDecimal getBrightness() {
        return brightness;
    }

    public void setBrightness(BigDecimal brightness) {
        this.brightness = brightness;
    }

    public BigDecimal getRiseAngle() {
        return riseAngle;
    }

    public void setRiseAngle(BigDecimal riseAngle) {
        this.riseAngle = riseAngle;
    }

    public BigDecimal getZenithAngle() {
        return zenithAngle;
    }

    public void setZenithAngle(BigDecimal zenithAngle) {
        this.zenithAngle = zenithAngle;
    }

    public Sun3DState getSun3DState() {
        return sun3DState;
    }

    public void setSun3DState(Sun3DState sun3dState) {
        sun3DState = sun3dState;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((brightness == null) ? 0 : brightness.hashCode());
        result = prime * result + ((riseAngle == null) ? 0 : riseAngle.hashCode());
        result = prime * result + ((sun3DState == null) ? 0 : sun3DState.hashCode());
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
        if (brightness == null) {
            if (other.brightness != null)
                return false;
        } else
            if (!brightness.equals(other.brightness))
                return false;
        if (riseAngle == null) {
            if (other.riseAngle != null)
                return false;
        } else
            if (!riseAngle.equals(other.riseAngle))
                return false;
        if (sun3DState == null) {
            if (other.sun3DState != null)
                return false;
        } else
            if (!sun3DState.equals(other.sun3DState))
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
        return "SunState [brightness=" + brightness + ", riseAngle=" + riseAngle + ", zenithAngle=" + zenithAngle
                + ", sun3DState=" + sun3DState + "]";
    }

}
