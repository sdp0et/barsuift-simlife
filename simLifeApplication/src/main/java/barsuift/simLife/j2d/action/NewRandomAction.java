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

import javax.swing.AbstractAction;

import barsuift.simLife.Application;
import barsuift.simLife.j2d.menu.Accelerators;
import barsuift.simLife.j2d.menu.Mnemonics;


public class NewRandomAction extends AbstractAction {

    private static final long serialVersionUID = -7620926200302148499L;

    private final Application application;

    public NewRandomAction(Application application) {
        super();
        this.application = application;
        putValue(NAME, "New (random)");
        putValue(SHORT_DESCRIPTION, "Create a new random universe");
        putValue(MNEMONIC_KEY, Mnemonics.FILE_RANDOM);
        putValue(ACCELERATOR_KEY, Accelerators.RANDOM);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        application.createRandomUniverse();
    }

}
