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
package barsuift.simLife.j3d.landscape;

import java.awt.Label;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.vecmath.Vector3f;

import org.fest.assertions.Delta;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.j3d.BoundingBoxState;
import barsuift.simLife.j3d.Tuple3dState;
import barsuift.simLife.j3d.Tuple3fState;
import barsuift.simLife.landscape.LandscapeParameters;

import com.sun.j3d.utils.universe.ViewingPlatform;

import static barsuift.simLife.j3d.assertions.VectorAssert.assertThat;

import static org.fest.assertions.Assertions.assertThat;


public class BasicNavigatorTest {

    private LandscapeParameters parameters;

    private BasicNavigator navigator;

    private NavigatorState state;

    private MockLandscape3D landscape3D;

    private float x;

    private float y;

    private float z;

    @BeforeMethod
    protected void setUp() {
        parameters = new LandscapeParameters();
        parameters.random();
        NavigatorStateFactory navigatorStateFactory = new NavigatorStateFactory();
        state = navigatorStateFactory.createNavigatorState(parameters);
        landscape3D = new MockLandscape3D();
        navigator = new BasicNavigator(state);
        navigator.init(landscape3D);
        ViewingPlatform vp = new ViewingPlatform();
        navigator.setViewingPlatform(vp);
        resetXYZ();
    }

    private void resetXYZ() {
        Tuple3fState originalTranslation = state.getOriginalTranslation();
        x = originalTranslation.getX();
        y = originalTranslation.getY();
        z = originalTranslation.getZ();
    }

    @AfterMethod
    protected void tearDown() {
        parameters = null;
        state = null;
        landscape3D = null;
        navigator = null;
    }


    private KeyEvent createKeyEvent(int id, int modifiers, int keyCode) {
        return new KeyEvent(new Label(), id, 0, modifiers, keyCode, KeyEvent.CHAR_UNDEFINED);
    }

    private MouseEvent createMouseEvent(int modifiers, int x, int y) {
        return new MouseEvent(new Label(), MouseEvent.MOUSE_DRAGGED, 0, modifiers, x, y, 0, false);
    }


    @Test
    public void testProcessKeyEvent_Basic() {
        // press UP
        navigator.processKeyPressedEvent(createKeyEvent(KeyEvent.KEY_PRESSED, 0, KeyEvent.VK_UP));

        // test position
        Vector3f expectedTranslation = new Vector3f(x, y, z - BasicNavigator.MOVE_STEP);
        assertThat(navigator.getTranslation()).isEqualTo(expectedTranslation);
    }

    @Test
    public void testProcessKeyEvent_Continuous() {
        Vector3f expectedTranslation;

        // press UP
        navigator.processKeyPressedEvent(createKeyEvent(KeyEvent.KEY_PRESSED, 0, KeyEvent.VK_UP));

        // test position
        expectedTranslation = new Vector3f(x, y, z - BasicNavigator.MOVE_STEP);
        assertThat(navigator.getTranslation()).isEqualTo(expectedTranslation);

        // press UP again
        navigator.processKeyPressedEvent(createKeyEvent(KeyEvent.KEY_PRESSED, 0, KeyEvent.VK_UP));

        // test position
        expectedTranslation = new Vector3f(x, y, z - 2 * BasicNavigator.MOVE_STEP);
        assertThat(navigator.getTranslation()).isEqualTo(expectedTranslation);
    }

    @Test
    public void testProcessKeyEvent_WithCtrl() {
        // press RIGHT
        navigator.processKeyPressedEvent(createKeyEvent(KeyEvent.KEY_PRESSED, 0, KeyEvent.VK_RIGHT));

        // test position
        Vector3f expectedTranslation = new Vector3f(x + BasicNavigator.MOVE_STEP, y, z);
        assertThat(navigator.getTranslation()).isEqualTo(expectedTranslation);
        assertThat(navigator.getState().getRotationY()).isEqualTo(NavigatorStateFactory.ORIGINAL_ROTATION_Y);

        // press CTRL + RIGHT
        navigator.processKeyPressedEvent(createKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CTRL_MASK, KeyEvent.VK_RIGHT));

        // test position
        assertThat(navigator.getTranslation()).isEqualTo(expectedTranslation);

        assertThat(navigator.getRotationY()).isEqualTo(
                NavigatorStateFactory.ORIGINAL_ROTATION_Y - BasicNavigator.ROTATION_STEP_KEYBOARD + 2 * Math.PI,
                Delta.delta(0.0001));
    }

    @Test
    public void testProcessMouseEvent() {
        navigator.processMouseEvent(createMouseEvent(MouseEvent.BUTTON3_DOWN_MASK, 0, 1));
        // the movement is not big enough, so the rotations are still the default ones
        assertThat(navigator.getState().getRotationX()).isEqualTo(NavigatorStateFactory.ORIGINAL_ROTATION_X);
        assertThat(navigator.getState().getRotationY()).isEqualTo(NavigatorStateFactory.ORIGINAL_ROTATION_Y);

        navigator.processMouseEvent(createMouseEvent(MouseEvent.BUTTON3_DOWN_MASK, 4, 0));
        // the movement is on X axis, so rotate to the right (around Y axis)
        assertThat(navigator.getState().getRotationX()).isEqualTo(NavigatorStateFactory.ORIGINAL_ROTATION_X);
        assertThat(navigator.getRotationY()).isEqualTo(
                NavigatorStateFactory.ORIGINAL_ROTATION_Y - BasicNavigator.ROTATION_STEP_MOUSE + 2 * Math.PI,
                Delta.delta(0.0001));

        navigator.processMouseEvent(createMouseEvent(MouseEvent.BUTTON3_DOWN_MASK, 4, 12));
        // the movement is on Y axis, so rotate to the bottom (around X axis)
        assertThat(navigator.getRotationX()).isEqualTo(
                NavigatorStateFactory.ORIGINAL_ROTATION_X - BasicNavigator.ROTATION_STEP_MOUSE + 2 * Math.PI,
                Delta.delta(0.0001));
        assertThat(navigator.getRotationY()).isEqualTo(
                NavigatorStateFactory.ORIGINAL_ROTATION_Y - BasicNavigator.ROTATION_STEP_MOUSE + 2 * Math.PI,
                Delta.delta(0.0001));

        navigator.processMouseEvent(createMouseEvent(MouseEvent.BUTTON3_DOWN_MASK, 4, 11));
        // the movement is not big enough, so nothing changes
        assertThat(navigator.getRotationX()).isEqualTo(
                NavigatorStateFactory.ORIGINAL_ROTATION_X - BasicNavigator.ROTATION_STEP_MOUSE + 2 * Math.PI,
                Delta.delta(0.0001));
        assertThat(navigator.getRotationY()).isEqualTo(
                NavigatorStateFactory.ORIGINAL_ROTATION_Y - BasicNavigator.ROTATION_STEP_MOUSE + 2 * Math.PI,
                Delta.delta(0.0001));

        navigator.processMouseEvent(createMouseEvent(MouseEvent.BUTTON3_DOWN_MASK, 4, 9));
        // the movement is on Y axis, so rotate to the top (around X axis)
        assertThat(navigator.getState().getRotationX()).isEqualTo(NavigatorStateFactory.ORIGINAL_ROTATION_X);
        assertThat(navigator.getRotationY()).isEqualTo(
                NavigatorStateFactory.ORIGINAL_ROTATION_Y - BasicNavigator.ROTATION_STEP_MOUSE + 2 * Math.PI,
                Delta.delta(0.0001));

    }

    @Test
    public void testSetNavigationMode() {
        // set to fly mode
        navigator.setNavigationMode(NavigationMode.FLY);
        // move up
        KeyEvent eventKeyUp = createKeyEvent(KeyEvent.KEY_PRESSED, 0, KeyEvent.VK_PAGE_UP);
        navigator.processKeyPressedEvent(eventKeyUp);
        assertThat(navigator.getTranslation().getY()).isEqualTo(
                NavigatorStateFactory.VIEWER_SIZE + BasicNavigator.MOVE_STEP, Delta.delta(0.0001));
        // set to walk mode
        navigator.setNavigationMode(NavigationMode.WALK);
        // check back on ground
        assertThat(navigator.getState().getTranslation().getY()).isEqualTo(NavigatorStateFactory.VIEWER_SIZE);
    }

    @Test
    public void testGetState() {
        assertThat(navigator.getState()).isEqualTo(state);
        assertThat(navigator.getState()).isSameAs(state);
        assertThat(navigator.getState().getRotationX()).isEqualTo(0.0);
        assertThat(navigator.getState().getRotationY()).isEqualTo(0.0);
        Vector3f expectedTranslation = new Vector3f(parameters.getSize() / 2, 2.0f, parameters.getSize() / 2);
        assertThat(navigator.getTranslation()).isEqualTo(expectedTranslation);
        assertThat(navigator.getState().getNavigationMode()).isEqualTo(NavigationMode.WALK);
        assertThat(navigator.getState().getNavigationMode()).isEqualTo(NavigationMode.DEFAULT);

        navigator.setNavigationMode(NavigationMode.FLY);
        KeyEvent eventKeyUp = createKeyEvent(KeyEvent.KEY_PRESSED, 0, KeyEvent.VK_PAGE_UP);
        KeyEvent eventKeyRotX = createKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CTRL_MASK, KeyEvent.VK_PAGE_UP);
        KeyEvent eventKeyRotY = createKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CTRL_MASK, KeyEvent.VK_LEFT);
        navigator.processKeyPressedEvent(eventKeyUp);
        navigator.processKeyPressedEvent(eventKeyRotX);
        navigator.processKeyPressedEvent(eventKeyRotY);


        assertThat(navigator.getState()).isEqualTo(state);
        assertThat(navigator.getState()).isSameAs(state);
        assertThat(navigator.getState().getRotationX()).isEqualTo(BasicNavigator.ROTATION_STEP_KEYBOARD);
        assertThat(navigator.getState().getRotationY()).isEqualTo(BasicNavigator.ROTATION_STEP_KEYBOARD);
        expectedTranslation = new Vector3f(x, y + BasicNavigator.MOVE_STEP, z);
        assertThat(navigator.getTranslation()).isEqualTo(expectedTranslation);
        assertThat(navigator.getState().getNavigationMode()).isEqualTo(NavigationMode.FLY);
    }


    // return

    @Test
    public void testResetToOriginalPosition_WALK_0_bis() {
        int size = 10;
        BoundingBoxState boundsState = new BoundingBoxState(new Tuple3dState(0, -1, 0), new Tuple3dState(size,
                size + 50, size));
        state = new NavigatorState(new Tuple3fState(5, 6, 7), new Tuple3fState(0, 1, 2), 0, 1, NavigationMode.WALK,
                boundsState);
        resetXYZ();
        navigator = new BasicNavigator(state);
        navigator.init(landscape3D);
        ViewingPlatform vp = new ViewingPlatform();
        navigator.setViewingPlatform(vp);

        landscape3D.setHeight((float) 0);
        float expectedHeight1 = NavigatorStateFactory.VIEWER_SIZE;
        float expectedHeight2 = NavigatorStateFactory.VIEWER_SIZE;
        float expectedHeight3 = NavigatorStateFactory.VIEWER_SIZE;
        internalTestResetToOriginalPosition(expectedHeight1, expectedHeight2, expectedHeight3);
    }

    @Test
    public void testResetToOriginalPosition_WALK_0() {
        navigator.setNavigationMode(NavigationMode.WALK);
        float height = 0;
        landscape3D.setHeight(height);
        float expectedHeight1 = NavigatorStateFactory.VIEWER_SIZE;
        float expectedHeight2 = NavigatorStateFactory.VIEWER_SIZE;
        float expectedHeight3 = NavigatorStateFactory.VIEWER_SIZE;
        internalTestResetToOriginalPosition(expectedHeight1, expectedHeight2, expectedHeight3);
    }

    @Test
    public void testResetToOriginalPosition_WALK_5() {
        navigator.setNavigationMode(NavigationMode.WALK);
        float height = 5;
        landscape3D.setHeight(height);
        float expectedHeight1 = height + NavigatorStateFactory.VIEWER_SIZE;
        float expectedHeight2 = height + NavigatorStateFactory.VIEWER_SIZE;
        float expectedHeight3 = height + NavigatorStateFactory.VIEWER_SIZE;
        internalTestResetToOriginalPosition(expectedHeight1, expectedHeight2, expectedHeight3);
    }

    @Test
    public void testResetToOriginalPosition_FLY_0() {
        navigator.setNavigationMode(NavigationMode.FLY);
        float height = 0;
        landscape3D.setHeight(height);
        float expectedHeight1 = NavigatorStateFactory.VIEWER_SIZE;
        float expectedHeight2 = NavigatorStateFactory.VIEWER_SIZE + BasicNavigator.MOVE_STEP;
        float expectedHeight3 = NavigatorStateFactory.VIEWER_SIZE;
        internalTestResetToOriginalPosition(expectedHeight1, expectedHeight2, expectedHeight3);
    }

    @Test
    public void testResetToOriginalPosition_FLY_5() {
        navigator.setNavigationMode(NavigationMode.FLY);
        float height = 5;
        landscape3D.setHeight(height);
        float expectedHeight1 = height + BasicNavigator.MIN_DISTANCE_FROM_GROUND;
        float expectedHeight2 = height + BasicNavigator.MIN_DISTANCE_FROM_GROUND + BasicNavigator.MOVE_STEP;
        float expectedHeight3 = height + BasicNavigator.MIN_DISTANCE_FROM_GROUND;
        internalTestResetToOriginalPosition(expectedHeight1, expectedHeight2, expectedHeight3);
    }

    private void internalTestResetToOriginalPosition(float expectedHeight1, float expectedHeight2, float expectedHeight3) {
        navigator.resetToOriginalPosition();
        assertThat(navigator.getState().getRotationX()).isEqualTo(NavigatorStateFactory.ORIGINAL_ROTATION_X);
        assertThat(navigator.getState().getRotationY()).isEqualTo(NavigatorStateFactory.ORIGINAL_ROTATION_Y);
        assertThat(navigator.getTranslation()).isEqualTo(new Vector3f(x, expectedHeight1, z));

        // move forward, up, and left
        // look up and left
        KeyEvent eventKeyFwd = createKeyEvent(KeyEvent.KEY_PRESSED, 0, KeyEvent.VK_UP);
        KeyEvent eventKeyUp = createKeyEvent(KeyEvent.KEY_PRESSED, 0, KeyEvent.VK_PAGE_UP);
        KeyEvent eventKeyLeft = createKeyEvent(KeyEvent.KEY_PRESSED, 0, KeyEvent.VK_LEFT);
        KeyEvent eventKeyRotX = createKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CTRL_MASK, KeyEvent.VK_PAGE_UP);
        KeyEvent eventKeyRotY = createKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CTRL_MASK, KeyEvent.VK_LEFT);
        navigator.processKeyPressedEvent(eventKeyFwd);
        navigator.processKeyPressedEvent(eventKeyUp);
        navigator.processKeyPressedEvent(eventKeyLeft);
        navigator.processKeyPressedEvent(eventKeyRotX);
        navigator.processKeyPressedEvent(eventKeyRotY);
        assertThat(navigator.getRotationX()).isEqualTo(
                NavigatorStateFactory.ORIGINAL_ROTATION_X + BasicNavigator.ROTATION_STEP_KEYBOARD, Delta.delta(0.0001));
        assertThat(navigator.getRotationY()).isEqualTo(
                NavigatorStateFactory.ORIGINAL_ROTATION_Y + BasicNavigator.ROTATION_STEP_KEYBOARD, Delta.delta(0.0001));
        Vector3f expectedTranslation = new Vector3f(x - BasicNavigator.MOVE_STEP, expectedHeight2, z
                - BasicNavigator.MOVE_STEP);
        assertThat(navigator.getTranslation()).isEqualTo(expectedTranslation);

        // rotations and translation must go back to default
        navigator.resetToOriginalPosition();
        assertThat(navigator.getState().getRotationX()).isEqualTo(NavigatorStateFactory.ORIGINAL_ROTATION_X);
        assertThat(navigator.getState().getRotationY()).isEqualTo(NavigatorStateFactory.ORIGINAL_ROTATION_Y);
        assertThat(navigator.getTranslation()).isEqualTo(new Vector3f(x, expectedHeight3, z));
    }

    @Test
    public void testResetToNominalViewAngle_WALK_0() {
        navigator.setNavigationMode(NavigationMode.WALK);
        float height = 0;
        landscape3D.setHeight(height);
        float expectedHeight1 = NavigatorStateFactory.VIEWER_SIZE;
        float expectedHeight2 = NavigatorStateFactory.VIEWER_SIZE;
        float expectedHeight3 = NavigatorStateFactory.VIEWER_SIZE;
        internalTestResetToNominalViewAngle(expectedHeight1, expectedHeight2, expectedHeight3);
    }

    @Test
    public void testResetToNominalViewAngle_WALK_5() {
        navigator.setNavigationMode(NavigationMode.WALK);
        float height = 5;
        landscape3D.setHeight(height);
        float expectedHeight1 = height + NavigatorStateFactory.VIEWER_SIZE;
        float expectedHeight2 = height + NavigatorStateFactory.VIEWER_SIZE;
        float expectedHeight3 = height + NavigatorStateFactory.VIEWER_SIZE;
        internalTestResetToNominalViewAngle(expectedHeight1, expectedHeight2, expectedHeight3);
    }

    @Test
    public void testResetToNominalViewAngle_FLY_0() {
        navigator.setNavigationMode(NavigationMode.FLY);
        float height = 0;
        landscape3D.setHeight(height);
        float expectedHeight1 = NavigatorStateFactory.VIEWER_SIZE;
        float expectedHeight2 = NavigatorStateFactory.VIEWER_SIZE + BasicNavigator.MOVE_STEP;
        float expectedHeight3 = NavigatorStateFactory.VIEWER_SIZE;
        internalTestResetToNominalViewAngle(expectedHeight1, expectedHeight2, expectedHeight3);
    }

    @Test
    public void testResetToNominalViewAngle_FLY_5() {
        navigator.setNavigationMode(NavigationMode.FLY);
        float height = 5;
        landscape3D.setHeight(height);
        float expectedHeight1 = height + BasicNavigator.MIN_DISTANCE_FROM_GROUND;
        float expectedHeight2 = height + BasicNavigator.MIN_DISTANCE_FROM_GROUND + BasicNavigator.MOVE_STEP;
        float expectedHeight3 = height + BasicNavigator.MIN_DISTANCE_FROM_GROUND;
        internalTestResetToNominalViewAngle(expectedHeight1, expectedHeight2, expectedHeight3);
    }

    private void internalTestResetToNominalViewAngle(float expectedHeight1, float expectedHeight2, float expectedHeight3) {
        navigator.resetToNominalViewAngle();
        assertThat(navigator.getState().getRotationX()).isEqualTo(NavigatorStateFactory.ORIGINAL_ROTATION_X);
        assertThat(navigator.getState().getRotationY()).isEqualTo(NavigatorStateFactory.ORIGINAL_ROTATION_Y);
        assertThat(navigator.getTranslation()).isEqualTo(new Vector3f(x, expectedHeight1, z));

        // move forward, up, and left
        // look up and left
        KeyEvent eventKeyFwd = createKeyEvent(KeyEvent.KEY_PRESSED, 0, KeyEvent.VK_UP);
        KeyEvent eventKeyUp = createKeyEvent(KeyEvent.KEY_PRESSED, 0, KeyEvent.VK_PAGE_UP);
        KeyEvent eventKeyLeft = createKeyEvent(KeyEvent.KEY_PRESSED, 0, KeyEvent.VK_LEFT);
        KeyEvent eventKeyRotX = createKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CTRL_MASK, KeyEvent.VK_PAGE_UP);
        KeyEvent eventKeyRotY = createKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CTRL_MASK, KeyEvent.VK_LEFT);
        navigator.processKeyPressedEvent(eventKeyFwd);
        navigator.processKeyPressedEvent(eventKeyUp);
        navigator.processKeyPressedEvent(eventKeyLeft);
        navigator.processKeyPressedEvent(eventKeyRotX);
        navigator.processKeyPressedEvent(eventKeyRotY);
        assertThat(navigator.getRotationX()).isEqualTo(
                NavigatorStateFactory.ORIGINAL_ROTATION_X + BasicNavigator.ROTATION_STEP_KEYBOARD, Delta.delta(0.0001));
        assertThat(navigator.getRotationY()).isEqualTo(
                NavigatorStateFactory.ORIGINAL_ROTATION_Y + BasicNavigator.ROTATION_STEP_KEYBOARD, Delta.delta(0.0001));
        Vector3f expectedTranslation = new Vector3f(x - BasicNavigator.MOVE_STEP, expectedHeight2, z
                - BasicNavigator.MOVE_STEP);
        assertThat(navigator.getTranslation()).isEqualTo(expectedTranslation);

        // rotations and Y translations must go back to default, but X and Z translations should not
        navigator.resetToNominalViewAngle();
        assertThat(navigator.getState().getRotationX()).isEqualTo(NavigatorStateFactory.ORIGINAL_ROTATION_X);
        assertThat(navigator.getState().getRotationY()).isEqualTo(NavigatorStateFactory.ORIGINAL_ROTATION_Y);
        Vector3f expectedTranslation2 = new Vector3f(x - BasicNavigator.MOVE_STEP, expectedHeight3, z
                - BasicNavigator.MOVE_STEP);
        assertThat(navigator.getTranslation()).isEqualTo(expectedTranslation2);
    }

}
