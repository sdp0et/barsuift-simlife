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
package barsuift.simLife.universe;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.JaxbTester;

import static org.fest.assertions.Assertions.assertThat;

public class UniverseContextStateTest {

    private final JaxbTester<UniverseContextState> tester = new JaxbTester<UniverseContextState>(getClass());

    @BeforeMethod
    protected void setUp() throws Exception {
        tester.init();
    }

    @AfterMethod
    protected void tearDown() {
        tester.clean();
    }

    @Test
    public void testJaxb() throws Exception {
        UniverseContextState universeContextState = CoreDataCreatorForTests.createRandomUniverseContextState();
        tester.write(universeContextState);
        UniverseContextState universeContextState2 = tester.read();
        assertThat(universeContextState2).isEqualTo(universeContextState);
    }

}
