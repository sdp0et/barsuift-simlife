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

    public static final int SPEED_NORMAL = KeyEvent.VK_1;

    public static final int SPEED_FAST = KeyEvent.VK_2;

    public static final int SPEED_VERY_FAST = KeyEvent.VK_3;


    /* File menu */

    public static final int FILE = KeyEvent.VK_F;

    public static final int FILE_SAVE_AS = KeyEvent.VK_A;

    public static final int FILE_NEW_EMPTY = KeyEvent.VK_N;

    public static final int FILE_OPEN = KeyEvent.VK_O;

    public static final int FILE_RANDOM = KeyEvent.VK_R;

    public static final int FILE_SAVE = KeyEvent.VK_S;



    /* View menu */

    public static final int WINDOW = KeyEvent.VK_W;

    public static final int WINDOW_FPS = KeyEvent.VK_F;

    public static final int WINDOW_AXIS = KeyEvent.VK_A;

    public static final int WINDOW_RESET_TO_ORIGINAL_VIEW = KeyEvent.VK_O;

    public static final int WINDOW_RESET_TO_NOMINAL_VIEW_ANGLE = KeyEvent.VK_N;



    /* Help menu */

    public static final int HELP = KeyEvent.VK_H;

    public static final int HELP_NAVIGATION = KeyEvent.VK_N;

}
