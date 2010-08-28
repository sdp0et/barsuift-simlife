/**
 * barsuift-simlife is a life simulator programm
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
package barsuift.simLife.j3d.universe;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import barsuift.simLife.j3d.Axis3DGroup;
import barsuift.simLife.j3d.MyCanvas3D;
import barsuift.simLife.j3d.util.TransformerHelper;
import barsuift.simLife.universe.Universe;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;

//TODO 001 unit test from Universe3DPanel
public class UniverseContext {

    private static final BoundingSphere BOUNDS_FOR_ALL = new BoundingSphere(new Point3d(0, 0, 0), 1000.0);

    private final Universe universe;

    private final SimpleUniverse simpleU;

    private final MyCanvas3D canvas3D;

    private final BranchGroup root;

    private final Axis3DGroup axisGroup = new Axis3DGroup();

    private boolean showFps;

    public UniverseContext(Universe universe) {
        this.universe = universe;
        showFps = false;
        canvas3D = new MyCanvas3D(this);
        simpleU = new SimpleUniverse(canvas3D);

        // limit to graphic to 50 FPS (interval = 1000ms / 50 = 20)
        simpleU.getViewer().getView().setMinimumFrameCycleTime(20);

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
        setAxis();
    }

    public MyCanvas3D getCanvas3D() {
        return canvas3D;
    }

    public Universe getUniverse() {
        return universe;
    }

    public void showFps(boolean show) {
        showFps = show;
    }

    public boolean isShowFps() {
        return showFps;
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

    /**
     * Add the X-Y-Z axis as 3 segments of 5 meters along X, Y, and Z axis
     */
    public void setAxis() {
        root.addChild(axisGroup);
    }

    /**
     * Remove the X-Y-Z axis
     */
    public void unsetAxis() {
        root.removeChild(axisGroup);
    }
}
