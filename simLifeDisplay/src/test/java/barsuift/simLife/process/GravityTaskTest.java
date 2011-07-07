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
package barsuift.simLife.process;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import barsuift.simLife.UtilDataCreatorForTests;
import barsuift.simLife.j3d.MobileEvent;
import barsuift.simLife.j3d.MockMobile;
import barsuift.simLife.j3d.landscape.Landscape3D;
import barsuift.simLife.j3d.landscape.MockLandscape3D;
import barsuift.simLife.message.PublisherTestHelper;

import static org.fest.assertions.Assertions.assertThat;


public class GravityTaskTest {

    @Test
    public void testExecuteSplitConditionalStep_WithHeight() {
        SplitConditionalTaskState state = UtilDataCreatorForTests.createSpecificSplitConditionalTaskState();
        MockLandscape3D landscape3D = new MockLandscape3D();
        landscape3D.setHeight(5);
        GravityTask gravity = new GravityTask(state);
        gravity.init(landscape3D);

        // add mobile instances
        PublisherTestHelper publisher = new PublisherTestHelper();
        MockMobile mobile = createMockMobileAtPosition(new Vector3f(1, 5.1f, 3));
        publisher.addSubscriberTo(mobile);
        gravity.fall(mobile);

        // with stepSize=3, the movement should be y-=0.075
        gravity.executeSplitConditionalStep(state.getStepSize());

        AssertJUnit.assertEquals(new Vector3f(1, 5.025f, 3), getTranslation(mobile));
        // not yet FALLEN
        AssertJUnit.assertEquals(0, publisher.nbUpdated());
        assertThat(publisher.getUpdateObjects()).isEmpty();
        // mobile should still be in the list of mobile
        AssertJUnit.assertTrue(gravity.getMobiles().contains(mobile));

        // with stepSize=3, the movement should be y-=0.075
        gravity.executeSplitConditionalStep(state.getStepSize());

        AssertJUnit.assertEquals(new Vector3f(1, 5.0f, 3), getTranslation(mobile));
        // it is now FALLEN, with height=5
        AssertJUnit.assertEquals(1, publisher.nbUpdated());
        AssertJUnit.assertEquals(MobileEvent.FALLEN, publisher.getUpdateObjects().get(0));
        // mobile should still be in the list of mobile
        AssertJUnit.assertFalse(gravity.getMobiles().contains(mobile));
    }

    @Test
    public void testExecuteSplitConditionalStep() {
        SplitConditionalTaskState state = UtilDataCreatorForTests.createSpecificSplitConditionalTaskState();
        Landscape3D landscape3D = new MockLandscape3D();
        GravityTask gravity = new GravityTask(state);
        gravity.init(landscape3D);

        // test with no mobile : nothing special should happen
        gravity.executeSplitConditionalStep(state.getStepSize());

        // add mobile instances
        PublisherTestHelper publisher1 = new PublisherTestHelper();
        MockMobile mobile1 = createMockMobileAtPosition(new Vector3f(1, 2, 3));
        publisher1.addSubscriberTo(mobile1);
        gravity.fall(mobile1);

        PublisherTestHelper publisher2 = new PublisherTestHelper();
        MockMobile mobile2 = createMockMobileAtPosition(new Vector3f(1, 0.076f, 3));
        publisher2.addSubscriberTo(mobile2);
        gravity.fall(mobile2);
        PublisherTestHelper publisher3 = new PublisherTestHelper();
        MockMobile mobile3 = createMockMobileAtPosition(new Vector3f(1, 0.075f, 3));
        publisher3.addSubscriberTo(mobile3);
        gravity.fall(mobile3);
        PublisherTestHelper publisher4 = new PublisherTestHelper();
        MockMobile mobile4 = createMockMobileAtPosition(new Vector3f(1, 0.074f, 3));
        publisher4.addSubscriberTo(mobile4);
        gravity.fall(mobile4);

        PublisherTestHelper publisher5 = new PublisherTestHelper();
        MockMobile mobile5 = createMockMobileAtPosition(new Vector3f(1, 0.026f, 3));
        publisher5.addSubscriberTo(mobile5);
        gravity.fall(mobile5);
        PublisherTestHelper publisher6 = new PublisherTestHelper();
        MockMobile mobile6 = createMockMobileAtPosition(new Vector3f(1, 0.025f, 3));
        publisher6.addSubscriberTo(mobile6);
        gravity.fall(mobile6);
        PublisherTestHelper publisher7 = new PublisherTestHelper();
        MockMobile mobile7 = createMockMobileAtPosition(new Vector3f(1, 0.024f, 3));
        publisher7.addSubscriberTo(mobile7);
        gravity.fall(mobile7);

        // with stepSize=3, the movement should be y-=0.075
        gravity.executeSplitConditionalStep(state.getStepSize());



        AssertJUnit.assertEquals(new Vector3f(1, 1.925f, 3), getTranslation(mobile1));
        AssertJUnit.assertEquals(new Vector3f(1, 0.001f, 3), getTranslation(mobile2));
        AssertJUnit.assertEquals(new Vector3f(1, 0, 3), getTranslation(mobile3));
        AssertJUnit.assertEquals(new Vector3f(1, 0, 3), getTranslation(mobile4));
        AssertJUnit.assertEquals(new Vector3f(1, 0, 3), getTranslation(mobile5));
        AssertJUnit.assertEquals(new Vector3f(1, 0, 3), getTranslation(mobile6));
        AssertJUnit.assertEquals(new Vector3f(1, 0, 3), getTranslation(mobile7));

        // every mobile except mobile1 and mobile2 should notify a FALLEN event
        AssertJUnit.assertEquals(0, publisher1.nbUpdated());
        assertThat(publisher1.getUpdateObjects()).isEmpty();
        AssertJUnit.assertEquals(0, publisher2.nbUpdated());
        assertThat(publisher2.getUpdateObjects()).isEmpty();

        AssertJUnit.assertEquals(1, publisher3.nbUpdated());
        AssertJUnit.assertEquals(MobileEvent.FALLEN, publisher3.getUpdateObjects().get(0));
        AssertJUnit.assertEquals(1, publisher4.nbUpdated());
        AssertJUnit.assertEquals(MobileEvent.FALLEN, publisher4.getUpdateObjects().get(0));
        AssertJUnit.assertEquals(1, publisher5.nbUpdated());
        AssertJUnit.assertEquals(MobileEvent.FALLEN, publisher5.getUpdateObjects().get(0));
        AssertJUnit.assertEquals(1, publisher6.nbUpdated());
        AssertJUnit.assertEquals(MobileEvent.FALLEN, publisher6.getUpdateObjects().get(0));
        AssertJUnit.assertEquals(1, publisher7.nbUpdated());
        AssertJUnit.assertEquals(MobileEvent.FALLEN, publisher7.getUpdateObjects().get(0));

        // only mobile1 and mobile2 should still be in the list of mobile
        AssertJUnit.assertTrue(gravity.getMobiles().contains(mobile1));
        AssertJUnit.assertTrue(gravity.getMobiles().contains(mobile2));
        AssertJUnit.assertFalse(gravity.getMobiles().contains(mobile3));
        AssertJUnit.assertFalse(gravity.getMobiles().contains(mobile4));
        AssertJUnit.assertFalse(gravity.getMobiles().contains(mobile5));
        AssertJUnit.assertFalse(gravity.getMobiles().contains(mobile6));
        AssertJUnit.assertFalse(gravity.getMobiles().contains(mobile7));

    }

    private Vector3f getTranslation(MockMobile mobile) {
        Transform3D transform3D = new Transform3D();
        mobile.getTransformGroup().getTransform(transform3D);
        Vector3f translation = new Vector3f();
        transform3D.get(translation);
        return translation;
    }

    private MockMobile createMockMobileAtPosition(Vector3f translation) {
        MockMobile mobile = new MockMobile();
        Transform3D transform3D = new Transform3D();
        transform3D.setTranslation(translation);
        TransformGroup tg = new TransformGroup(transform3D);
        mobile.setTransformGroup(tg);
        return mobile;
    }

}
