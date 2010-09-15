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

public class FpsAction extends AbstractAction implements Observer {

    private static final long serialVersionUID = 8709944906687074411L;

    private UniverseContext universeContext;

    private boolean fpsShowing;

    public FpsAction(Application application) {
        super();
        this.universeContext = application.getUniverseContext();
        application.addObserver(this);
        fpsShowing = universeContext == null ? false : universeContext.isFpsShowing();
        putValue(MNEMONIC_KEY, Mnemonics.WINDOW_FPS);
        putValue(ACCELERATOR_KEY, Accelerators.FPS);
        updateState(fpsShowing);
        setEnabled(false);
    }

    private void updateState(boolean fpsShowing) {
        this.fpsShowing = fpsShowing;
        if (fpsShowing) {
            // fps is now showing, so next action will be to hide it
            putValue(NAME, "Do not show FPS");
            putValue(SHORT_DESCRIPTION, "Hide the FPS (Frame Per Second)");
        } else {
            // fps is now hidden, so next action will be to show it
            putValue(NAME, "Show FPS");
            putValue(SHORT_DESCRIPTION, "Show the FPS (Frame Per Second)");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // switch the fpsShowing flag : if it is currently displayed, then the action is to hide it
        updateState(!fpsShowing);
        universeContext.setFpsShowing(fpsShowing);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == ApplicationUpdateCode.OPEN || arg == ApplicationUpdateCode.NEW_EMPTY
                || arg == ApplicationUpdateCode.NEW_RANDOM) {
            setEnabled(true);
            this.universeContext = ((Application) o).getUniverseContext();
            updateState(((Application) o).getUniverseContext().isFpsShowing());
        }
    }

}
