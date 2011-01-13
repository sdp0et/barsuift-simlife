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

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.media.j3d.Transform3D;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOr;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Vector3f;

import barsuift.simLife.Persistent;
import barsuift.simLife.j3d.Tuple3fState;

import com.sun.j3d.utils.behaviors.vp.ViewPlatformBehavior;


/**
 * Use key presses to move the viewpoint. Movement is restricted to:
 * <ul>
 * <li>move forward</li>
 * <li>move backward</li>
 * <li>translate left</li>
 * <li>translate right</li>
 * <li>move up (only in FLY mode, and only above the ground))</li>
 * <li>move down (only in FLY mode)</li>
 * <li>rotate left</li>
 * <li>rotate right</li>
 * <li>rotate up</li>
 * <li>rotate down</li>
 * </ul>
 * 
 * This class implements landscape following. Moreover, it has 2 navigations modes :
 * <ul>
 * <li>WALK : landscape following, always 2 meters above the ground, unable to move up or down</li>
 * <li>FLY : ability to move up or down, but never below 50 cm above the ground</li>
 * </ul>
 * 
 * This class is inspired from KeyBehavior class written by Andrew Davison (ad@fivedots.coe.psu.ac.th), with additions
 * from FlyingPlatform, written by Mark Pendergast.
 * 
 */
// TODO 030. add a "Show position" menu item to display current position (same style as "Show FPS")
public class BasicNavigator extends ViewPlatformBehavior implements Persistent<NavigatorState>, Navigator {

    /**
     * Always 50 centimeters above the ground in fly mode
     */
    static final float MIN_DISTANCE_FROM_GROUND = 0.5f;

    /**
     * Max value in radian for an angle
     */
    private static final double MAX_ANGLE = Math.PI * 2;

    /**
     * Rotates by 3 degrees
     */
    static final double ROTATION_STEP_KEYBOARD = Math.toRadians(3);

    /**
     * Rotates by 1 degrees
     */
    static final double ROTATION_STEP_MOUSE = Math.toRadians(1);

    /**
     * Mouse sensitivity in pixel
     */
    static final int SENSITIVITY = 3;

    /**
     * Move by 20 centimeters
     */
    static final float MOVE_STEP = 0.2f;

    // movement vectors
    private static final Vector3f FWD = new Vector3f(0, 0, -MOVE_STEP);

    private static final Vector3f BACK = new Vector3f(0, 0, MOVE_STEP);

    private static final Vector3f LEFT = new Vector3f(-MOVE_STEP, 0, 0);

    private static final Vector3f RIGHT = new Vector3f(MOVE_STEP, 0, 0);

    private static final Vector3f UP = new Vector3f(0, MOVE_STEP, 0);

    private static final Vector3f DOWN = new Vector3f(0, -MOVE_STEP, 0);


    private final Landscape3D landscape3D;

    private final NavigatorState state;

    private final Transform3D globalTransform = new Transform3D();

    private Vector3f translation = new Vector3f();

    /**
     * current X rotation in radian
     */
    private double rotationX = 0;

    private final Transform3D transformRotationX = new Transform3D();

    /**
     * current Y rotation in radian
     */
    private double rotationY = 0;

    private final Transform3D transformRotationY = new Transform3D();

    private int oldx = 0;

    private int oldy = 0;

    private NavigationMode navigationMode;

    private final WakeupCondition wakeUpCondition;

    public BasicNavigator(NavigatorState state, Landscape3D landscape3D) {
        super();
        this.state = state;
        this.landscape3D = landscape3D;
        this.translation = state.getTranslation().toVectorValue();
        rotateX(state.getRotationX());
        rotateY(state.getRotationY());
        this.navigationMode = state.getNavigationMode();
        adjustHeight();
        // there is no need to wake up on KEY_RELEASED criterion, as the AWT model won't send anymore KEY_PRESSED event
        // on such case :
        // 1. press a key
        // 2. press another key
        // 3. release the second key
        // 4. here AWT won't send any more events, even if the first key is still pressed
        wakeUpCondition = new WakeupOr(new WakeupCriterion[] { new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED),
                new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED) });
        // wakeUpCondition = new WakeupOr(new WakeupCriterion[] { new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED),
        // new WakeupOnAWTEvent(KeyEvent.KEY_RELEASED), new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED) });
        setSchedulingBounds(state.getBounds().toBoundingBox());
    }

    public void initialize() {
        wakeupOn(wakeUpCondition);
        propagateTransforms();
    }

    /**
     * respond to a key press
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void processStimulus(Enumeration criteria) {
        WakeupCriterion wakeup;
        AWTEvent[] events;
        while (criteria.hasMoreElements()) {
            wakeup = (WakeupCriterion) criteria.nextElement();
            if (wakeup instanceof WakeupOnAWTEvent) {
                events = ((WakeupOnAWTEvent) wakeup).getAWTEvent();
                for (int i = 0; i < events.length; i++) {
                    AWTEvent event = events[i];
                    if (event.getID() == KeyEvent.KEY_PRESSED) {
                        processKeyPressedEvent((KeyEvent) event);
                    }
                    if (event.getID() == MouseEvent.MOUSE_DRAGGED) {
                        processMouseEvent((MouseEvent) event);
                    }


                }
            }
        }
        propagateTransforms();
        wakeupOn(wakeUpCondition);
    }

    void processMouseEvent(MouseEvent e) {
        int mods = e.getModifiersEx();

        int x = e.getX();
        int y = e.getY();

        //
        // skip the event if it moved just a little
        //
        if (Math.abs(y - oldy) < SENSITIVITY && Math.abs(x - oldx) < SENSITIVITY)
            return;

        //
        // process right button down
        //
        if ((mods & MouseEvent.BUTTON3_DOWN_MASK) != 0) {
            // mouse moves down the screen
            if (y > oldy) {
                rotateX(-ROTATION_STEP_MOUSE);
            }
            // mouse moves up the screen
            if (y < oldy) {
                rotateX(ROTATION_STEP_MOUSE);
            }
            // mouse move right
            if (x > oldx) {
                rotateY(-ROTATION_STEP_MOUSE);
            }
            // mouse move left
            if (x < oldx) {
                rotateY(ROTATION_STEP_MOUSE);
            }
        }
        // save for comparison on next mouse move
        oldx = x;
        oldy = y;
    }

    void processKeyPressedEvent(KeyEvent eventKey) {
        int keyCode = eventKey.getKeyCode();
        if (eventKey.isControlDown()) {
            // <ctrl> + key
            ctrlMove(keyCode);
        } else {
            standardMove(keyCode);
        }
    }

    /**
     * Make viewer moves forward or backward; rotate left or right
     * 
     * @param keycode
     */
    private void standardMove(int keycode) {
        if (keycode == KeyEvent.VK_UP) {
            doCheckedMove(FWD);
            return;
        }
        if (keycode == KeyEvent.VK_DOWN) {
            doCheckedMove(BACK);
            return;
        }
        if (keycode == KeyEvent.VK_LEFT) {
            doCheckedMove(LEFT);
            return;
        }
        if (keycode == KeyEvent.VK_RIGHT) {
            doCheckedMove(RIGHT);
            return;
        }
        // only available in FLY mode
        if (navigationMode == NavigationMode.FLY) {
            if (keycode == KeyEvent.VK_PAGE_UP) {
                doCheckedAbsoluteMove(UP);
                return;
            }
            if (keycode == KeyEvent.VK_PAGE_DOWN) {
                doCheckedAbsoluteMove(DOWN);
                return;
            }
        }
    }

    /**
     * Moves viewer up or down, left or right
     * 
     * @param keycode
     */
    private void ctrlMove(int keycode) {
        if (keycode == KeyEvent.VK_LEFT) {
            rotateY(ROTATION_STEP_KEYBOARD);
            return;
        }
        if (keycode == KeyEvent.VK_RIGHT) {
            rotateY(-ROTATION_STEP_KEYBOARD);
            return;
        }
        if (keycode == KeyEvent.VK_PAGE_UP) {
            rotateX(ROTATION_STEP_KEYBOARD);
            return;
        }
        if (keycode == KeyEvent.VK_PAGE_DOWN) {
            rotateX(-ROTATION_STEP_KEYBOARD);
            return;
        }
    }

    /**
     * rotate about y-axis by radians
     * 
     * @param radians
     */
    private void rotateY(double radians) {
        rotationY += radians;
        if (rotationY >= MAX_ANGLE)
            rotationY -= MAX_ANGLE;
        if (rotationY < 0)
            rotationY += MAX_ANGLE;

        transformRotationY.set(new AxisAngle4d(0.0, 1.0, 0.0, rotationY));
    }

    /**
     * rotate about x-axis by radians
     * 
     * @param radians
     */
    private void rotateX(double radians) {
        rotationX += radians;
        if (rotationX >= MAX_ANGLE)
            rotationX -= MAX_ANGLE;
        if (rotationX < 0)
            rotationX += MAX_ANGLE;

        transformRotationX.set(new AxisAngle4d(1.0, 0.0, 0.0, rotationX));
    }

    private void doCheckedMove(Vector3f theMove) {
        // next user position
        Vector3f nextLoc = tryMove(theMove);
        // if not on landscape
        if (!landscape3D.inLandscape(nextLoc.x, nextLoc.z)) {
            return;
        }
        float height = landscape3D.getHeight(nextLoc.x, nextLoc.z);
        Vector3f actualMove = theMove;
        // adapt to landscape, if in WALK mode
        if (navigationMode == NavigationMode.WALK) {
            // create new translation movement, with updated y value
            actualMove = new Vector3f(theMove.x, height - translation.y + NavigatorStateFactory.VIEWER_SIZE, theMove.z);
        }
        if (navigationMode == NavigationMode.FLY) {
            if ((nextLoc.y) < (height + MIN_DISTANCE_FROM_GROUND)) {
                // the move make us go under the ground
                actualMove = new Vector3f(theMove.x, height - translation.y + MIN_DISTANCE_FROM_GROUND, theMove.z);
            }
        }
        doMove(actualMove);
    }

    private void doCheckedAbsoluteMove(Vector3f theMove) {
        // next user position
        Vector3f nextLoc = tryAbsoluteMove(theMove);
        // no need to check if still in landscape, as we are only getting up or down along the Y axis.
        float height = landscape3D.getHeight(nextLoc.x, nextLoc.z);
        float newY = Math.max(nextLoc.y, height + MIN_DISTANCE_FROM_GROUND);
        newY -= translation.y;
        Vector3f moveVector = new Vector3f(theMove.x, newY, theMove.z);
        translation.add(moveVector);
    }

    private Vector3f tryMove(Vector3f theMove) {
        Transform3D transform = new Transform3D();
        transform.mul(transformRotationY);
        if (navigationMode == NavigationMode.FLY) {
            transform.mul(transformRotationX);
        }
        // new local move vector
        Vector3f moveVector = new Vector3f(theMove);
        // translates movement based on heading
        transform.transform(moveVector);
        Vector3f nextPosition = new Vector3f();
        nextPosition.add(translation, moveVector);
        return nextPosition;
    }

    private Vector3f tryAbsoluteMove(Vector3f theMove) {
        Vector3f nextPosition = new Vector3f();
        nextPosition.add(translation, theMove);
        return nextPosition;
    }

    private void doMove(Vector3f theMove) {
        Transform3D transform = new Transform3D();
        transform.mul(transformRotationY);
        if (navigationMode == NavigationMode.FLY) {
            transform.mul(transformRotationX);
        }
        // new local move vector
        Vector3f moveVector = new Vector3f(theMove);
        // translates movement based on heading
        transform.transform(moveVector);
        doAbsoluteMove(moveVector);
    }

    private void doAbsoluteMove(Vector3f theMove) {
        translation.add(theMove);
    }

    private void propagateTransforms() {
        globalTransform.set(translation);
        globalTransform.mul(transformRotationY);
        globalTransform.mul(transformRotationX);
        targetTG.setTransform(globalTransform);
    }

    public NavigationMode getNavigationMode() {
        return navigationMode;
    }

    public void setNavigationMode(NavigationMode navigationMode) {
        this.navigationMode = navigationMode;
        adjustHeight();
        propagateTransforms();
    }

    private void adjustHeight() {
        switch (navigationMode) {
        case FLY:
            adjustHeightToFlyMode();
            break;
        case WALK:
            adjustHeightToWalkMode();
            break;
        }
    }

    private void adjustHeightToWalkMode() {
        translation.y = landscape3D.getHeight(translation.x, translation.z) + NavigatorStateFactory.VIEWER_SIZE;
    }

    private void adjustHeightToFlyMode() {
        float minHeight = landscape3D.getHeight(translation.x, translation.z) + MIN_DISTANCE_FROM_GROUND;
        translation.y = Math.max(minHeight, translation.y);
    }

    @Override
    public NavigatorState getState() {
        synchronize();
        return state;
    }


    @Override
    public void synchronize() {
        state.setTranslation(new Tuple3fState(translation));
        state.setRotationX(rotationX);
        state.setRotationY(rotationY);
        state.setNavigationMode(navigationMode);
    }

    public void goHome() {
        resetToOriginalPosition();
    }

    @Override
    public void resetToOriginalPosition() {
        rotationX = NavigatorStateFactory.ORIGINAL_ROTATION_X;
        transformRotationX.set(new AxisAngle4d(1.0, 0.0, 0.0, rotationX));
        rotationY = NavigatorStateFactory.ORIGINAL_ROTATION_Y;
        transformRotationY.set(new AxisAngle4d(0.0, 1.0, 0.0, rotationY));
        translation = new Vector3f(NavigatorStateFactory.ORIGINAL_POSITION);
        adjustHeight();
        propagateTransforms();
    }

    @Override
    public void resetToNominalViewAngle() {
        rotationX = NavigatorStateFactory.ORIGINAL_ROTATION_X;
        transformRotationX.set(new AxisAngle4d(1.0, 0.0, 0.0, rotationX));
        rotationY = NavigatorStateFactory.ORIGINAL_ROTATION_Y;
        transformRotationY.set(new AxisAngle4d(0.0, 1.0, 0.0, rotationY));
        translation.y = NavigatorStateFactory.VIEWER_SIZE;
        adjustHeight();
        propagateTransforms();
    }

}
