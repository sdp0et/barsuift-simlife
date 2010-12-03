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
package barsuift.simLife.j3d.terrain;

import java.awt.Label;
import java.awt.event.KeyEvent;

import junit.framework.TestCase;
import barsuift.simLife.j3d.Tuple3dState;

import com.sun.j3d.utils.universe.ViewingPlatform;


public class BasicNavigatorTest extends TestCase {

    private BasicNavigator navigator;

    private NavigatorState state;

    private MockLandscape3D landscape3D;

    protected void setUp() throws Exception {
        super.setUp();
        NavigatorStateFactory navigatorStateFactory = new NavigatorStateFactory();
        state = navigatorStateFactory.createNavigatorState();
        landscape3D = new MockLandscape3D();
        navigator = new BasicNavigator(state, landscape3D);
        ViewingPlatform vp = new ViewingPlatform();
        navigator.setViewingPlatform(vp);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        state = null;
        landscape3D = null;
        navigator = null;
    }

    public void testProcessKeyPressedEvent() {
        fail("Not yet implemented");
    }

    public void testProcessKeyReleasedEvent() {
        fail("Not yet implemented");
    }

    public void testProcessMouseEvent() {
        fail("Not yet implemented");
    }

    public void testSetNavigationMode() {
        fail("Not yet implemented");
    }

    public void testGetState() {
        assertEquals(state, navigator.getState());
        assertSame(state, navigator.getState());
        assertEquals(0.0, navigator.getState().getRotationX());
        assertEquals(0.0, navigator.getState().getRotationY());
        Tuple3dState translation = navigator.getState().getTranslation();
        assertEquals(4.0, translation.getX());
        assertEquals(2.0, translation.getY());
        assertEquals(20.0, translation.getZ());
        assertEquals(NavigationMode.WALK, navigator.getState().getNavigationMode());
        assertEquals(NavigationMode.DEFAULT, navigator.getState().getNavigationMode());

        navigator.setNavigationMode(NavigationMode.FLY);
        KeyEvent eventKeyUp = createKeyEvent(KeyEvent.KEY_PRESSED, 0, KeyEvent.VK_PAGE_UP);
        KeyEvent eventKeyRotX = createKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CTRL_MASK, KeyEvent.VK_PAGE_UP);
        KeyEvent eventKeyRotY = createKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CTRL_MASK, KeyEvent.VK_LEFT);
        navigator.processKeyPressedEvent(eventKeyUp);
        navigator.processKeyPressedEvent(eventKeyRotX);
        navigator.processKeyPressedEvent(eventKeyRotY);


        assertEquals(state, navigator.getState());
        assertSame(state, navigator.getState());
        assertEquals(BasicNavigator.ROTATION_STEP_KEYBOARD, navigator.getState().getRotationX());
        assertEquals(BasicNavigator.ROTATION_STEP_KEYBOARD, navigator.getState().getRotationY());
        translation = navigator.getState().getTranslation();
        assertEquals(4.0, translation.getX());
        assertEquals(2.0 + BasicNavigator.MOVE_STEP, translation.getY());
        assertEquals(20.0, translation.getZ());
        assertEquals(NavigationMode.FLY, navigator.getState().getNavigationMode());
    }

    private KeyEvent createKeyEvent(int id, int modifiers, int keyCode) {
        return new KeyEvent(new Label(), id, 0, modifiers, keyCode, KeyEvent.CHAR_UNDEFINED);
    }

    public void testResetToOriginalPosition() {

        double height = 5;
        landscape3D.setHeight(height);
        navigator.resetToOriginalPosition();
        assertEquals(NavigatorStateFactory.ORIGINAL_ROTATION_X, navigator.getState().getRotationX());
        assertEquals(NavigatorStateFactory.ORIGINAL_ROTATION_Y, navigator.getState().getRotationY());
        assertEquals(NavigatorStateFactory.ORIGINAL_POSITION.x, navigator.getState().getTranslation().getX());
        assertEquals(height + NavigatorStateFactory.VIEWER_SIZE, navigator.getState().getTranslation().getY());
        assertEquals(NavigatorStateFactory.ORIGINAL_POSITION.z, navigator.getState().getTranslation().getZ());
    }

    public void testGoHome() {
        // FIXME idem
    }

    public void testResetToNominalViewAngle_WALK_0() {
        navigator.setNavigationMode(NavigationMode.WALK);
        double height = 0;
        landscape3D.setHeight(height);
        double expectedHeight1 = NavigatorStateFactory.VIEWER_SIZE;
        double expectedHeight2 = NavigatorStateFactory.VIEWER_SIZE;
        double expectedHeight3 = NavigatorStateFactory.VIEWER_SIZE;
        internalTestResetToNominalViewAngle(expectedHeight1, expectedHeight2, expectedHeight3);
    }

    public void testResetToNominalViewAngle_WALK_5() {
        navigator.setNavigationMode(NavigationMode.WALK);
        double height = 5;
        landscape3D.setHeight(height);
        double expectedHeight1 = height + NavigatorStateFactory.VIEWER_SIZE;
        double expectedHeight2 = height + NavigatorStateFactory.VIEWER_SIZE;
        double expectedHeight3 = height + NavigatorStateFactory.VIEWER_SIZE;
        internalTestResetToNominalViewAngle(expectedHeight1, expectedHeight2, expectedHeight3);
    }

    public void testResetToNominalViewAngle_FLY_0() {
        navigator.setNavigationMode(NavigationMode.FLY);
        double height = 0;
        landscape3D.setHeight(height);
        double expectedHeight1 = NavigatorStateFactory.VIEWER_SIZE;
        double expectedHeight2 = NavigatorStateFactory.VIEWER_SIZE + BasicNavigator.MOVE_STEP;
        double expectedHeight3 = NavigatorStateFactory.VIEWER_SIZE;
        internalTestResetToNominalViewAngle(expectedHeight1, expectedHeight2, expectedHeight3);
    }

    public void testResetToNominalViewAngle_FLY_5() {
        navigator.setNavigationMode(NavigationMode.FLY);
        double height = 5;
        landscape3D.setHeight(height);
        double expectedHeight1 = height + BasicNavigator.MIN_DISTANCE_FROM_GROUND;
        double expectedHeight2 = height + BasicNavigator.MIN_DISTANCE_FROM_GROUND + BasicNavigator.MOVE_STEP;
        double expectedHeight3 = height + BasicNavigator.MIN_DISTANCE_FROM_GROUND;
        internalTestResetToNominalViewAngle(expectedHeight1, expectedHeight2, expectedHeight3);
    }

    private void internalTestResetToNominalViewAngle(double expectedHeight1, double expectedHeight2,
            double expectedHeight3) {
        navigator.resetToNominalViewAngle();
        assertEquals(NavigatorStateFactory.ORIGINAL_ROTATION_X, navigator.getState().getRotationX());
        assertEquals(NavigatorStateFactory.ORIGINAL_ROTATION_Y, navigator.getState().getRotationY());
        assertEquals(NavigatorStateFactory.ORIGINAL_POSITION.x, navigator.getState().getTranslation().getX());
        assertEquals(expectedHeight1, navigator.getState().getTranslation().getY());
        assertEquals(NavigatorStateFactory.ORIGINAL_POSITION.z, navigator.getState().getTranslation().getZ());

        // move forward and up, then look up and left
        KeyEvent eventKeyFwd = createKeyEvent(KeyEvent.KEY_PRESSED, 0, KeyEvent.VK_UP);
        KeyEvent eventKeyUp = createKeyEvent(KeyEvent.KEY_PRESSED, 0, KeyEvent.VK_PAGE_UP);
        KeyEvent eventKeyRotX = createKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CTRL_MASK, KeyEvent.VK_PAGE_UP);
        KeyEvent eventKeyRotY = createKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CTRL_MASK, KeyEvent.VK_LEFT);
        navigator.processKeyPressedEvent(eventKeyUp);
        navigator.processKeyPressedEvent(eventKeyFwd);
        navigator.processKeyPressedEvent(eventKeyRotX);
        navigator.processKeyPressedEvent(eventKeyRotY);
        assertEquals(NavigatorStateFactory.ORIGINAL_ROTATION_X + BasicNavigator.ROTATION_STEP_KEYBOARD, navigator
                .getState().getRotationX());
        assertEquals(NavigatorStateFactory.ORIGINAL_ROTATION_Y + BasicNavigator.ROTATION_STEP_KEYBOARD, navigator
                .getState().getRotationY());
        assertEquals(NavigatorStateFactory.ORIGINAL_POSITION.x, navigator.getState().getTranslation().getX());
        assertEquals(expectedHeight2, navigator.getState().getTranslation().getY());
        assertEquals(NavigatorStateFactory.ORIGINAL_POSITION.z - BasicNavigator.MOVE_STEP, navigator.getState()
                .getTranslation().getZ());

        // rotations and Y translations go back to default, but X and Z translations should not
        navigator.resetToNominalViewAngle();
        assertEquals(NavigatorStateFactory.ORIGINAL_ROTATION_X, navigator.getState().getRotationX());
        assertEquals(NavigatorStateFactory.ORIGINAL_ROTATION_Y, navigator.getState().getRotationY());
        assertEquals(NavigatorStateFactory.ORIGINAL_POSITION.x, navigator.getState()
                .getTranslation().getX());
        assertEquals(expectedHeight3, navigator.getState().getTranslation().getY());
        assertEquals(NavigatorStateFactory.ORIGINAL_POSITION.z - BasicNavigator.MOVE_STEP, navigator.getState()
                .getTranslation().getZ());
    }

}
