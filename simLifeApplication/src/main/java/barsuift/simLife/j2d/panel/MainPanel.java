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

import barsuift.simLife.universe.UniverseContext;


public class MainPanel extends JPanel {

    private static final long serialVersionUID = -3396495972510779750L;

    /**
     * Creates the main panel, with box layout, ordered along the LINE axis
     * 
     * @param universe the universe
     */
    public MainPanel(UniverseContext universeContext) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));

        JPanel environmentPanel = new EnvironmentPanel(universeContext.getUniverse().getEnvironment());
        rightPanel.add(environmentPanel);

        JPanel controllerPanel = new TimeControllerPanel(universeContext.getUniverse());
        rightPanel.add(controllerPanel);

        add(rightPanel);

        Universe3DPanel universe3DPanel = new Universe3DPanel(universeContext.getCanvas3D());
        add(universe3DPanel);
    }

}
