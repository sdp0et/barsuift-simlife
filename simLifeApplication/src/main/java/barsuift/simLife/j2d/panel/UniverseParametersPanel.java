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

import barsuift.simLife.PlanetParameters;
import barsuift.simLife.j2d.ParametersDependent;
import barsuift.simLife.landscape.LandscapeParameters;


public class UniverseParametersPanel extends JPanel implements ParametersDependent {

    private static final long serialVersionUID = -7881514466076103277L;

    private final LandscapeParametersPanel landscapePanel;

    private final PlanetParametersPanel planetPanel;

    public UniverseParametersPanel(PlanetParameters planetParameters, LandscapeParameters landscapeParameters) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


        planetPanel = new PlanetParametersPanel(planetParameters);
        add(planetPanel);

        add(Box.createRigidArea(new Dimension(0, 20)));

        landscapePanel = new LandscapeParametersPanel(landscapeParameters);
        add(landscapePanel);
    }

    @Override
    public void readFromParameters() {
        planetPanel.readFromParameters();
        landscapePanel.readFromParameters();
    }

    @Override
    public void writeIntoParameters() {
        planetPanel.writeIntoParameters();
        landscapePanel.writeIntoParameters();
    }

}
