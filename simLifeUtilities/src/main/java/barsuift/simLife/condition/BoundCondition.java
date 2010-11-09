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
package barsuift.simLife.condition;

import barsuift.simLife.Persistent;


/**
 * A bound condition evaluates to false as long as the bound is not reached. Then it always return true.
 * 
 */
public class BoundCondition implements Condition, Persistent<BoundConditionState> {

    private final BoundConditionState state;

    private final int bound;

    private int count;

    public BoundCondition(BoundConditionState state) {
        super();
        this.state = state;
        this.bound = state.getBound();
        this.count = state.getCount();
    }

    /**
     * If the counter is less than the bound, increment the counter and return true if the counter has now reached the
     * bound. Return false otherwise.
     */
    @Override
    public boolean evaluate() {
        if (bound == 0) {
            // bound 0 means no bound, so it can not be reached
            return false;
        }
        // this test is to prevent overflow of count
        if (count < bound) {
            count++;
            return count >= bound;
        }
        return true;
    }

    @Override
    public BoundConditionState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setCount(count);
        state.setBound(bound);
    }

}
