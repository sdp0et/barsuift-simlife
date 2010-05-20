package barsuift.simLife.j2d.panel;

import junit.framework.TestCase;
import barsuift.simLife.j3d.universe.MockUniverse3D;
import barsuift.simLife.j3d.universe.Universe3D;


public class Universe3DPanelTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testUnsetAxis() {
        Universe3D universe3D = new MockUniverse3D();
        Universe3DPanel panel = new Universe3DPanel(universe3D);
        panel.setAxis();
        panel.unsetAxis();
        panel.setAxis();
        panel.unsetAxis();
    }

}
