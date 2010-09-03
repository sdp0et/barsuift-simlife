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
package barsuift.simLife.time;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SimLifeCalendarState {

    private long value;

    public SimLifeCalendarState() {
        value = 0;
    }

    public SimLifeCalendarState(long value) {
        this.value = value;
    }

    public SimLifeCalendarState(SimLifeCalendarState copy) {
        value = copy.value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public SimLifeCalendar toSimLifeCalendar() {
        return new SimLifeCalendar(value);
    }

    @Override
    public String toString() {
        return "SimLifeCalendarState [value=" + value + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (value ^ (value >>> 32));
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
        SimLifeCalendarState other = (SimLifeCalendarState) obj;
        if (value != other.value)
            return false;
        return true;
    }

}
