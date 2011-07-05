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
package barsuift.simLife.j3d;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.JaxbTester;

import static org.fest.assertions.Assertions.assertThat;


public class Transform3DStateTest {

    private final JaxbTester<Transform3DState> tester = new JaxbTester<Transform3DState>(getClass());

    @BeforeMethod
    protected void init() throws Exception {
        tester.init();
    }

    @AfterMethod
    protected void clean() {
        tester.clean();
    }

    @Test
    public void readWriteJaxb() throws Exception {
        Transform3DState originalState = DisplayDataCreatorForTests.createRandomTransform3DState();
        tester.write(originalState);
        Transform3DState readState = tester.read();
        assertThat(readState).isEqualTo(originalState);
    }

}
