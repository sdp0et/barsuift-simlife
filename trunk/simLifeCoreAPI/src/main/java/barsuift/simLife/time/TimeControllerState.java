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
import barsuift.simLife.process.SynchronizerState;

@XmlRootElement
public class TimeControllerState implements State {

    private DateHandlerState dateHandler;

    private SynchronizerState synchronizer;

    public TimeControllerState() {
        super();
        this.dateHandler = new DateHandlerState();
        this.synchronizer = new SynchronizerState();
    }

    public TimeControllerState(DateHandlerState dateHandler, SynchronizerState synchronizer) {
        super();
        this.dateHandler = dateHandler;
        this.synchronizer = synchronizer;
    }

    public DateHandlerState getDateHandler() {
        return dateHandler;
    }

    public void setDateHandler(DateHandlerState dateHandler) {
        this.dateHandler = dateHandler;
    }

    public SynchronizerState getSynchronizer() {
        return synchronizer;
    }

    public void setSynchronizer(SynchronizerState synchronizer) {
        this.synchronizer = synchronizer;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dateHandler == null) ? 0 : dateHandler.hashCode());
        result = prime * result + ((synchronizer == null) ? 0 : synchronizer.hashCode());
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
        TimeControllerState other = (TimeControllerState) obj;
        if (dateHandler == null) {
            if (other.dateHandler != null)
                return false;
        } else
            if (!dateHandler.equals(other.dateHandler))
                return false;
        if (synchronizer == null) {
            if (other.synchronizer != null)
                return false;
        } else
            if (!synchronizer.equals(other.synchronizer))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "TimeControllerState [dateHandler=" + dateHandler + ", synchronizer=" + synchronizer + "]";
    }

}
