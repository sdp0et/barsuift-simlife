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
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import barsuift.simLife.j2d.menu.Mnemonics;

public class HelpNavigationAction extends AbstractAction {

    private static final long serialVersionUID = 7758429822275467527L;

    private final JFrame parentFrame;

    public HelpNavigationAction(JFrame parentFrame) {
        super();
        this.parentFrame = parentFrame;
        putValue(NAME, "Navigation help");
        putValue(SHORT_DESCRIPTION, "Display the navigation memento");
        putValue(MNEMONIC_KEY, Mnemonics.HELP_NAVIGATION);
        // no accelerator
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String navigationMessage = "Here are the navigation keys :\n\n";
        navigationMessage += "Up : Move forward\n";
        navigationMessage += "Down : Move backward\n";
        navigationMessage += "\n";
        navigationMessage += "Left : Rotate left\n";
        navigationMessage += "Right : Rotate right\n";
        navigationMessage += "\n";
        navigationMessage += "Alt + Left : Translate left\n";
        navigationMessage += "Alt + Right : Translate right\n";
        navigationMessage += "\n";
        navigationMessage += "PgUp : Rotate down\n";
        navigationMessage += "PgDown : Rotate up\n";
        navigationMessage += "\n";
        navigationMessage += "Alt + PgUp : Translate up\n";
        navigationMessage += "Alt + PdDown : Translate down\n";
        JOptionPane.showMessageDialog(parentFrame, navigationMessage, "Navigation Help",
                JOptionPane.INFORMATION_MESSAGE);
    }

}
