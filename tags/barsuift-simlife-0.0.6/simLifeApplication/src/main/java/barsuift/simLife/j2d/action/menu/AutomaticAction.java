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

import barsuift.simLife.Automatable;

public class AutomaticAction extends AbstractAction {

    private static final long serialVersionUID = 588787634109419768L;

    private Automatable automatable;

    private boolean automatic;

    public AutomaticAction(Automatable automatable) {
        super();
        this.automatable = automatable;
        automatic = automatable == null ? false : automatable.isAutomatic();
        updateState(automatic);
        setEnabled(true);
    }

    private void updateState(boolean automatic) {
        this.automatic = automatic;
        if (automatic) {
            // mode is now automatic, so next action will be to switch to manual mode
            putValue(SELECTED_KEY, true);
            putValue(SHORT_DESCRIPTION, "Switch to manual mode");
        } else {
            // mode is now manual, so next action will be to switch to automatic
            putValue(SELECTED_KEY, false);
            putValue(SHORT_DESCRIPTION, "Switch to automatic mode");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // switch the automatic flag : if it is currently automatic, then the action is to switch to manual
        updateState(!automatic);
        automatable.setAutomatic(automatic);
    }

}
