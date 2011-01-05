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
 * This interface is for all parameters panels. It allows to read the values from the parameters (to update the panel
 * accordingly), and to write the values back to the parameter object.
 * 
 */
public interface ParametersPanel {

    /**
     * Read the values from the parameter object and update the status of the panel.
     */
    public void readFromParameters();

    /**
     * Write back the values set in the panel into the parameters object.
     */
    public void writeIntoParameters();

}
