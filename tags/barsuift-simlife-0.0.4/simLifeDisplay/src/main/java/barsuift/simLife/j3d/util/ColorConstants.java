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
package barsuift.simLife.j3d.util;

import javax.vecmath.Color3f;

public final class ColorConstants {

    private ColorConstants() {
        // private constructor to enforce static access
    }

    public static final Color3f red = new Color3f(1.0f, 0.0f, 0.0f);

    public static final Color3f green = new Color3f(0.0f, 1.0f, 0.0f);

    public static final Color3f blue = new Color3f(0.0f, 0.0f, 1.0f);

    public static final Color3f yellow = new Color3f(1.0f, 1.0f, 0.0f);

    public static final Color3f cyan = new Color3f(0.0f, 1.0f, 1.0f);

    public static final Color3f magenta = new Color3f(1.0f, 0.0f, 1.0f);

    public static final Color3f white = new Color3f(1.0f, 1.0f, 1.0f);

    public static final Color3f black = new Color3f(0.0f, 0.0f, 0.0f);

    public static final Color3f grey = new Color3f(0.5f, 0.5f, 0.5f);

    public static final Color3f brown = new Color3f(0.57f, 0.36f, 0.15f);

    public static final Color3f brownYellow = new Color3f(0.78f, 0.68f, 0.15f);

}
