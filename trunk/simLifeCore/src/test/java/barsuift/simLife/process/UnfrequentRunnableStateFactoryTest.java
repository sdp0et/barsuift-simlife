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

import junit.framework.TestCase;


public class UnfrequentRunnableStateFactoryTest extends TestCase {

    public void testCreateUnfrequentRunnableState() {
        UnfrequentRunnableStateFactory factory = new UnfrequentRunnableStateFactory();

        UnfrequentRunnableState photosynthesisState = factory.createUnfrequentRunnableState(Photosynthesis.class);
        assertEquals(60, photosynthesisState.getDelay());
        assertEquals(0, photosynthesisState.getCount());

        UnfrequentRunnableState agingTreeState = factory.createUnfrequentRunnableState(Aging.class);
        assertEquals(240, agingTreeState.getDelay());
        assertEquals(0, agingTreeState.getCount());

        UnfrequentRunnableState treeGrowthState = factory.createUnfrequentRunnableState(TreeGrowth.class);
        assertEquals(600, treeGrowthState.getDelay());
        assertEquals(0, treeGrowthState.getCount());

        UnfrequentRunnableState unknownState = factory.createUnfrequentRunnableState(MockUnfrequentRunnable.class);
        assertEquals(1, unknownState.getDelay());
        assertEquals(0, unknownState.getCount());
    }

}
