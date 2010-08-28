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
package barsuift.simLife.j2d.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import barsuift.simLife.Application;

// TODO 001 unit test
public class FpsAction extends AbstractAction {

    private static final long serialVersionUID = 8709944906687074411L;

    private final Application application;

    private boolean showFps;

    public FpsAction(Application application) {
        super();
        this.application = application;
        showFps = false;
        putValue(Action.NAME, "Show FPS");
        putValue(SHORT_DESCRIPTION, "Show the FPS (Frame Per Second)");
        putValue(MNEMONIC_KEY, KeyEvent.VK_F);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
    }

    private void showFps() {
        showFps = true;
        putValue(Action.NAME, "Do not show FPS");
        putValue(SHORT_DESCRIPTION, "Hide the FPS (Frame Per Second)");
        application.showFps(showFps);
    }

    private void doNotShowFps() {
        showFps = false;
        putValue(Action.NAME, "Show FPS");
        putValue(SHORT_DESCRIPTION, "Show the FPS (Frame Per Second)");
        application.showFps(showFps);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (showFps) {
            // fps are already shown, so we hide it
            doNotShowFps();
        } else {
            // fps is not yet shown, so we show it
            showFps();
        }
    }

}
