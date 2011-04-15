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
package barsuift.simLife.process;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;

@XmlRootElement
public class MainSynchronizerState implements State {

    private SynchronizerSlowState synchronizerSlowState;

    private SynchronizerFastState synchronizerFastState;

    public MainSynchronizerState() {
        super();
        this.synchronizerSlowState = new SynchronizerSlowState();
        this.synchronizerFastState = new SynchronizerFastState();
    }

    public MainSynchronizerState(SynchronizerSlowState synchronizerSlowState, SynchronizerFastState synchronizerFastState) {
        super();
        this.synchronizerSlowState = synchronizerSlowState;
        this.synchronizerFastState = synchronizerFastState;
    }

    public SynchronizerSlowState getSynchronizerSlowState() {
        return synchronizerSlowState;
    }

    public void setSynchronizerSlowState(SynchronizerSlowState synchronizerSlowState) {
        this.synchronizerSlowState = synchronizerSlowState;
    }

    public SynchronizerFastState getSynchronizerFastState() {
        return synchronizerFastState;
    }

    public void setSynchronizerFastState(SynchronizerFastState synchronizerFastState) {
        this.synchronizerFastState = synchronizerFastState;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((synchronizerFastState == null) ? 0 : synchronizerFastState.hashCode());
        result = prime * result + ((synchronizerSlowState == null) ? 0 : synchronizerSlowState.hashCode());
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
        MainSynchronizerState other = (MainSynchronizerState) obj;
        if (synchronizerFastState == null) {
            if (other.synchronizerFastState != null)
                return false;
        } else
            if (!synchronizerFastState.equals(other.synchronizerFastState))
                return false;
        if (synchronizerSlowState == null) {
            if (other.synchronizerSlowState != null)
                return false;
        } else
            if (!synchronizerSlowState.equals(other.synchronizerSlowState))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "MainSynchronizerState [synchronizerSlowState=" + synchronizerSlowState + ", synchronizerFastState="
                + synchronizerFastState + "]";
    }

}
