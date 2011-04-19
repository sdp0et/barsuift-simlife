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
package barsuift.simLife.j2d.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import barsuift.simLife.process.MainSynchronizer;
import barsuift.simLife.process.Speed;


public class SpeedAction extends AbstractAction {

    private static final long serialVersionUID = -8115155709776072647L;

    private final MainSynchronizer synchronizer;

    public SpeedAction(MainSynchronizer synchronizer, String name, String description, int mnemonic,
            String actionCommand) {
        super();
        this.synchronizer = synchronizer;
        putValue(MNEMONIC_KEY, mnemonic);
        putValue(ACTION_COMMAND_KEY, actionCommand);
        putValue(NAME, name);
        putValue(SHORT_DESCRIPTION, description);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean wasRunning = stopApp();
        setSpeed(e);
        if (wasRunning) {
            synchronizer.start();
        }
    }

    /**
     * If the application is running, stop it and return true. Else, simply return false;
     * 
     * @return true if the application was running, false otherwise
     */
    private boolean stopApp() {
        if (synchronizer.isRunning()) {
            synchronizer.stopAndWait();
            return true;
        }
        return false;
    }

    private void setSpeed(ActionEvent e) {
        String actionCmd = e.getActionCommand();
        synchronizer.setSpeed(Speed.valueOf(actionCmd));
    }

}
