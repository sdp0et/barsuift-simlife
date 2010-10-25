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
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;
import barsuift.simLife.process.Synchronizer;
import barsuift.simLife.universe.SaveException;


public class SaveAsAction extends AbstractAction implements Subscriber {

    private static final long serialVersionUID = -2391532464769897167L;

    private final Application application;

    public SaveAsAction(Application application) {
        super();
        this.application = application;
        application.addSubscriber(this);
        putValue(NAME, "Save As ...");
        putValue(SHORT_DESCRIPTION, "Save the current universe in a new file");
        putValue(MNEMONIC_KEY, Mnemonics.FILE_SAVE_AS);
        putValue(ACCELERATOR_KEY, Accelerators.SAVE_AS);
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean wasRunning = stopApp();
        saveAs();
        if (wasRunning) {
            Synchronizer synchronizer = application.getUniverseContext().getUniverse().getSynchronizer();
            synchronizer.start();
        }
    }

    /**
     * If the application is running, stop it and return true. Else, simply return false;
     * 
     * @return true if the application was running, false otherwise
     */
    private boolean stopApp() {
        Synchronizer synchronizer = application.getUniverseContext().getUniverse().getSynchronizer();
        if (synchronizer.isRunning()) {
            synchronizer.stop();
            return true;
        }
        return false;
    }

    private void saveAs() {
        try {
            application.saveUniverseAs();
        } catch (SaveException se) {
            System.out.println("Unable to save the universe to given file because " + se.getMessage());
        }
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        if (arg == ApplicationUpdateCode.NEW_EMPTY || arg == ApplicationUpdateCode.NEW_RANDOM
                || arg == ApplicationUpdateCode.OPEN) {
            setEnabled(true);
        }
    }

}
