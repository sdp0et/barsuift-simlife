package barsuift.simLife.process;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import junit.framework.TestCase;
import barsuift.simLife.UtilDataCreatorForTests;
import barsuift.simLife.j3d.MobileEvent;
import barsuift.simLife.j3d.MockMobile;
import barsuift.simLife.j3d.helper.VectorTestHelper;
import barsuift.simLife.j3d.terrain.Landscape3D;
import barsuift.simLife.j3d.terrain.MockLandscape3D;
import barsuift.simLife.message.PublisherTestHelper;


public class GravityTaskTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testExecuteSplitConditionalStep_WithHeight() {
        SplitConditionalTaskState state = UtilDataCreatorForTests.createSpecificSplitConditionalTaskState();
        MockLandscape3D landscape3D = new MockLandscape3D();
        landscape3D.setHeight(5);
        GravityTask gravity = new GravityTask(state, landscape3D);

        // add mobile instances
        PublisherTestHelper publisher = new PublisherTestHelper();
        MockMobile mobile = createMockMobileAtPosition(new Vector3d(1, 5.1, 3));
        publisher.addSubscriberTo(mobile);
        gravity.fall(mobile);

        // with stepSize=3, the movement should be y-=0.075
        gravity.executeSplitConditionalStep(state.getStepSize());

        VectorTestHelper.assertVectorEquals(new Vector3d(1, 5.025, 3), getTranslation(mobile));
        // not yet FALLEN
        assertEquals(0, publisher.nbUpdated());
        assertEquals(0, publisher.getUpdateObjects().size());
        // mobile should still be in the list of mobile
        assertTrue(gravity.getMobiles().contains(mobile));

        // with stepSize=3, the movement should be y-=0.075
        gravity.executeSplitConditionalStep(state.getStepSize());

        VectorTestHelper.assertVectorEquals(new Vector3d(1, 5.0, 3), getTranslation(mobile));
        // it is now FALLEN, with height=5
        assertEquals(1, publisher.nbUpdated());
        assertEquals(MobileEvent.FALLEN, publisher.getUpdateObjects().get(0));
        // mobile should still be in the list of mobile
        assertFalse(gravity.getMobiles().contains(mobile));
    }

    public void testExecuteSplitConditionalStep() {
        SplitConditionalTaskState state = UtilDataCreatorForTests.createSpecificSplitConditionalTaskState();
        // FIXME improve tests with different heights
        Landscape3D landscape3D = new MockLandscape3D();
        GravityTask gravity = new GravityTask(state, landscape3D);

        // test with no mobile : nothing special should happen
        gravity.executeSplitConditionalStep(state.getStepSize());

        // add mobile instances
        PublisherTestHelper publisher1 = new PublisherTestHelper();
        MockMobile mobile1 = createMockMobileAtPosition(new Vector3d(1, 2, 3));
        publisher1.addSubscriberTo(mobile1);
        gravity.fall(mobile1);

        PublisherTestHelper publisher2 = new PublisherTestHelper();
        MockMobile mobile2 = createMockMobileAtPosition(new Vector3d(1, 0.076, 3));
        publisher2.addSubscriberTo(mobile2);
        gravity.fall(mobile2);
        PublisherTestHelper publisher3 = new PublisherTestHelper();
        MockMobile mobile3 = createMockMobileAtPosition(new Vector3d(1, 0.075, 3));
        publisher3.addSubscriberTo(mobile3);
        gravity.fall(mobile3);
        PublisherTestHelper publisher4 = new PublisherTestHelper();
        MockMobile mobile4 = createMockMobileAtPosition(new Vector3d(1, 0.074, 3));
        publisher4.addSubscriberTo(mobile4);
        gravity.fall(mobile4);

        PublisherTestHelper publisher5 = new PublisherTestHelper();
        MockMobile mobile5 = createMockMobileAtPosition(new Vector3d(1, 0.026, 3));
        publisher5.addSubscriberTo(mobile5);
        gravity.fall(mobile5);
        PublisherTestHelper publisher6 = new PublisherTestHelper();
        MockMobile mobile6 = createMockMobileAtPosition(new Vector3d(1, 0.025, 3));
        publisher6.addSubscriberTo(mobile6);
        gravity.fall(mobile6);
        PublisherTestHelper publisher7 = new PublisherTestHelper();
        MockMobile mobile7 = createMockMobileAtPosition(new Vector3d(1, 0.024, 3));
        publisher7.addSubscriberTo(mobile7);
        gravity.fall(mobile7);

        // with stepSize=3, the movement should be y-=0.075
        gravity.executeSplitConditionalStep(state.getStepSize());



        VectorTestHelper.assertVectorEquals(new Vector3d(1, 1.925, 3), getTranslation(mobile1));
        VectorTestHelper.assertVectorEquals(new Vector3d(1, 0.001, 3), getTranslation(mobile2));
        VectorTestHelper.assertVectorEquals(new Vector3d(1, 0, 3), getTranslation(mobile3));
        VectorTestHelper.assertVectorEquals(new Vector3d(1, 0, 3), getTranslation(mobile4));
        VectorTestHelper.assertVectorEquals(new Vector3d(1, 0, 3), getTranslation(mobile5));
        VectorTestHelper.assertVectorEquals(new Vector3d(1, 0, 3), getTranslation(mobile6));
        VectorTestHelper.assertVectorEquals(new Vector3d(1, 0, 3), getTranslation(mobile7));

        // every mobile except mobile1 and mobile2 should notify a FALLEN event
        assertEquals(0, publisher1.nbUpdated());
        assertEquals(0, publisher1.getUpdateObjects().size());
        assertEquals(0, publisher2.nbUpdated());
        assertEquals(0, publisher2.getUpdateObjects().size());

        assertEquals(1, publisher3.nbUpdated());
        assertEquals(MobileEvent.FALLEN, publisher3.getUpdateObjects().get(0));
        assertEquals(1, publisher4.nbUpdated());
        assertEquals(MobileEvent.FALLEN, publisher4.getUpdateObjects().get(0));
        assertEquals(1, publisher5.nbUpdated());
        assertEquals(MobileEvent.FALLEN, publisher5.getUpdateObjects().get(0));
        assertEquals(1, publisher6.nbUpdated());
        assertEquals(MobileEvent.FALLEN, publisher6.getUpdateObjects().get(0));
        assertEquals(1, publisher7.nbUpdated());
        assertEquals(MobileEvent.FALLEN, publisher7.getUpdateObjects().get(0));

        // only mobile1 and mobile2 should still be in the list of mobile
        assertTrue(gravity.getMobiles().contains(mobile1));
        assertTrue(gravity.getMobiles().contains(mobile2));
        assertFalse(gravity.getMobiles().contains(mobile3));
        assertFalse(gravity.getMobiles().contains(mobile4));
        assertFalse(gravity.getMobiles().contains(mobile5));
        assertFalse(gravity.getMobiles().contains(mobile6));
        assertFalse(gravity.getMobiles().contains(mobile7));

    }

    private Vector3d getTranslation(MockMobile mobile) {
        Transform3D transform3D = new Transform3D();
        mobile.getTransformGroup().getTransform(transform3D);
        Vector3d translation = new Vector3d();
        transform3D.get(translation);
        return translation;
    }

    private MockMobile createMockMobileAtPosition(Vector3d translation) {
        MockMobile mobile = new MockMobile();
        Transform3D transform3D = new Transform3D();
        transform3D.setTranslation(translation);
        TransformGroup tg = new TransformGroup(transform3D);
        mobile.setTransformGroup(tg);
        return mobile;
    }

}
