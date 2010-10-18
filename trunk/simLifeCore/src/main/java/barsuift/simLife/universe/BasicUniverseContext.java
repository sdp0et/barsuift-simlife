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
package barsuift.simLife.universe;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import barsuift.simLife.InitException;
import barsuift.simLife.j3d.Axis3DGroup;
import barsuift.simLife.j3d.BasicSimLifeCanvas3D;
import barsuift.simLife.j3d.SimLifeCanvas3D;
import barsuift.simLife.time.BasicTimeController;
import barsuift.simLife.time.TimeController;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class BasicUniverseContext implements UniverseContext {

    private static final BoundingSphere BOUNDS_FOR_ALL = new BoundingSphere(new Point3d(0, 0, 0), 1000.0);

    private final UniverseContextState state;

    private boolean axisShowing;

    private final BasicSimLifeCanvas3D canvas3D;

    private final Universe universe;

    private final TimeController timeController;


    private final TransformGroup viewTransform;

    private final BranchGroup root;

    private final Axis3DGroup axisGroup = new Axis3DGroup();

    public BasicUniverseContext(UniverseContextState state) throws InitException {
        this.state = state;
        this.axisShowing = state.isAxisShowing();

        this.universe = new BasicUniverse(state.getUniverseState());
        timeController = new BasicTimeController(universe, state.getTimeControllerState());
        canvas3D = new BasicSimLifeCanvas3D(universe.getFpsCounter(), state.getCanvasState());
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

        // limit to graphic to 40 FPS (interval = 1000ms / 40 = 25)
        simpleU.getViewer().getView().setMinimumFrameCycleTime(25);

        root = new BranchGroup();
        // allow to add children to the root
        root.setCapability(Group.ALLOW_CHILDREN_EXTEND);
        // allow the remove children from the root
        root.setCapability(Group.ALLOW_CHILDREN_WRITE);

        viewTransform = simpleU.getViewingPlatform().getViewPlatformTransform();
        viewTransform.setTransform(new Transform3D(state.getViewerTransform3D()));
        addNavigators();

        root.addChild(universe.getUniverse3D().getUniverseRoot());

        root.compile();
        simpleU.addBranchGraph(root);
        setAxisShowing(state.isAxisShowing());
    }

    @Override
    public SimLifeCanvas3D getCanvas3D() {
        return canvas3D;
    }

    @Override
    public Universe getUniverse() {
        return universe;
    }

    @Override
    public TimeController getTimeController() {
        return timeController;
    }

    @Override
    public void setFpsShowing(boolean fpsShowing) {
        universe.setFpsShowing(fpsShowing);
        canvas3D.setFpsShowing(fpsShowing);
    }

    @Override
    public boolean isFpsShowing() {
        return universe.isFpsShowing() && canvas3D.isFpsShowing();
    }

    @Override
    public void setAxisShowing(boolean axisShowing) {
        if (axisShowing) {
            root.addChild(axisGroup);
        } else {
            root.removeChild(axisGroup);
        }
        this.axisShowing = axisShowing;
    }

    @Override
    public boolean isAxisShowing() {
        return axisShowing;
    }

    @Override
    public void resetToOriginalView() {
        viewTransform.setTransform(new Transform3D(UniverseContextStateFactory.NOMINAL_VIEWER_TRANSFORM));
    }

    @Override
    public void resetToNominalAngleOfView() {
        Transform3D viewingTransform = new Transform3D();
        viewTransform.getTransform(viewingTransform);
        // set viewer angle of view
        Quat4d rotationQuat = new Quat4d();
        viewingTransform.get(rotationQuat);
        rotationQuat.x = 0;
        rotationQuat.z = 0;
        viewingTransform.setRotation(rotationQuat);
        // set viewer position
        Vector3d currentTranslation = new Vector3d();
        viewingTransform.get(currentTranslation);
        currentTranslation.y = 2;
        viewingTransform.setTranslation(currentTranslation);
        viewTransform.setTransform(viewingTransform);
    }

    @Override
    public UniverseContextState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setAxisShowing(axisShowing);
        Transform3D transform3D = new Transform3D();
        viewTransform.getTransform(transform3D);
        double[] matrix = new double[16];
        transform3D.get(matrix);
        state.setViewerTransform3D(matrix);
        canvas3D.synchronize();
        universe.synchronize();
    }

    private void addNavigators() {
        KeyNavigatorBehavior navigator = createKeyboardNavigator();
        root.addChild(navigator);
        // MouseRotate mouseRotateNavigator = createMouseRotateNavigator();
        // root.addChild(mouseRotateNavigator);
        MouseTranslate mouseTranslateNavigator = createMouseTranslateNavigator();
        root.addChild(mouseTranslateNavigator);
        MouseZoom mouseZoomNavigator = createMouseZoomNavigator();
        root.addChild(mouseZoomNavigator);
    }

    private KeyNavigatorBehavior createKeyboardNavigator() {
        KeyNavigatorBehavior keyNavigator = new KeyNavigatorBehavior(viewTransform);
        keyNavigator.setSchedulingBounds(BOUNDS_FOR_ALL);
        return keyNavigator;
    }

    // private MouseRotate createMouseRotateNavigator() {
    // MouseRotate mouseRotateNavigator = new MouseRotate(MouseBehavior.INVERT_INPUT);
    // mouseRotateNavigator.setTransformGroup(viewTransform);
    // mouseRotateNavigator.setSchedulingBounds(BOUNDS_FOR_ALL);
    // mouseRotateNavigator.setFactor(0.003);
    // return mouseRotateNavigator;
    // }

    private MouseTranslate createMouseTranslateNavigator() {
        MouseTranslate mouseTranslateNavigator = new MouseTranslate(MouseBehavior.INVERT_INPUT);
        mouseTranslateNavigator.setTransformGroup(viewTransform);
        mouseTranslateNavigator.setSchedulingBounds(BOUNDS_FOR_ALL);
        return mouseTranslateNavigator;
    }

    private MouseZoom createMouseZoomNavigator() {
        MouseZoom mouseZoomNavigator = new MouseZoom(MouseBehavior.INVERT_INPUT);
        mouseZoomNavigator.setTransformGroup(viewTransform);
        mouseZoomNavigator.setSchedulingBounds(BOUNDS_FOR_ALL);
        return mouseZoomNavigator;
    }

}
