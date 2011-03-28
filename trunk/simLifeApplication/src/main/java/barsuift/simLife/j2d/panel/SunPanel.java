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

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import barsuift.simLife.j3d.environment.Sun3D;


public class SunPanel extends JPanel {

    private static final long serialVersionUID = -6102868842517781193L;

    public SunPanel(Sun3D sun3D) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(new EarthRevolutionPanel(sun3D));
        add(new EarthRotationPanel(sun3D));
        add(new SunBrightnessPanel(sun3D));
        add(new SunColorPanel(sun3D));
    }

}
