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
package barsuift.simLife.j2d;

/**
 * This interface is for all object which depends on parameters. It allows to read the values from the parameters to
 * update its own state, and to write the values back to the parameters object.
 * 
 */
public interface ParametersDependent {

    /**
     * Read the values from the parameter object and update own state.
     */
    public void readFromParameters();

    /**
     * Write back the values into the parameters object.
     */
    public void writeIntoParameters();

}
