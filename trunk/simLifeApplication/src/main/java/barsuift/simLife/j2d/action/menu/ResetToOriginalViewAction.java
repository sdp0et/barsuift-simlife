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
import barsuift.simLife.j2d.menu.Mnemonics;
import barsuift.simLife.j3d.terrain.Navigator;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;

public class ResetToOriginalViewAction extends AbstractAction implements Subscriber {

    private static final long serialVersionUID = -348387892154292590L;

    private Navigator navigator;

    public ResetToOriginalViewAction(Application application) {
        super();
        application.addSubscriber(this);
        putValue(NAME, "Reset to original view");
        putValue(SHORT_DESCRIPTION, "Reset the view angle and position to its original state");
        putValue(MNEMONIC_KEY, Mnemonics.WINDOW_RESET_TO_ORIGINAL_VIEW);
        // no accelerator
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        navigator.resetToOriginalPosition();
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        if (arg == ApplicationUpdateCode.OPEN || arg == ApplicationUpdateCode.NEW_EMPTY
                || arg == ApplicationUpdateCode.NEW_RANDOM) {
            setEnabled(true);
            this.navigator = ((Application) publisher).getUniverseContext().getUniverseContext3D().getNavigator();
        }

    }

}
