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

import java.math.BigDecimal;

import barsuift.simLife.time.SimLifeCalendarState;


public final class UtilDataCreatorForTests {

    private UtilDataCreatorForTests() {
        // private constructor to enforce static access
    }

    public static BigDecimal createRandomBigDecimal() {
        return new BigDecimal(Math.random());
    }

    public static boolean createRandomBoolean() {
        return Math.random() >= 0.5;
    }

    /**
     * Creates a random calendar state, between 0 and 100 seconds
     * 
     * @return
     */
    public static SimLifeCalendarState createRandomCalendarState() {
        return new SimLifeCalendarState(Randomizer.randomBetween(0, 100) * 1000);
    }

    /**
     * Creates a specific calendar state, at 100 seconds
     * 
     * @return
     */
    public static SimLifeCalendarState createSpecificCalendarState() {
        return new SimLifeCalendarState(100000);
    }

}
