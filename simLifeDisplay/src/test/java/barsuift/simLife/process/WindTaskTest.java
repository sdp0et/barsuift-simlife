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

import org.testng.annotations.Test;

import barsuift.simLife.UtilDataCreatorForTests;
import barsuift.simLife.j3d.MobileEvent;
import barsuift.simLife.j3d.MockMobile;
import barsuift.simLife.j3d.landscape.MockLandscape3D;
import barsuift.simLife.message.PublisherTestHelper;
import static barsuift.simLife.j3d.assertions.VectorAssert.assertThat;

import static org.fest.assertions.Assertions.assertThat;

// FIXME fix tests
// TODO add test with size
public class WindTaskTest {


    @Test
    public void testExecuteSplitConditionalStep_WithHeight() {
        SplitConditionalTaskState state = UtilDataCreatorForTests.createSpecificSplitConditionalTaskState();
        MockLandscape3D landscape3D = new MockLandscape3D();
        landscape3D.setSize(10);
        landscape3D.setHeight(6);
        WindTask wind = new WindTask(state);
        wind.init(landscape3D);

        // add mobile instances
        PublisherTestHelper publisher = new PublisherTestHelper();
        MockMobile mobile = createMockMobileAtPosition(new Vector3f(5, 6.1f, 3));
        publisher.addSubscriberTo(mobile);
        wind.fall(mobile);

        // with stepSize=3, the movement should be x+=0.15
        wind.executeSplitConditionalStep(state.getStepSize());

        assertThat(getTranslation(mobile)).isEqualTo(new Vector3f(5.15f, 6.1f, 3));
        // not yet FALLEN
        assertThat(publisher.nbUpdated()).isEqualTo(0);
        assertThat(publisher.getUpdateObjects()).isEmpty();
        // mobile should still be in the list of mobile
        assertThat(wind.getMobiles()).contains(mobile);

        // with stepSize=3, the movement should be x+=0.15
        wind.executeSplitConditionalStep(state.getStepSize());
        // now the height is 5 so the mobile will be on the ground
        landscape3D.setHeight(5);

        assertThat(getTranslation(mobile)).isEqualTo(new Vector3f(5.30f, 5.0f, 3));
        // it is now FALLEN, with height=5
        assertThat(publisher.nbUpdated()).isEqualTo(1);
        assertThat(publisher.getUpdateObjects().get(0)).isEqualTo(MobileEvent.FALLEN);
        // mobile should not be in the list of mobile anymore
        assertThat(wind.getMobiles()).excludes(mobile);
    }

    @Test
    public void testExecuteSplitConditionalStep() {
        SplitConditionalTaskState state = UtilDataCreatorForTests.createSpecificSplitConditionalTaskState();
        MockLandscape3D landscape3D = new MockLandscape3D();
        landscape3D.setSize(10);
        WindTask wind = new WindTask(state);
        wind.init(landscape3D);

        // test with no mobile : nothing special should happen
        wind.executeSplitConditionalStep(state.getStepSize());

        // add mobile instances
        PublisherTestHelper publisher1 = new PublisherTestHelper();
        MockMobile mobile1 = createMockMobileAtPosition(new Vector3f(1, 2, 3));
        publisher1.addSubscriberTo(mobile1);
        wind.fall(mobile1);

        PublisherTestHelper publisher2 = new PublisherTestHelper();
        MockMobile mobile2 = createMockMobileAtPosition(new Vector3f(1, 0.076f, 3));
        publisher2.addSubscriberTo(mobile2);
        wind.fall(mobile2);
        PublisherTestHelper publisher3 = new PublisherTestHelper();
        MockMobile mobile3 = createMockMobileAtPosition(new Vector3f(1, 0.075f, 3));
        publisher3.addSubscriberTo(mobile3);
        wind.fall(mobile3);
        PublisherTestHelper publisher4 = new PublisherTestHelper();
        MockMobile mobile4 = createMockMobileAtPosition(new Vector3f(1, 0.074f, 3));
        publisher4.addSubscriberTo(mobile4);
        wind.fall(mobile4);

        PublisherTestHelper publisher5 = new PublisherTestHelper();
        MockMobile mobile5 = createMockMobileAtPosition(new Vector3f(1, 0.026f, 3));
        publisher5.addSubscriberTo(mobile5);
        wind.fall(mobile5);
        PublisherTestHelper publisher6 = new PublisherTestHelper();
        MockMobile mobile6 = createMockMobileAtPosition(new Vector3f(1, 0.025f, 3));
        publisher6.addSubscriberTo(mobile6);
        wind.fall(mobile6);
        PublisherTestHelper publisher7 = new PublisherTestHelper();
        MockMobile mobile7 = createMockMobileAtPosition(new Vector3f(1, 0.024f, 3));
        publisher7.addSubscriberTo(mobile7);
        wind.fall(mobile7);

        // with stepSize=3, the movement should be x+=0.15
        wind.executeSplitConditionalStep(state.getStepSize());



        assertThat(getTranslation(mobile1)).isEqualTo(new Vector3f(1.15f, 2, 3));
        assertThat(getTranslation(mobile2)).isEqualTo(new Vector3f(1, 0.001f, 3));
        assertThat(getTranslation(mobile3)).isEqualTo(new Vector3f(1, 0, 3));
        assertThat(getTranslation(mobile4)).isEqualTo(new Vector3f(1, 0, 3));
        assertThat(getTranslation(mobile5)).isEqualTo(new Vector3f(1, 0, 3));
        assertThat(getTranslation(mobile6)).isEqualTo(new Vector3f(1, 0, 3));
        assertThat(getTranslation(mobile7)).isEqualTo(new Vector3f(1, 0, 3));

        // every mobile except mobile1 and mobile2 should notify a FALLEN event
        assertThat(publisher1.nbUpdated()).isEqualTo(0);
        assertThat(publisher1.getUpdateObjects()).isEmpty();
        assertThat(publisher2.nbUpdated()).isEqualTo(0);
        assertThat(publisher2.getUpdateObjects()).isEmpty();

        assertThat(publisher3.nbUpdated()).isEqualTo(1);
        assertThat(publisher3.getUpdateObjects().get(0)).isEqualTo(MobileEvent.FALLEN);
        assertThat(publisher4.nbUpdated()).isEqualTo(1);
        assertThat(publisher4.getUpdateObjects().get(0)).isEqualTo(MobileEvent.FALLEN);
        assertThat(publisher5.nbUpdated()).isEqualTo(1);
        assertThat(publisher5.getUpdateObjects().get(0)).isEqualTo(MobileEvent.FALLEN);
        assertThat(publisher6.nbUpdated()).isEqualTo(1);
        assertThat(publisher6.getUpdateObjects().get(0)).isEqualTo(MobileEvent.FALLEN);
        assertThat(publisher7.nbUpdated()).isEqualTo(1);
        assertThat(publisher7.getUpdateObjects().get(0)).isEqualTo(MobileEvent.FALLEN);

        // only mobile1 and mobile2 should still be in the list of mobile
        assertThat(wind.getMobiles().contains(mobile1)).isTrue();
        assertThat(wind.getMobiles().contains(mobile2)).isTrue();
        assertThat(wind.getMobiles().contains(mobile3)).isFalse();
        assertThat(wind.getMobiles().contains(mobile4)).isFalse();
        assertThat(wind.getMobiles().contains(mobile5)).isFalse();
        assertThat(wind.getMobiles().contains(mobile6)).isFalse();
        assertThat(wind.getMobiles().contains(mobile7)).isFalse();

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
