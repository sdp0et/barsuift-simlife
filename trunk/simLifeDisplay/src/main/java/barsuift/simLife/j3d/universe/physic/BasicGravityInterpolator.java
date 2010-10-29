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
package barsuift.simLife.j3d.universe.physic;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Interpolator;
import javax.media.j3d.Node;
import javax.media.j3d.RotPosPathInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.j3d.util.ProjectionHelper;

// TODO 001. Be able to stop and change speed of interpolators
/*
 * Implementation note : An interpolator must not last more than one cycle, or it could be unsynchronized with 'core'
 * processes. As a consequence, the alpha in the interpolator must always be set to increasingAlphaDuration=100 ms (1
 * cycle)
 * 
 * Implementation note : As the alpha duration is always one cycle, changing its speed consists in setting the
 * increasingAlphaDuration to 100ms (speed=1), 10ms (speed=10), or 1ms (speed=100).
 * 
 * To Do :
 * 
 * 1. Create an InterpolatorWrapper class, with a state storing the required information (What are the required
 * information ??). The wrapper must have start, stop and oneStep methods. The wrapper should also take a parameter to
 * know how many cycles the interpolators should last. As a consequence, it must recreate a new interpolator at each
 * cycle or refire the enable conditions of the existing one so that it can continue on the next cycle. When making this
 * decision, take into account how this will behave regarding the reload of the wrapper from its state.
 * 
 * 2. Create a Synchronizer3D to store the interpolatorWrappers. The Synchronizer3D must have start, stop and oneStep
 * methods. It also put the wrapped interpolator in its branch group, which is itself part of the Universe3D root.
 * 
 * 3. To synchronize the CoreSynchronizer and the Synchronizer3D, their BarrierTask should wait on a main barrier which
 * only have the two of them as parties. This implies that a MainSynchronizer exists in simlifeApplication module.
 * 
 * 5. (or 6. ??) the gravity should keeps its falling elements, and the Universe should only have the fallen leaves.
 * 
 * 6. (or 5. ??) integrate the existing gravity interpolator into the Gravity3D framework
 * 
 * x. all graphic changes should use an InterpolatorWrapper
 * 
 * x.1. Leaf color
 * 
 * x.2. Gravity
 */
public class BasicGravityInterpolator implements GravityInterpolator {

    private Universe3D universe3D;

    public BasicGravityInterpolator(Universe3D universe3D) {
        this.universe3D = universe3D;
    }

    public void fall(BranchGroup groupToFall) {
        TransformGroup tg = (TransformGroup) groupToFall.getChild(0);
        Node node = tg.getChild(0);
        // get the global transform before detaching to get all the transforms along the way
        // the group will not be attached locally anymore, but at the root,
        // so we need to get the global transform.
        Transform3D transform3D = getGlobalTransform3D(node);
        // we need to detach before changing the global transform, or it will move the group too far
        groupToFall.detach();
        tg.setTransform(transform3D);
        Interpolator gravity = createGravityInterpolator(tg);
        addToUniverse(groupToFall, gravity);
    }

    private void addToUniverse(BranchGroup groupToFall, Interpolator gravity) {
        BranchGroup bg = new BranchGroup();
        bg.addChild(groupToFall);
        bg.addChild(gravity);

        universe3D.addElement3D(bg);
    }

    /**
     * Get the composite of all Transforms for the given Node in the scene graph as a new Transform3D object
     * 
     * @param node the node to get transforms
     * @return the global transform
     */
    private Transform3D getGlobalTransform3D(Node node) {
        Transform3D transform = new Transform3D();
        node.getLocalToVworld(transform);
        return transform;
    }

    private Interpolator createGravityInterpolator(TransformGroup newTransformGroup) {
        Vector3f translationVector = new Vector3f();
        Transform3D transform = new Transform3D();
        newTransformGroup.getTransform(transform);
        transform.get(translationVector);
        Quat4f rotationQuat = new Quat4f();
        transform.get(rotationQuat);
        float[] knots = new float[] { 0, 1 };
        Point3f position1 = new Point3f(translationVector.getX(), translationVector.getY(), translationVector.getZ());
        Point3f position2 = ProjectionHelper.getProjectionPoint(position1);
        Point3f[] positions = new Point3f[] { position1, position2 };
        Quat4f[] rotations = new Quat4f[] { rotationQuat, rotationQuat };
        Alpha alpha = new Alpha(1, (long) (translationVector.getY() * 1000));
        alpha.setStartTime(System.currentTimeMillis());
        RotPosPathInterpolator gravity = new RotPosPathInterpolator(alpha, newTransformGroup, new Transform3D(), knots,
                rotations, positions);
        gravity.setSchedulingBounds(new BoundingSphere());
        return gravity;
    }

}
