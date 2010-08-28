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

import java.text.MessageFormat;
import java.util.Observable;

public class TimeCounter extends Observable implements Comparable<TimeCounter> {

    private static final MessageFormat TO_STRING = new MessageFormat(
            "{0} days {1,number,00}:{2,number,00}:{3,number,00}");

    private int seconds;

    /**
     * Default constructor
     */
    public TimeCounter(TimeCounterState state) {
        if (state.getSeconds() < 0) {
            throw new IllegalArgumentException("The seconds are negative");
        }
        this.seconds = state.getSeconds();
    }

    /**
     * Increment the counter, and notify observers
     */
    public void increment() {
        seconds++;
        setChanged();
        notifyObservers();
    }

    /**
     * Get the number of seconds
     * 
     * @return a number in [0-59]
     */
    public int getSeconds() {
        return (int) seconds % 60;
    }

    /**
     * Get the number of minutes
     * 
     * @return a number in [0-59]
     */
    public int getMinutes() {
        return (seconds % 3600) / 60;
    }

    /**
     * Get the number of hours
     * 
     * @return a number in [0-23]
     */
    public int getHours() {
        return (seconds % 86400) / 3600;
    }

    /**
     * Get the number of days
     * 
     * @return a number
     */
    public int getDays() {
        return seconds / 86400;
    }

    public TimeCounterState getState() {
        return new TimeCounterState(seconds);
    }

    /**
     * Return a representation of this counter in the form "<code>d days hh:mm:ss</code>"
     */
    @Override
    public String toString() {
        return TO_STRING.format(new Integer[] { getDays(), getHours(), getMinutes(), getSeconds() });
    }

    @Override
    public int compareTo(TimeCounter o) {
        return (seconds - o.seconds);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + seconds;
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
        TimeCounter other = (TimeCounter) obj;
        if (seconds != other.seconds)
            return false;
        return true;
    }

}
