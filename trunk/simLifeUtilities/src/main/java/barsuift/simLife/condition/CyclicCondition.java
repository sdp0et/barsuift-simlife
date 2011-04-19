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
 * A cyclic condition evaluates to false except on a cyclic period.
 * <p>
 * For example, with a cycle of 3, successive calls to evaluate returns the following values :
 * <ol>
 * <li>{@code false}</li>
 * <li>{@code false}</li>
 * <li>{@code true}</li>
 * <li>{@code false}</li>
 * <li>{@code false}</li>
 * <li>{@code true}</li>
 * <li>...</li>
 * </ol>
 * </p>
 * 
 */
public class CyclicCondition implements Condition, Persistent<CyclicConditionState> {

    private final CyclicConditionState state;

    private final int cycle;

    private int count;

    public CyclicCondition(CyclicConditionState state) {
        super();
        this.state = state;
        this.cycle = state.getCycle();
        this.count = state.getCount();
    }

    /**
     * Increment a counter and then test if the counter is equal to the cycle. If true, the counter is reseted.
     */
    @Override
    public boolean evaluate() {
        count++;
        if (count == cycle) {
            count = 0;
            return true;
        }
        return false;
    }

    @Override
    public CyclicConditionState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setCount(count);
        state.setCycle(cycle);
    }

}
