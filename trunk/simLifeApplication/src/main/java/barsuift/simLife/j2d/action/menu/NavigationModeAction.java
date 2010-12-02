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
package barsuift.simLife.j2d.action.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import barsuift.simLife.Application;
import barsuift.simLife.ApplicationUpdateCode;
import barsuift.simLife.j2d.menu.Accelerators;
import barsuift.simLife.j2d.menu.Mnemonics;
import barsuift.simLife.j3d.terrain.NavigationMode;
import barsuift.simLife.j3d.terrain.Navigator;
import barsuift.simLife.j3d.universe.UniverseContext3D;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;
import barsuift.simLife.universe.UniverseContext;

public class NavigationModeAction extends AbstractAction implements Subscriber {

    private static final long serialVersionUID = 4735542751147315362L;

    private Navigator navigator;

    private NavigationMode navigationMode;

    public NavigationModeAction(Application application) {
        super();
        UniverseContext universeContext = application.getUniverseContext();
        UniverseContext3D universeContext3D = universeContext == null ? null : universeContext.getUniverseContext3D();
        this.navigator = universeContext3D == null ? null : universeContext3D.getNavigator();
        application.addSubscriber(this);
        navigationMode = navigator == null ? NavigationMode.DEFAULT : navigator.getNavigationMode();
        putValue(MNEMONIC_KEY, Mnemonics.WINDOW_NAVIGATION_MODE);
        putValue(ACCELERATOR_KEY, Accelerators.NAVIGATION_MODE);
        updateState(navigationMode);
        setEnabled(false);
    }

    private void updateState(NavigationMode navigationMode) {
        this.navigationMode = navigationMode;
        if (navigationMode == NavigationMode.WALK) {
            // current navigation mode is WALK, so next action will be to switch to FLY mode
            putValue(NAME, "Switch to FLY mode");
            putValue(SHORT_DESCRIPTION, "Change the current navigation mode to FLY mode");
        } else {
            // current navigation mode is FLY, so next action will be to switch to WALK mode
            putValue(NAME, "Switch to WALK mode");
            putValue(SHORT_DESCRIPTION, "Change the current navigation mode to WALK mode");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // switch the navigation mode : if it is currently WALK, then change it to FLY
        switch (navigationMode) {
        case WALK:
            updateState(NavigationMode.FLY);
            break;

        case FLY:
            updateState(NavigationMode.WALK);
            break;
        }
        navigator.setNavigationMode(navigationMode);
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        if (arg == ApplicationUpdateCode.OPEN || arg == ApplicationUpdateCode.NEW_EMPTY
                || arg == ApplicationUpdateCode.NEW_RANDOM) {
            setEnabled(true);
            navigator = ((Application) publisher).getUniverseContext().getUniverseContext3D().getNavigator();
            updateState(navigator.getNavigationMode());
        }
    }

}
