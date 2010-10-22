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

import barsuift.simLife.Persistent;
import barsuift.simLife.process.DateUpdater;
import barsuift.simLife.process.Synchronizer;

public class DateHandler implements Persistent<DateHandlerState> {

    private DateHandlerState state;

    private SimLifeDate date;

    private DateUpdater dateUpdater;

    public DateHandler(DateHandlerState state, Synchronizer synchronizer) {
        this.state = state;
        this.date = new SimLifeDate(state.getDate());
        this.dateUpdater = new DateUpdater(date);
        synchronizer.schedule(dateUpdater);

    }

    public SimLifeDate getDate() {
        return date;
    }

    @Override
    public DateHandlerState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        date.synchronize();
    }

}
