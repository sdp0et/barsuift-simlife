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
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import barsuift.simLife.Application;
import barsuift.simLife.ApplicationUpdateCode;
import barsuift.simLife.j2d.menu.Accelerators;
import barsuift.simLife.j2d.menu.Mnemonics;
import barsuift.simLife.universe.UniverseContext;

public class AxisAction extends AbstractAction implements Observer {

    private static final long serialVersionUID = 649806431886707462L;

    private UniverseContext universeContext;

    private boolean axisShowing;

    public AxisAction(Application application) {
        super();
        this.universeContext = application.getUniverseContext();
        application.addObserver(this);
        axisShowing = universeContext == null ? false : universeContext.isAxisShowing();
        putValue(MNEMONIC_KEY, Mnemonics.WINDOW_AXIS);
        putValue(ACCELERATOR_KEY, Accelerators.AXIS);
        updateState(axisShowing);
        setEnabled(false);
    }

    private void updateState(boolean axisShowing) {
        this.axisShowing = axisShowing;
        if (axisShowing) {
            // axis are now showing, so next action will be to hide them
            putValue(NAME, "Do not show axis");
            putValue(SHORT_DESCRIPTION, "Hide the axis");
        } else {
            // axis are now hidden, so next action will be to show them
            putValue(NAME, "Show axis");
            putValue(SHORT_DESCRIPTION, "Show the axis");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // switch the axisShowing flag : if it is currently displayed, then the action is to hide it
        updateState(!axisShowing);
        universeContext.setAxisShowing(axisShowing);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == ApplicationUpdateCode.OPEN || arg == ApplicationUpdateCode.NEW_EMPTY
                || arg == ApplicationUpdateCode.NEW_RANDOM) {
            setEnabled(true);
            this.universeContext = ((Application) o).getUniverseContext();
            updateState(((Application) o).getUniverseContext().isAxisShowing());
        }
    }

}
