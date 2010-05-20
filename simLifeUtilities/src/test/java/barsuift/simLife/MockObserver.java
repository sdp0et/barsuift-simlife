/**
 * barsuift-simlife is a life simulator programm
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
package barsuift.simLife;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class MockObserver {

    private int updateCounter;

    private List<Observable> observableObjectsObserved;

    private List<Object> arguments;

    public MockObserver() {
        resetObserver();
    }

    public void resetObserver() {
        updateCounter = 0;
        observableObjectsObserved = new ArrayList<Observable>();
        arguments = new ArrayList<Object>();
    }

    public void update(Observable o, Object arg) {
        updateCounter++;
        observableObjectsObserved.add(o);
        arguments.add(arg);
    }

    public List<Object> getArguments() {
        return arguments;
    }

    public List<Observable> getObservableObjectsObserved() {
        return observableObjectsObserved;
    }

    public int getUpdateCounter() {
        return updateCounter;
    }
}
