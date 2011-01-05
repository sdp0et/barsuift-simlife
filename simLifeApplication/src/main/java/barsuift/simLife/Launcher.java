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


import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

public class Launcher {

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

    private static void createApplicationDirectory() {
        String userHome = System.getProperty("user.home");
        String appHome = userHome + File.separatorChar + ".barsuift-simlife" + File.separatorChar;
        File appHomeDirectory = new File(appHome);
        if (!appHomeDirectory.exists()) {
            appHomeDirectory.mkdir();
        }

    }

    public static void main(String[] args) {
        Logger logger = null;
        try {
            // this MUST be done before any logger declaration
            createApplicationDirectory();
            configureLoggingPropertyFile();

            logger = Logger.getLogger(Launcher.class.getName());
            logger.info("Launching application");

            Launcher launcher = new Launcher();
            launcher.switchToSystemLookAndFeel();
            launcher.start();
        } catch (Exception e) {
            if (logger != null) {
                logger.log(Level.SEVERE, "Global error", e);
            }
        }
    }

}
