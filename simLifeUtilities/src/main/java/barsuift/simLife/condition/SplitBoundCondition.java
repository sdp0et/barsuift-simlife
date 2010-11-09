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
public class SplitBoundCondition implements SplitCondition, Persistent<BoundConditionState> {

    private final BoundConditionState state;

    private final int bound;

    private int count;

    public SplitBoundCondition(BoundConditionState state) {
        super();
        this.state = state;
        this.bound = state.getBound();
        this.count = state.getCount();
    }

    /**
     * Always return false.
     */
    @Override
    public boolean evaluate(int stepSize) {
        count += stepSize;
        return count >= bound;
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