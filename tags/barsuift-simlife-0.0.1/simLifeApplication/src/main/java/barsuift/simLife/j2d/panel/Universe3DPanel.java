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
package barsuift.simLife.j2d.panel;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Group;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JPanel;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import barsuift.simLife.j3d.Axis3DGroup;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.j3d.util.TransformerHelper;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Universe3DPanel extends JPanel {

    private static final long serialVersionUID = -9023573589569686409L;

    private SimpleUniverse simpleU;

    private TransformGroup viewTransform;

    private BranchGroup root;

    private final Axis3DGroup axisGroup = new Axis3DGroup();

    public Universe3DPanel(Universe3D universe3D) {
        Canvas3D canvas3D = createGlobalGraphics();
        simpleU = new SimpleUniverse(canvas3D);
        viewTransform = simpleU.getViewingPlatform().getViewPlatformTransform();
        moveView();

        root = new BranchGroup();
        // allow to add children to the root
        root.setCapability(Group.ALLOW_CHILDREN_EXTEND);
        // allow the remove children from the root
        root.setCapability(Group.ALLOW_CHILDREN_WRITE);
        addNavigators(root);

        root.addChild(universe3D.getUniverseRoot());
        root.compile();
        simpleU.addBranchGraph(root);
    }

    public void addChild(Shape3D child) {
        BranchGroup group = new BranchGroup();
        // allow the child the be removed from its branch group (if the BG is not anymore part of the scene)
        // group.setCapability(Group.ALLOW_CHILDREN_WRITE);
        // allow the branch group to be removed from the root
        group.setCapability(BranchGroup.ALLOW_DETACH);
        group.addChild(child);
        addChild(group);
    }

    public void addChild(BranchGroup child) {
        root.addChild(child);
    }

    public void removeChild(BranchGroup child) {
        root.removeChild(child);
    }

    public void removeChild(Shape3D child) {
        BranchGroup childParent = (BranchGroup) child.getParent();
        root.removeChild(childParent);
        childParent.removeChild(child);
    }

    private void addNavigators(BranchGroup root) {
        KeyNavigatorBehavior navigator = createKeyboardNavigator(viewTransform);
        root.addChild(navigator);

        // MouseRotate mouseRotateNavigator =
        // createMouseRotateNavigator(viewTransform);
        // scene.addChild(mouseRotateNavigator);
        MouseTranslate mouseTranslateNavigator = createMouseTranslateNavigator(viewTransform);
        root.addChild(mouseTranslateNavigator);
        MouseZoom mouseZoomNavigator = createMouseZoomNavigator(viewTransform);
        root.addChild(mouseZoomNavigator);
    }

    private Canvas3D createGlobalGraphics() {
        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(config);
        add("Center", canvas3D);
        return canvas3D;
    }

    private void moveView() {
        // step back 20 meters, and 4 meters right
        // view is at 2 meters high
        Transform3D t3d = TransformerHelper.getTranslationTransform3D(new Vector3d(4, 2, 20));
        viewTransform.setTransform(t3d);
    }

    public KeyNavigatorBehavior createKeyboardNavigator(TransformGroup viewTransform) {
        KeyNavigatorBehavior keyNavigator = new KeyNavigatorBehavior(viewTransform);
        keyNavigator.setSchedulingBounds(new BoundingSphere(new Point3d(0, 0, 0), 1000.0));
        return keyNavigator;
    }

    /*
     * private MouseRotate createMouseRotateNavigator(TransformGroup viewTransform) { MouseRotate mouseRotateNavigator =
     * new MouseRotate(MouseBehavior.INVERT_INPUT); mouseRotateNavigator.setTransformGroup(viewTransform);
     * mouseRotateNavigator.setSchedulingBounds(new BoundingSphere(PointHelper.zeroPoint, 1000.0));
     * mouseRotateNavigator.setFactor(0.003); return mouseRotateNavigator; }
     */

    private MouseTranslate createMouseTranslateNavigator(TransformGroup viewTransform) {
        MouseTranslate mouseTranslateNavigator = new MouseTranslate(MouseBehavior.INVERT_INPUT);
        mouseTranslateNavigator.setTransformGroup(viewTransform);
        mouseTranslateNavigator.setSchedulingBounds(new BoundingSphere(new Point3d(0, 0, 0), 1000.0));
        return mouseTranslateNavigator;
    }

    private MouseZoom createMouseZoomNavigator(TransformGroup viewTransform) {
        MouseZoom mouseZoomNavigator = new MouseZoom(MouseBehavior.INVERT_INPUT);
        mouseZoomNavigator.setTransformGroup(viewTransform);
        mouseZoomNavigator.setSchedulingBounds(new BoundingSphere(new Point3d(0, 0, 0), 1000.0));
        return mouseZoomNavigator;
    }

    /**
     * Add the X-Y-Z axis as 3 segments of 5 meters along X, Y, and Z axis
     */
    public void setAxis() {
        addChild(axisGroup);
    }

    /**
     * Remove the X-Y-Z axis
     */
    public void unsetAxis() {
        removeChild(axisGroup);
    }

}
