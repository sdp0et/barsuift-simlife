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

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import barsuift.simLife.environment.Environment;


public class EnvironmentPanel extends JPanel {

    private static final long serialVersionUID = 2499187337226355527L;

    // private final Environment environment;

    /**
     * Creates the environment panel, with box layout, ordered along the PAGE axis
     * 
     * @param environment the universe environment
     */
    public EnvironmentPanel(Environment environment) {
        super();
        // this.environment = environment;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(new SunPanel(environment.getSun()));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        Border blacklineBorder = BorderFactory.createLineBorder(Color.black);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(blacklineBorder, "Environment");
        setBorder(titledBorder);

    }

}
