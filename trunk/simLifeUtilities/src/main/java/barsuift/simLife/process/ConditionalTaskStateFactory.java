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

import barsuift.simLife.condition.BoundConditionState;
import barsuift.simLife.condition.CyclicConditionState;

public class ConditionalTaskStateFactory {

    /**
     * The default bound to use when no value is found in the properties file. 0 means no bound.
     */
    private static final int DEFAULT_BOUND = 0;

    /**
     * The default cycle to use when no value is found in the properties file
     */
    private static final int DEFAULT_CYCLE = 1;

    private static final String PROPERTIES_FILE = "barsuift/simLife/process/ConditionalTasks.properties";

    private static final String BOUND_SUFFIX = ".bound";

    private static final String CYCLE_SUFFIX = ".cycle";

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

    public ConditionalTaskState createConditionalTaskState(Class<? extends SynchronizedTask> clazz) {
        CyclicConditionState executionCondition = createCyclicConditionState(clazz);
        BoundConditionState endingCondition = createBoundConditionState(clazz);
        return new ConditionalTaskState(executionCondition, endingCondition);
    }

    private BoundConditionState createBoundConditionState(Class<? extends SynchronizedTask> clazz) {
        String boundStr = getProperty(clazz.getSimpleName() + BOUND_SUFFIX);
        int bound = (boundStr.length() == 0) ? DEFAULT_BOUND : Integer.parseInt(boundStr);
        int count = 0;
        return new BoundConditionState(bound, count);
    }

    private CyclicConditionState createCyclicConditionState(Class<? extends SynchronizedTask> clazz) {
        String boundStr = getProperty(clazz.getSimpleName() + CYCLE_SUFFIX);
        int bound = (boundStr.length() == 0) ? DEFAULT_CYCLE : Integer.parseInt(boundStr);
        int count = 0;
        return new CyclicConditionState(bound, count);
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
