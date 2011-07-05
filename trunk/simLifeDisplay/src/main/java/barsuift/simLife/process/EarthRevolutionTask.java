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

import barsuift.simLife.Automatable;
import barsuift.simLife.j3d.environment.Sun3D;
import barsuift.simLife.time.SimLifeDate;

public class EarthRevolutionTask extends AbstractSplitConditionalTask implements Automatable {

    protected final static double REVOLUTION_ANGLE_PER_SECOND = Math.PI * 2
            / (SimLifeDate.SECOND_PER_MINUTE * SimLifeDate.MINUTE_PER_DAY * SimLifeDate.DAY_PER_YEAR);

    private Sun3D sun3D;

    private SimLifeDate date;

    private boolean automatic;


    public EarthRevolutionTask(SplitConditionalTaskState state) {
        super(state);
    }

    public void init(Sun3D sun3D, SimLifeDate date) {
        this.sun3D = sun3D;
        this.date = date;
        this.automatic = sun3D.isEarthRevolutionTaskAutomatic();
    }

    @Override
    public void executeSplitConditionalStep(int stepSize) {
        if (automatic) {
            updateSunPosition();
        }
    }

    private void updateSunPosition() {
        int daysForYear = (date.getMonthOfYear().getIndex() - 1) * SimLifeDate.DAY_PER_MONTH + date.getDayOfMonth() - 1;
        int minutesForYear = daysForYear * SimLifeDate.MINUTE_PER_DAY + date.getMinuteOfDay();
        int secondsForYear = minutesForYear * SimLifeDate.SECOND_PER_MINUTE + date.getSecondOfMinute();
        sun3D.setEarthRevolution((float) (secondsForYear * REVOLUTION_ANGLE_PER_SECOND));
    }

    @Override
    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
        if (automatic) {
            updateSunPosition();
        }
    }

    @Override
    public boolean isAutomatic() {
        return automatic;
    }

}
