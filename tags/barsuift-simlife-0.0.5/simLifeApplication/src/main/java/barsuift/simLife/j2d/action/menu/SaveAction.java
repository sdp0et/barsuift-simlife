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
import barsuift.simLife.process.MainSynchronizer;
import barsuift.simLife.universe.SaveException;


public class SaveAction extends AbstractAction implements Subscriber {

    private static final long serialVersionUID = 8223229157394283604L;

    private final Application application;

    private boolean isAbleToSave;

    public SaveAction(Application application) {
        super();
        this.application = application;
        application.addSubscriber(this);
        isAbleToSave = false;
        putValue(NAME, "Save");
        putValue(SHORT_DESCRIPTION, "Save the current universe");
        putValue(MNEMONIC_KEY, Mnemonics.FILE_SAVE);
        putValue(ACCELERATOR_KEY, Accelerators.SAVE);
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean wasRunning = stopApp();
        saveOrSaveAs();
        if (wasRunning) {
            MainSynchronizer synchronizer = application.getUniverseContext().getSynchronizer();
            synchronizer.start();
        }
    }

    private void saveOrSaveAs() {
        if (isAbleToSave) {
            // "Save" action
            try {
                application.saveUniverse();
            } catch (SaveException se) {
                System.out.println("Unable to save the universe to the current save file because " + se.getMessage());
            }
        } else {
            // "Save as" action
            try {
                application.saveUniverseAs();
            } catch (SaveException se) {
                System.out.println("Unable to save the universe to given file because " + se.getMessage());
            }
        }
    }

    /**
     * If the application is running, stop it and return true. Else, simply return false;
     * 
     * @return true if the application was running, false otherwise
     */
    private boolean stopApp() {
        MainSynchronizer synchronizer = application.getUniverseContext().getSynchronizer();
        if (synchronizer.isRunning()) {
            synchronizer.stop();
            return true;
        }
        return false;
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        if (arg == ApplicationUpdateCode.SAVE_AS || arg == ApplicationUpdateCode.OPEN) {
            setEnabled(true);
            isAbleToSave = true;
            return;
        }
        if (arg == ApplicationUpdateCode.NEW_EMPTY || arg == ApplicationUpdateCode.NEW_RANDOM) {
            setEnabled(true);
            isAbleToSave = false;
            return;
        }
    }

}
