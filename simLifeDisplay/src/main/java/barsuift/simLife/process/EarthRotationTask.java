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

public class EarthRotationTask extends AbstractSplitConditionalTask implements Automatable {

    protected final static double ROTATION_ANGLE_PER_MS = Math.PI * 2
            / (SimLifeDate.MS_PER_SECOND * SimLifeDate.SECOND_PER_MINUTE * SimLifeDate.MINUTE_PER_DAY);

    private Sun3D sun3D;

    private SimLifeDate date;

    private boolean automatic;


    public EarthRotationTask(SplitConditionalTaskState state) {
        super(state);
    }

    public void init(Sun3D sun3D, SimLifeDate date) {
        this.sun3D = sun3D;
        this.date = date;
        this.automatic = sun3D.isEarthRotationTaskAutomatic();
    }

    @Override
    public void executeSplitConditionalStep(int stepSize) {
        if (automatic) {
            updateSunPosition();
        }
    }

    private void updateSunPosition() {
        int msForDay = (date.getMinuteOfDay() * SimLifeDate.SECOND_PER_MINUTE + date.getSecondOfMinute())
                * SimLifeDate.MS_PER_SECOND + date.getMillisOfSecond();
        sun3D.setEarthRotation((float) (msForDay * ROTATION_ANGLE_PER_MS));
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
