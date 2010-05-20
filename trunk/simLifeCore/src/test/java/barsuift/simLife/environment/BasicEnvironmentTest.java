package barsuift.simLife.environment;

import junit.framework.TestCase;


public class BasicEnvironmentTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testConstructor() {
        try {
            new BasicEnvironment(null);
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

}
