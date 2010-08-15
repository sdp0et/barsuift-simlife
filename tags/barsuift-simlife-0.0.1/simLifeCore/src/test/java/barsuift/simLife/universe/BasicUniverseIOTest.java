/**
 * barsuift-simlife is a life simulator programm
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

import java.io.File;

import barsuift.simLife.FileTestHelper;

import junit.framework.TestCase;


public class BasicUniverseIOTest extends TestCase {

    private BasicUniverseIO universeIo;

    private File file;

    protected void setUp() throws Exception {
        super.setUp();
        file = new File("target/test-data/universe.xml");
        FileTestHelper.deleteAllFiles(file.getParentFile());
        universeIo = new BasicUniverseIO(file);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        universeIo = null;
        FileTestHelper.deleteAllFiles(file.getParentFile());
        file = null;
    }

    public void testWriteAndReadRandom() throws Exception {
        BasicUniverseFactory factory = new BasicUniverseFactory();
        Universe universe = factory.createRandom();
        universeIo.write(universe);
        Universe universe2 = universeIo.read();
        assertEquals(universe, universe2);
    }

    public void testWriteAndReadEmpty() throws Exception {
        BasicUniverseFactory factory = new BasicUniverseFactory();
        Universe universe = factory.createEmpty();
        universeIo.write(universe);
        Universe universe2 = universeIo.read();
        assertEquals(universe, universe2);
    }

}
