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
import javax.vecmath.Vector3d;

import barsuift.simLife.j3d.Axis3DGroup;
import barsuift.simLife.j3d.BasicSimLifeCanvas3D;
import barsuift.simLife.j3d.SimLifeCanvas3D;
import barsuift.simLife.j3d.util.TransformerHelper;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;

// TODO 001. store the camera position (and create the Action and menu item)
public class BasicUniverseContext implements UniverseContext {

    private static final BoundingSphere BOUNDS_FOR_ALL = new BoundingSphere(new Point3d(0, 0, 0), 1000.0);

    private final UniverseContextState state;

    private boolean axisShowing;

    private final BasicSimLifeCanvas3D canvas3D;

    private final Universe universe;



    private final SimpleUniverse simpleU;

    private final BranchGroup root;

    private final Axis3DGroup axisGroup = new Axis3DGroup();

    public BasicUniverseContext(UniverseContextState state) {
        this.state = state;
        this.axisShowing = state.isAxisShowing();

        this.universe = new BasicUniverse(state.getUniverseState());
        canvas3D = new BasicSimLifeCanvas3D(universe.getFpsCounter(), state.getCanvasState());
        simpleU = new SimpleUniverse(canvas3D);

        // limit to graphic to 40 FPS (interval = 1000ms / 40 = 25)
        simpleU.getViewer().getView().setMinimumFrameCycleTime(25);

        root = new BranchGroup();
        // allow to add children to the root
        root.setCapability(Group.ALLOW_CHILDREN_EXTEND);
        // allow the remove children from the root
        root.setCapability(Group.ALLOW_CHILDREN_WRITE);

        TransformGroup viewTransform = simpleU.getViewingPlatform().getViewPlatformTransform();
        moveView(viewTransform);
        addNavigators(viewTransform);

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
    public UniverseContextState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setAxisShowing(axisShowing);
        canvas3D.synchronize();
        universe.synchronize();
    }

    private void addNavigators(TransformGroup viewTransform) {
        KeyNavigatorBehavior navigator = createKeyboardNavigator(viewTransform);
        root.addChild(navigator);
        // MouseRotate mouseRotateNavigator = createMouseRotateNavigator(viewTransform);
        // scene.addChild(mouseRotateNavigator);
        MouseTranslate mouseTranslateNavigator = createMouseTranslateNavigator(viewTransform);
        root.addChild(mouseTranslateNavigator);
        MouseZoom mouseZoomNavigator = createMouseZoomNavigator(viewTransform);
        root.addChild(mouseZoomNavigator);
    }

    private void moveView(TransformGroup viewTransform) {
        // step back 20 meters, and 4 meters right
        // view is at 2 meters high
        Transform3D t3d = TransformerHelper.getTranslationTransform3D(new Vector3d(4, 2, 20));
        viewTransform.setTransform(t3d);
    }

    private KeyNavigatorBehavior createKeyboardNavigator(TransformGroup viewTransform) {
        KeyNavigatorBehavior keyNavigator = new KeyNavigatorBehavior(viewTransform);
        keyNavigator.setSchedulingBounds(BOUNDS_FOR_ALL);
        return keyNavigator;
    }

    // private MouseRotate createMouseRotateNavigator(TransformGroup viewTransform) {
    // MouseRotate mouseRotateNavigator = new MouseRotate(MouseBehavior.INVERT_INPUT);
    // mouseRotateNavigator.setTransformGroup(viewTransform);
    // mouseRotateNavigator.setSchedulingBounds(BOUNDS_FOR_ALL);
    // mouseRotateNavigator.setFactor(0.003);
    // return mouseRotateNavigator;
    // }

    private MouseTranslate createMouseTranslateNavigator(TransformGroup viewTransform) {
        MouseTranslate mouseTranslateNavigator = new MouseTranslate(MouseBehavior.INVERT_INPUT);
        mouseTranslateNavigator.setTransformGroup(viewTransform);
        mouseTranslateNavigator.setSchedulingBounds(BOUNDS_FOR_ALL);
        return mouseTranslateNavigator;
    }

    private MouseZoom createMouseZoomNavigator(TransformGroup viewTransform) {
        MouseZoom mouseZoomNavigator = new MouseZoom(MouseBehavior.INVERT_INPUT);
        mouseZoomNavigator.setTransformGroup(viewTransform);
        mouseZoomNavigator.setSchedulingBounds(BOUNDS_FOR_ALL);
        return mouseZoomNavigator;
    }

}
