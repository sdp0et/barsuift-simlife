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

import java.io.File;

import junit.framework.TestCase;
import barsuift.simLife.universe.BasicUniverseContextFactory;
import barsuift.simLife.universe.OpenException;
import barsuift.simLife.universe.SaveException;
import barsuift.simLife.universe.Universe;
import barsuift.simLife.universe.UniverseContext;
import barsuift.simLife.universe.UniverseContextIO;


public class ApplicationTest extends TestCase {

    private Application application;

    private File saveFile;

    protected void setUp() throws Exception {
        super.setUp();
        application = new Application();
        saveFile = new File("target/test/testUniverse.xml");
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        application = null;
        saveFile.delete();
        saveFile = null;
    }

    public void testSaveUniverse() throws SaveException, InitException {
        try {
            application.saveUniverse();
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException e) {
            // OK expected exception
        }
        UniverseContext universeContext = application.createEmptyUniverse();
        Universe universe = universeContext.getUniverse();
        // now there is a current universe, but still no current save file
        try {
            application.saveUniverse();
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException e) {
            // OK expected exception
        }
        // this should work fine
        application.saveUniverseAs(saveFile);
        // change the universe
        universe.getEnvironment().getSun().setBrightness(PercentHelper.getDecimalValue(13));
        // this should work fine
        application.saveUniverse();
        application.createEmptyUniverse();
        // now we should have reset the current save file and thus throw an Exception
        try {
            application.saveUniverse();
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException e) {
            // OK expected exception
        }
    }

    public void testOpen() throws OpenException, SaveException, InitException {
        // create a test file to be read
        BasicUniverseContextFactory factory = new BasicUniverseContextFactory();
        UniverseContext universeContext = factory.createRandom();
        UniverseContextIO io = new UniverseContextIO(saveFile);
        io.write(universeContext);
        // now try to read it
        UniverseContext universeContext2 = application.openUniverse(saveFile);
        assertEquals(universeContext.getState(), universeContext2.getState());
    }

}
