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

@XmlRootElement
public class UnfrequentRunnableState extends SynchronizedRunnableState {

    private int delay;

    private int count;

    public UnfrequentRunnableState() {
        super();
        delay = 1;
        count = 0;
    }

    public UnfrequentRunnableState(Class<? extends SynchronizedRunnable> clazz, int delay, int count) {
        super(clazz);
        this.delay = delay;
        this.count = count;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + count;
        result = prime * result + delay;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        UnfrequentRunnableState other = (UnfrequentRunnableState) obj;
        if (count != other.count)
            return false;
        if (delay != other.delay)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UnfrequentRunnableState [delay=" + delay + ", count=" + count + ", super=" + super.toString() + "]";
    }

}
