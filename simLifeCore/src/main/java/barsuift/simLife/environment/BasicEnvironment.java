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

    private final EnvironmentState state;

    private final Sun sun;

    /**
     * Creates the environment with given state
     * 
     * @param state the environment state
     * @throws IllegalArgumentException if the given sun state is null
     */
    public BasicEnvironment(EnvironmentState state) {
        if (state == null) {
            throw new IllegalArgumentException("Null environment state");
        }
        this.state = state;
        this.sun = new BasicSun(state.getSunState());
    }

    public Sun getSun() {
        return sun;
    }

    public EnvironmentState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        sun.synchronize();
    }

}
