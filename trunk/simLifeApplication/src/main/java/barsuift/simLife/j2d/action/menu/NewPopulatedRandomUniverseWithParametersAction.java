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
package barsuift.simLife.j2d.action.menu;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;

import barsuift.simLife.Application;
import barsuift.simLife.j2d.menu.Accelerators;
import barsuift.simLife.process.MainSynchronizer;
import barsuift.simLife.universe.UniverseContext;


public class NewPopulatedRandomUniverseWithParametersAction extends AbstractAction {

    private static final Logger logger = Logger.getLogger(NewPopulatedRandomUniverseWithParametersAction.class
            .getName());

    private static final long serialVersionUID = -7620926200302148499L;

    private final Application application;

    public NewPopulatedRandomUniverseWithParametersAction(Application application) {
        super();
        this.application = application;
        putValue(NAME, "New populated random universe (with parameters)");
        putValue(SHORT_DESCRIPTION, "Create a new populated random universe with parameters");
        putValue(ACCELERATOR_KEY, Accelerators.NEW_POPULATED_RANDOM_UNIVERSE_WITH_PARAMETERS);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        stopApp();
        try {
            application.createPopulatedRandomUniverseWithParameters();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Unable to create new populated random universe with parameters", ex);
        }
    }

    private void stopApp() {
        UniverseContext universeContext = application.getUniverseContext();
        if (universeContext != null) {
            MainSynchronizer synchronizer = universeContext.getSynchronizer();
            if (synchronizer.isRunning()) {
                synchronizer.stop();
            }
        }
    }

}
