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

/**
 * A split task is a task split in increments. The {@code stepSize} parameter allow to run more than one increment in a
 * row. Note that executing more than one increment in a row does NOT mean executing them one after the other, but
 * executing the whole increment range in one action.
 */
public interface SplitConditionalTask extends ConditionalTask {

    public void setStepSize(int stepSize);

    public void executeSplitConditionalStep(int stepSize);

}