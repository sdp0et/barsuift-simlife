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
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import barsuift.simLife.Application;
import barsuift.simLife.j2d.menu.Accelerators;
import barsuift.simLife.j2d.menu.Mnemonics;
import barsuift.simLife.process.MainSynchronizer;
import barsuift.simLife.universe.OpenException;
import barsuift.simLife.universe.UniverseContext;


public class OpenAction extends AbstractAction {

    private static final Logger logger = Logger.getLogger(OpenAction.class.getName());

    private static final long serialVersionUID = -7706268023944038274L;

    private final Application application;

    public OpenAction(Application application) {
        super();
        this.application = application;
        putValue(NAME, "Open");
        putValue(SHORT_DESCRIPTION, "Open another universe");
        putValue(MNEMONIC_KEY, Mnemonics.FILE_OPEN);
        putValue(ACCELERATOR_KEY, Accelerators.OPEN);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        stopApp();
        open();
    }

    private void stopApp() {
        UniverseContext universeContext = application.getUniverseContext();
        if (universeContext != null) {
            MainSynchronizer synchronizer = universeContext.getSynchronizer();
            if (synchronizer.isRunning()) {
                synchronizer.stop();
            }
        }
    }

    private void open() {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                application.openUniverse(file);
            } catch (OpenException oe) {
                logger.log(Level.SEVERE, "Unable to open the given file : " + file.getAbsolutePath(), oe);
            }
        }
    }

}
