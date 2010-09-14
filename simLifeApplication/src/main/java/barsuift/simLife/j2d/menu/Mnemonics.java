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

import java.awt.event.KeyEvent;



public class Mnemonics {

    private Mnemonics() {
        // private constructor to enforce static access
    }

    /*
     * Mnemonics should be unique at a given point of the navigation. They are ordered by menu.
     */


    /* File menu */

    public static final int FILE = KeyEvent.VK_F;

    public static final int FILE_SAVE_AS = KeyEvent.VK_A;

    public static final int FILE_NEW_EMPTY = KeyEvent.VK_N;

    public static final int FILE_OPEN = KeyEvent.VK_O;

    public static final int FILE_RANDOM = KeyEvent.VK_R;

    public static final int FILE_SAVE = KeyEvent.VK_S;



    /* View menu */

    public static final int WINDOW = KeyEvent.VK_W;

    public static final int VIEW_FPS = KeyEvent.VK_F;

    public static final int VIEW_AXIS = KeyEvent.VK_A;

}
