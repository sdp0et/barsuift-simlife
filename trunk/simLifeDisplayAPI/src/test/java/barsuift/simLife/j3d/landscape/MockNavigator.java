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
package barsuift.simLife.j3d.landscape;

import barsuift.simLife.j3d.landscape.NavigationMode;
import barsuift.simLife.j3d.landscape.Navigator;


public class MockNavigator implements Navigator {

    private int nbResetToOriginalPositionCalled;

    private int nbResetToNominalViewAngleCalled;

    private NavigationMode navigationMode;

    public MockNavigator() {
        reset();
    }

    public void reset() {
        this.nbResetToOriginalPositionCalled = 0;
        this.nbResetToNominalViewAngleCalled = 0;
        this.navigationMode = NavigationMode.DEFAULT;
    }

    @Override
    public void resetToOriginalPosition() {
        nbResetToOriginalPositionCalled++;
    }

    public int getNbResetToOriginalPositionCalled() {
        return nbResetToOriginalPositionCalled;
    }

    @Override
    public void resetToNominalViewAngle() {
        nbResetToNominalViewAngleCalled++;
    }

    public int getNbResetToNominalViewAngleCalled() {
        return nbResetToNominalViewAngleCalled;
    }

    @Override
    public NavigationMode getNavigationMode() {
        return navigationMode;
    }

    @Override
    public void setNavigationMode(NavigationMode navigationMode) {
        this.navigationMode = navigationMode;
    }

}
