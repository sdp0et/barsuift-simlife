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
package barsuift.simLife.time;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.JaxbTester;

import static org.fest.assertions.Assertions.assertThat;

public class SimLifeDateStateTest {

    private final JaxbTester<SimLifeDateState> tester = new JaxbTester<SimLifeDateState>(getClass());

    @BeforeMethod
    public void init() throws Exception {
        tester.init();
    }

    @AfterMethod
    public void clean() {
        tester.clean();
    }

    @Test
    public void readWriteJaxb() throws Exception {
        SimLifeDateState date1 = new SimLifeDateState(199199000);
        tester.write(date1);
        SimLifeDateState date2 = tester.read();
        assertThat(date2).isEqualTo(date1);
    }

}
