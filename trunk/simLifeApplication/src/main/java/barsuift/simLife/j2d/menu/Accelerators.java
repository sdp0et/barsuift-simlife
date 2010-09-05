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
package barsuift.simLife.j2d.menu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;



public class Accelerators {

    private Accelerators() {
        // private constructor to enforce static access
    }

    // Ctrl + F
    public static final KeyStroke FPS = KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK);

    // Ctrl + N
    public static final KeyStroke NEW_EMPTY = KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK);

    // Ctrl + O
    public static final KeyStroke OPEN = KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK);

    // Ctrl + R
    public static final KeyStroke RANDOM = KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK);

    // Ctrl + S
    public static final KeyStroke SAVE = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK);

    // Ctrl + Shift + S
    public static final KeyStroke SAVE_AS = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.SHIFT_MASK
            | ActionEvent.CTRL_MASK);

}
