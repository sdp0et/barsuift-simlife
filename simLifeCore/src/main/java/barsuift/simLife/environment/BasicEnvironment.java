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

public class BasicEnvironment implements Environment {

    private Sun sun;

    /**
     * Creates the environement with given state
     * 
     * @param state the environment state
     * @throws IllegalArgumentException if the given sun state is null
     */
    public BasicEnvironment(EnvironmentState state) {
        if (state == null) {
            throw new IllegalArgumentException("Null environment state");
        }
        sun = new BasicSun(state.getSunState());
    }

    public Sun getSun() {
        return sun;
    }

    public EnvironmentState getState() {
        return new EnvironmentState(sun.getState());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sun == null) ? 0 : sun.hashCode());
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
        BasicEnvironment other = (BasicEnvironment) obj;
        if (sun == null) {
            if (other.sun != null)
                return false;
        } else
            if (!sun.equals(other.sun))
                return false;
        return true;
    }

    /**
     * Return a String representation of the Environment, in the form
     * 
     * <pre>
     * Environment [
     *    sun=Sun [luminosity=xx.00%]
     * ]
     * </pre>
     * 
     * @return
     */
    @Override
    public String toString() {
        return "Environment [\n\tsun=" + sun + "\n]";
    }

}
