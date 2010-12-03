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
import javax.vecmath.Vector3d;

import barsuift.simLife.Persistent;
import barsuift.simLife.j3d.Tuple3dState;

import com.sun.j3d.utils.behaviors.vp.ViewPlatformBehavior;


/**
 * Use key presses to move the viewpoint. Movement is restricted to: forward, backwards, move left, move right, rotate
 * left, rotate right, up, down.
 * 
 * This class is widely inspired from KeyBehavior class writtent by Andrew Davison (ad@fivedots.coe.psu.ac.th).
 * 
 */
// TODO 001. 010. find a way to deal with multiple key presses (with combination of KEY_PRESSED and KEY_RELEASED
// FIXME 001. 999. once fully completed, unit test
// FIXME in FLY mode, it is possible to go under the ground in all moves but PG_UP or PG_DOWN
public class BasicNavigator extends ViewPlatformBehavior implements Persistent<NavigatorState>, Navigator {

    /**
     * Always 50 centimeters above the ground in fly mode
     */
    private static final double MIN_DISTANCE_FROM_GROUND = 0.5;

    /**
     * Max value in radian for an angle
     */
    private static final double MAX_ANGLE = Math.PI * 2;

    /**
     * Rotates by 3 degrees
     */
    private static final double ROTATION_STEP_KEYBOARD = Math.toRadians(3);

    /**
     * Rotates by 1 degrees
     */
    private static final double ROTATION_STEP_MOUSE = Math.toRadians(1);

    /**
     * Mouse sensitivity in pixel
     */
    private static final int SENSITIVITY = 3;

    /**
     * Move by 20 centimeters
     */
    private static final double MOVE_STEP = 0.2;

    // movement vectors
    private static final Vector3d FWD = new Vector3d(0, 0, -MOVE_STEP);

    private static final Vector3d BACK = new Vector3d(0, 0, MOVE_STEP);

    private static final Vector3d LEFT = new Vector3d(-MOVE_STEP, 0, 0);

    private static final Vector3d RIGHT = new Vector3d(MOVE_STEP, 0, 0);

    private static final Vector3d UP = new Vector3d(0, MOVE_STEP, 0);

    private static final Vector3d DOWN = new Vector3d(0, -MOVE_STEP, 0);


    private final Landscape3D landscape3D;

    private final NavigatorState state;

    private final Transform3D globalTransform = new Transform3D();

    private Vector3d translation = new Vector3d();

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



    private int upMoves = 0;


    public BasicNavigator(NavigatorState state, Landscape3D landscape3D) {
        super();
        this.state = state;
        this.landscape3D = landscape3D;
        this.translation = state.getTranslation().toVectorValue();
        rotateX(state.getRotationX());
        rotateY(state.getRotationY());
        this.navigationMode = state.getNavigationMode();
        wakeUpCondition = new WakeupOr(new WakeupCriterion[] { new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED),
                new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED) });
        // wakeUpCondition = new WakeupOr(new WakeupCriterion[] { new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED),
        // new WakeupOnAWTEvent(KeyEvent.KEY_RELEASED), new WakeupOnAWTEvent(KeyEvent.KEY_TYPED),
        // new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED) });
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
        AWTEvent[] event;

        while (criteria.hasMoreElements()) {
            wakeup = (WakeupCriterion) criteria.nextElement();
            if (wakeup instanceof WakeupOnAWTEvent) {
                event = ((WakeupOnAWTEvent) wakeup).getAWTEvent();
                for (int i = 0; i < event.length; i++) {
                    if (event[i].getID() == KeyEvent.KEY_PRESSED) {
                        processKeyEvent((KeyEvent) event[i]);
                    }
                    if (event[i].getID() == MouseEvent.MOUSE_DRAGGED) {
                        processMouseEvent((MouseEvent) event[i]);
                    }


                }
            }
        }
        propagateTransforms();
        wakeupOn(wakeUpCondition);
    }

    private void processMouseEvent(MouseEvent e) {
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

    private void processKeyEvent(KeyEvent eventKey) {
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
                upMoves++;
                doAbsoluteMove(UP);
                return;
            }
            if (keycode == KeyEvent.VK_PAGE_DOWN) {
                // Vector3d nextPosition = new Vector3d();
                // nextPosition.add(translation, DOWN);

                // TODO 001. 002. use tryMove when the height is properly managed
                // don't drop below start height
                if (upMoves > 0) {
                    upMoves--;
                    doAbsoluteMove(DOWN);
                }
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

    private void doCheckedMove(Vector3d theMove) {
        // next user position
        Vector3d nextLoc = tryMove(theMove);
        // if not on landscape
        if (!landscape3D.inLandscape(nextLoc.x, nextLoc.z)) {
            return;
        }
        Vector3d actualMove = theMove;
        // adapt to terrain, if in WALK mode
        if (navigationMode == NavigationMode.WALK) {
            double height = landscape3D.getHeight(nextLoc.x, nextLoc.z);
            // create new translation movement, with updated y value
            actualMove = new Vector3d(theMove.x, height - translation.y + NavigatorStateFactory.VIEWER_SIZE, theMove.z);
        }
        // if (navigationMode == NavigationMode.FLY) {
        // double height = landscape3D.getHeight(nextLoc.x, nextLoc.z);
        // System.out.println("height=" + height);
        // if ((nextLoc.y + translation.y) < (height + MIN_DISTANCE_FROM_GROUND)) {
        // // the move make us go under the ground
        // actualMove = new Vector3d(theMove.x, height - translation.y + MIN_DISTANCE_FROM_GROUND, theMove.z);
        // }
        // }
        doMove(actualMove);
    }

    // private void doCheckedAbsoluteMove(Vector3d theMove) {
    // // next user position
    // Vector3d nextLoc = tryAbsoluteMove(theMove);
    // double height = landscape3D.getHeight(nextLoc.x, nextLoc.z);
    // double newY = Math.max(nextLoc.y, height + MIN_DISTANCE_FROM_GROUND);
    // newY -= translation.y;
    // Vector3d moveVector = new Vector3d(nextLoc.x, newY, nextLoc.z);
    // translation.add(moveVector);
    // }

    private Vector3d tryMove(Vector3d theMove) {
        Transform3D transform = new Transform3D();
        transform.mul(transformRotationY);
        if (navigationMode == NavigationMode.FLY) {
            transform.mul(transformRotationX);
        }
        // new local move vector
        Vector3d moveVector = new Vector3d(theMove);
        // translates movement based on heading
        transform.transform(moveVector);
        Vector3d nextPosition = new Vector3d();
        nextPosition.add(translation, moveVector);
        return nextPosition;
    }

    // private Vector3d tryAbsoluteMove(Vector3d theMove) {
    // Vector3d nextPosition = new Vector3d();
    // nextPosition.add(translation, theMove);
    // return nextPosition;
    // }

    private void doMove(Vector3d theMove) {
        Transform3D transform = new Transform3D();
        transform.mul(transformRotationY);
        if (navigationMode == NavigationMode.FLY) {
            transform.mul(transformRotationX);
        }
        // new local move vector
        Vector3d moveVector = new Vector3d(theMove);
        // translates movement based on heading
        transform.transform(moveVector);
        doAbsoluteMove(moveVector);
    }

    private void doAbsoluteMove(Vector3d theMove) {
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
        // if new mode is WALK, then go back to floor
        if (navigationMode == NavigationMode.WALK) {
            translation.y = landscape3D.getHeight(translation.x, translation.z) + NavigatorStateFactory.VIEWER_SIZE;
            propagateTransforms();
        }
    }

    @Override
    public NavigatorState getState() {
        synchronize();
        return state;
    }


    @Override
    public void synchronize() {
        state.setTranslation(new Tuple3dState(translation));
        state.setRotationX(rotationX);
        state.setRotationY(rotationY);
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
        translation = new Vector3d(NavigatorStateFactory.ORIGINAL_POSITION);
        propagateTransforms();
    }

    @Override
    public void resetToNominalViewAngle() {
        rotationX = 0;
        transformRotationX.set(new AxisAngle4d(1.0, 0.0, 0.0, 0));
        rotationY = 0;
        transformRotationY.set(new AxisAngle4d(0.0, 1.0, 0.0, 0));
        translation.y = 2;
        propagateTransforms();
    }

}
