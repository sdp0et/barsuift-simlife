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
package barsuift.simLife.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UnfrequentRunnableStateFactory {

    /**
     * The default delay to use when no value is found in the properties file
     */
    private static final int DEFAULT_DELAY = 1;

    private static final String PROPERTIES_FILE = "barsuift/simLife/process/UnfrequentRunnables.properties";

    private static final String DELAY_SUFFIX = ".delay";

    private static final Properties prop = loadProperties();;


    private static Properties loadProperties() {
        Properties prop = new Properties();
        InputStream is = null;

        try {
            is = ClassLoader.getSystemResourceAsStream(PROPERTIES_FILE);
            prop.load(is);
        } catch (IOException e) {
            System.out.println("Unable to open properties file " + PROPERTIES_FILE + "\n" + e);
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println("Unable to close open stream on properties file " + PROPERTIES_FILE);
                }
        }
        return prop;
    }

    public UnfrequentRunnableState createUnfrequentRunnableState(Class<? extends UnfrequentRunnable> clazz) {
        String delayStr = getProperty(clazz.getSimpleName() + DELAY_SUFFIX);

        int delay = (delayStr.length() == 0) ? DEFAULT_DELAY : Integer.parseInt(delayStr);
        int count = 0;
        return new UnfrequentRunnableState(delay, count);

    }

    /**
     * @param key the key to look for
     * @return the value from the properties. The returned value is never null, but can be the empty string
     */
    private String getProperty(String key) {
        String property = prop.getProperty(key);
        if (property == null) {
            System.out.println("No value four for key " + key);
            return "";
        }
        return property;
    }

}
