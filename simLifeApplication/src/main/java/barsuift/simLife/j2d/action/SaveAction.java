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
import barsuift.simLife.universe.SaveException;


public class SaveAction extends AbstractAction implements Observer {

    private static final long serialVersionUID = 8223229157394283604L;

    private final Application application;

    public SaveAction(Application application) {
        super();
        this.application = application;
        application.addObserver(this);
        putValue(NAME, "Save");
        putValue(SHORT_DESCRIPTION, "Save the current universe");
        putValue(MNEMONIC_KEY, Mnemonics.FILE_SAVE);
        putValue(ACCELERATOR_KEY, Accelerators.SAVE);
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            application.saveUniverse();
        } catch (SaveException se) {
            System.out.println("Unable to save the universe to the current save file because " + se.getMessage());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == ApplicationUpdateCode.SAVE_AS || arg == ApplicationUpdateCode.OPEN) {
            setEnabled(true);
        }
        if (arg == ApplicationUpdateCode.NEW_EMPTY || arg == ApplicationUpdateCode.NEW_RANDOM) {
            setEnabled(false);
        }
    }

}
