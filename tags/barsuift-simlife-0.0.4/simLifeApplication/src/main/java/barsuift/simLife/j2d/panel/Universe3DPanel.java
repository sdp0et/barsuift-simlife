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
package barsuift.simLife.j2d.panel;

import java.awt.BorderLayout;

import javax.media.j3d.Canvas3D;
import javax.swing.JPanel;

import barsuift.simLife.j3d.SimLifeCanvas3D;

public class Universe3DPanel extends JPanel {

    private static final long serialVersionUID = -9023573589569686409L;

    public Universe3DPanel(SimLifeCanvas3D canvas3D) {
        setLayout(new BorderLayout());
        add("Center", (Canvas3D) canvas3D);
    }

}
