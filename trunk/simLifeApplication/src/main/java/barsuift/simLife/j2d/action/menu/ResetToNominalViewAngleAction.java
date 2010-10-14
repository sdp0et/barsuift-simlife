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
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;
import barsuift.simLife.universe.UniverseContext;

public class ResetToNominalViewAngleAction extends AbstractAction implements Subscriber {

    private static final long serialVersionUID = 1291567621047871503L;

    private UniverseContext universeContext;

    public ResetToNominalViewAngleAction(Application application) {
        super();
        application.addSubscriber(this);
        this.universeContext = application.getUniverseContext();
        putValue(NAME, "Reset to nominal view angle");
        putValue(SHORT_DESCRIPTION, "Reset the view angle to its nominal state");
        putValue(MNEMONIC_KEY, Mnemonics.WINDOW_RESET_TO_NOMINAL_VIEW_ANGLE);
        // no accelerator
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        universeContext.resetToNominalAngleOfView();
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        if (arg == ApplicationUpdateCode.OPEN || arg == ApplicationUpdateCode.NEW_EMPTY
                || arg == ApplicationUpdateCode.NEW_RANDOM) {
            setEnabled(true);
            this.universeContext = ((Application) publisher).getUniverseContext();
        }

    }

}
