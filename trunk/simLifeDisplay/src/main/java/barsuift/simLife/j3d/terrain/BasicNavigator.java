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
// TODO 000. 001. create A Landscape class skeleton, and Landscape3D with double:getHeight(x,z) and
// boolean:inLandscape(x,z)
// TODO 001. 001. add landscape.inLandscape(x,z) in the doMove (see terra/src/KeyBehavior, and split into doMove and
// tryMove).
// TODO 001. 001. use a simplified version of the ElevationModel.getElevationAt(x,z) from nav project to adjust the
// y height.
// FIXME 001. 999. once fully completed, unit test
// TODO 001. 002. create a walking mode and a fly mode
// TODO 001. 003. change nav keys : CTRL is for head movement, while basic keys are for body movement.
public class BasicNavigator extends ViewPlatformBehavior implements Persistent<NavigatorState>, Navigator {

    /**
     * Max value in radian for an angle
     */
    private static final double MAX_ANGLE = Math.PI * 2;

    /**
     * Rotates by 3 degrees
     */
    private static final double ROTATION_STEP_KEYBOARD = Math.PI / 60.0;

    /**
     * Rotates by 1 degrees
     */
    private static final double ROTATION_STEP_MOUSE = Math.PI / 180.0;

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


    private final NavigatorState state;

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

    private final WakeupCondition wakeUpCondition;




    private int upMoves = 0;


    public BasicNavigator(NavigatorState state) {
        super();
        this.state = state;
        this.translation = state.getTranslation().toVectorValue();
        rotateX(state.getRotationX());
        rotateY(state.getRotationY());
        // wakeUpCondition = new WakeupOr(new WakeupCriterion[] { new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED),
        // new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED) });
        wakeUpCondition = new WakeupOr(new WakeupCriterion[] { new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED),
                new WakeupOnAWTEvent(KeyEvent.KEY_RELEASED), new WakeupOnAWTEvent(KeyEvent.KEY_TYPED),
                new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED) });
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
            doMove(FWD);
            return;
        }
        if (keycode == KeyEvent.VK_DOWN) {
            doMove(BACK);
            return;
        }
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
     * Moves viewer up or down, left or right
     * 
     * @param keycode
     */
    private void ctrlMove(int keycode) {
        if (keycode == KeyEvent.VK_UP) {
            upMoves++;
            doMove(UP);
            return;
        }
        if (keycode == KeyEvent.VK_DOWN) {
            // TODO 001. 001. remove this code when the height is properly managed
            // don't drop below start height
            if (upMoves > 0) {
                upMoves--;
                doMove(DOWN);
            }
            return;
        }
        if (keycode == KeyEvent.VK_LEFT) {
            doMove(LEFT);
            return;
        }
        if (keycode == KeyEvent.VK_RIGHT) {
            doMove(RIGHT);
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
        // propagateTransforms();
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
        // propagateTransforms();
    }

    private void doMove(Vector3d theMove) {
        Transform3D transform = new Transform3D();
        transform.mul(transformRotationY);
        // FIXME in walking move : do not multiply by X rotation
        transform.mul(transformRotationX);
        // new local move vector
        Vector3d moveVector = new Vector3d(theMove);
        // translates movement based on heading
        transform.transform(moveVector);
        translation.add(moveVector);
        // propagateTransforms();
    }

    private void propagateTransforms() {
        Transform3D transform = new Transform3D();
        transform.set(translation);
        transform.mul(transformRotationY);
        transform.mul(transformRotationX);
        targetTG.setTransform(transform);
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
