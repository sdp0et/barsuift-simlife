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

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import junit.framework.Assert;
import barsuift.simLife.IObservable;


public class ObservableTestHelper extends Assert {

    private int nbUpdated;

    private List<Object> updateObjects;

    public ObservableTestHelper() {
        reset();
    }

    public void reset() {
        nbUpdated = 0;
        updateObjects = new ArrayList<Object>();
    }

    public void addObserver(final Observable obs) {
        obs.addObserver(new Observer() {

            public void update(Observable o, Object arg) {
                assertEquals(obs, o);
                nbUpdated++;
                updateObjects.add(arg);
            }

        });
    }

    public void addIObserver(final IObservable obs) {
        obs.addObserver(new Observer() {

            public void update(Observable o, Object arg) {
                assertEquals(obs, o);
                nbUpdated++;
                updateObjects.add(arg);
            }

        });
    }

    public int nbUpdated() {
        return nbUpdated;
    }

    public List<Object> getUpdateObjects() {
        return updateObjects;
    }

}
