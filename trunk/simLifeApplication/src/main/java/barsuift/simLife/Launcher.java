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
package barsuift.simLife;


import java.util.logging.Logger;

import javax.swing.UIManager;

public class Launcher {

    private static final Logger logger = Logger.getLogger(Launcher.class.getName());

    public void start() {
        new Application();
    }

    /**
     * Switch to the platform specific look & feel (Windows, or Linux, depending on which platform is used to run the
     * application)
     * 
     * @throws Exception
     */
    public void switchToSystemLookAndFeel() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }

    public static void configureLoggingPropertyFile() {
        String path = ClassLoader.getSystemResource("logging.properties").getPath();
        System.setProperty("java.util.logging.config.file", path);
    }

    public static void main(String[] args) throws Exception {
        configureLoggingPropertyFile();
        logger.info("Launching application");

        Launcher launcher = new Launcher();
        launcher.switchToSystemLookAndFeel();
        launcher.start();
    }

}
