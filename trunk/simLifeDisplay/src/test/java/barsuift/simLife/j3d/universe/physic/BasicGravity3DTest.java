package barsuift.simLife.j3d.universe.physic;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;

import junit.framework.TestCase;
import barsuift.simLife.j3d.universe.BasicUniverse3D;
import barsuift.simLife.j3d.universe.Universe3DState;
import barsuift.simLife.j3d.universe.Universe3DStateFactory;
import barsuift.simLife.universe.MockUniverse;


public class BasicGravity3DTest extends TestCase {

    private BasicUniverse3D universe3D;

    private Gravity3DState gravity3DState;

    protected void setUp() throws Exception {
        super.setUp();
        Universe3DStateFactory universe3DStateFactory = new Universe3DStateFactory();
        Universe3DState universe3DState = universe3DStateFactory.createRandomUniverse3DState();
        universe3D = new BasicUniverse3D(universe3DState, new MockUniverse());

        Gravity3DStateFactory gravity3DStateFactory = new Gravity3DStateFactory();
        gravity3DState = gravity3DStateFactory.createGravity3DState();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        universe3D = null;
        gravity3DState = null;
    }

    public void testFall() {
        BasicGravity3D gravity3D = new BasicGravity3D(gravity3DState, universe3D);
        assertEquals(0, gravity3D.getGroup().numChildren());

        BranchGroup groupToFall = new BranchGroup();
        groupToFall.addChild(new TransformGroup());
        gravity3D.fall(groupToFall);
        assertEquals(1, gravity3D.getGroup().numChildren());
    }

}
