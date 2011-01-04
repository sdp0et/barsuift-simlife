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

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import barsuift.simLife.universe.AllParameters;


public class UniverseParametersPanel extends JPanel {

    private static final long serialVersionUID = -7881514466076103277L;

    private final LandscapeParametersPanel landscapePanel;

    private final WorldParametersPanel worldPanel;

    public UniverseParametersPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


        worldPanel = new WorldParametersPanel();
        add(worldPanel);

        add(Box.createRigidArea(new Dimension(0, 20)));

        landscapePanel = new LandscapeParametersPanel();
        add(landscapePanel);
    }

    public AllParameters getParameters() {
        return new AllParameters(worldPanel.getCommonParameters(), landscapePanel.getLandscapeParameters());
    }

}
