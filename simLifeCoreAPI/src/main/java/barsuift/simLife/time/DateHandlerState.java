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

import barsuift.simLife.State;

@XmlRootElement
public class DateHandlerState implements State {

    private SimLifeDateState date;

    public DateHandlerState() {
        super();
        this.date = new SimLifeDateState();
    }

    public DateHandlerState(SimLifeDateState date) {
        super();
        this.date = date;
    }

    public SimLifeDateState getDate() {
        return date;
    }

    public void setDate(SimLifeDateState date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
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
        DateHandlerState other = (DateHandlerState) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else
            if (!date.equals(other.date))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "DateHandlerState [date=" + date + "]";
    }
}
