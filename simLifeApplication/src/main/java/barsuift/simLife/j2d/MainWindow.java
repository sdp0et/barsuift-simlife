/**
 * barsuift-simlife is a life simulator programm
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
package barsuift.simLife.j2d;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import barsuift.simLife.Application;
import barsuift.simLife.j2d.menu.MenuFactory;
import barsuift.simLife.j2d.panel.MainPanel;
import barsuift.simLife.j3d.universe.UniverseContext;


public class MainWindow extends JFrame {

    private static final long serialVersionUID = 2791518087247151942L;

    private MainPanel mainPanel;

    public MainWindow(Application application) {
        super("SimLife");
        int width = 768;
        int height = 512;
        setBounds(0, 0, width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MenuFactory menuFactory = new MenuFactory();
        JMenuBar menuBar = menuFactory.createMenuBar(application);
        setJMenuBar(menuBar);
    }

    /**
     * Destroy the current panel (if any) and creates a new one from the given universe.
     * 
     * @param universe the new universe to display
     */
    public void changeUniverse(UniverseContext universeContext) {
        mainPanel = null;
        mainPanel = new MainPanel(universeContext);
        setContentPane(mainPanel);
        validate();
    }

}
