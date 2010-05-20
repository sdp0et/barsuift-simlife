package barsuift.simLife;

import java.io.File;

import junit.framework.TestCase;
import barsuift.simLife.universe.BasicUniverseFactory;
import barsuift.simLife.universe.BasicUniverseIO;
import barsuift.simLife.universe.OpenException;
import barsuift.simLife.universe.SaveException;
import barsuift.simLife.universe.Universe;


public class ApplicationTest extends TestCase {

    private Application application;

    private File saveFile;

    protected void setUp() throws Exception {
        super.setUp();
        application = new Application();
        saveFile = new File("test/testUniverse.xml");
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        application = null;
        saveFile.delete();
        saveFile = null;
    }

    public void testSaveUniverse() throws SaveException {
        try {
            application.saveUniverse();
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException e) {
            // OK expected exception
        }
        Universe universe = application.createEmptyUniverse();
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
        universe.getEnvironment().getSun().setLuminosity(new Percent(13));
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

    public void testOpen() throws OpenException, SaveException {
        // create a tes file to be read
        BasicUniverseFactory factory = new BasicUniverseFactory();
        Universe universe = factory.createRandom();
        BasicUniverseIO io = new BasicUniverseIO(saveFile);
        io.write(universe);
        // now try to read it
        Universe universe2 = application.openUniverse(saveFile);
        assertEquals(universe, universe2);
    }

}
