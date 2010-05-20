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
