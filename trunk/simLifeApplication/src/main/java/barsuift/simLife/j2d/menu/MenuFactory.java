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

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import barsuift.simLife.Application;
import barsuift.simLife.j2d.action.FpsAction;
import barsuift.simLife.j2d.action.NewEmptyAction;
import barsuift.simLife.j2d.action.NewRandomAction;
import barsuift.simLife.j2d.action.OpenAction;
import barsuift.simLife.j2d.action.SaveAction;
import barsuift.simLife.j2d.action.SaveAsAction;

// TODO put mnemonic and accelerator keys in a static class to put them all together
// TODO 001 unit test
public class MenuFactory {

    public JMenuBar createMenuBar(Application application) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu(application));
        menuBar.add(createAdminMenu(application));
        return menuBar;
    }

    private JMenu createFileMenu(Application application) {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        NewEmptyAction newEmptyAction = new NewEmptyAction(application);
        JMenuItem newEmptyItem = new JMenuItem(newEmptyAction);
        fileMenu.add(newEmptyItem);

        NewRandomAction newRandomAction = new NewRandomAction(application);
        JMenuItem newRandomItem = new JMenuItem(newRandomAction);
        fileMenu.add(newRandomItem);

        SaveAction saveAction = new SaveAction(application);
        JMenuItem saveItem = new JMenuItem(saveAction);
        fileMenu.add(saveItem);

        SaveAsAction saveAsAction = new SaveAsAction(application);
        JMenuItem saveAsItem = new JMenuItem(saveAsAction);
        fileMenu.add(saveAsItem);

        OpenAction openAction = new OpenAction(application);
        JMenuItem openItem = new JMenuItem(openAction);
        fileMenu.add(openItem);
        return fileMenu;
    }

    private JMenu createAdminMenu(Application application) {
        JMenu adminMenu = new JMenu("Administration");
        adminMenu.setMnemonic(KeyEvent.VK_A);

        FpsAction fpsAction = new FpsAction(application);
        JMenuItem fpsItem = new JMenuItem(fpsAction);
        adminMenu.add(fpsItem);

        return adminMenu;
    }

}
