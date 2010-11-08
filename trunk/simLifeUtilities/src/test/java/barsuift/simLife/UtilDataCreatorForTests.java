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

import barsuift.simLife.condition.BoundConditionState;
import barsuift.simLife.condition.CyclicConditionState;
import barsuift.simLife.process.BoundedTaskState;
import barsuift.simLife.process.CyclicTaskState;
import barsuift.simLife.time.SimLifeDateState;


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
     * Creates a random date state, between 0 and 100 seconds
     * 
     * @return
     */
    public static SimLifeDateState createRandomDateState() {
        return new SimLifeDateState(Randomizer.randomBetween(0, 100) * 1000);
    }

    /**
     * Creates a specific date state, at 100 seconds
     * 
     * @return
     */
    public static SimLifeDateState createSpecificDateState() {
        return new SimLifeDateState(100000);
    }


    /*
     * ************* CONDTIONS *************
     */

    public static CyclicConditionState createRandomCyclicConditionState() {
        return new CyclicConditionState(Randomizer.randomBetween(3, 10), Randomizer.randomBetween(0, 2));
    }

    /**
     * Creates a specific CyclicConditionState with
     * <ul>
     * <li>cycle=5</li>
     * <li>count=2</li>
     * </ul>
     * 
     */
    public static CyclicConditionState createSpecificCyclicConditionState() {
        return new CyclicConditionState(5, 2);
    }

    public static BoundConditionState createRandomBoundConditionState() {
        return new BoundConditionState(Randomizer.randomBetween(3, 10), Randomizer.randomBetween(0, 2));
    }

    /**
     * Creates a specific BoundConditionState with
     * <ul>
     * <li>bound=5</li>
     * <li>count=2</li>
     * </ul>
     * 
     */
    public static BoundConditionState createSpecificBoundConditionState() {
        return new BoundConditionState(5, 2);
    }



    /*
     * ************* TASKS *************
     */

    public static CyclicTaskState createRandomCyclicTaskState() {
        CyclicConditionState executionCondition = createRandomCyclicConditionState();
        return new CyclicTaskState(executionCondition);
    }

    public static CyclicTaskState createSpecificCyclicTaskState() {
        CyclicConditionState executionCondition = createSpecificCyclicConditionState();
        return new CyclicTaskState(executionCondition);
    }

    public static BoundedTaskState createRandomBoundedTaskState() {
        BoundConditionState executionCondition = createRandomBoundConditionState();
        return new BoundedTaskState(executionCondition);
    }

    public static BoundedTaskState createSpecificBoundedTaskState() {
        BoundConditionState executionCondition = createSpecificBoundConditionState();
        return new BoundedTaskState(executionCondition);
    }

}
