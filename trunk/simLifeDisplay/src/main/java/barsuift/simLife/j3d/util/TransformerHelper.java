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
package barsuift.simLife.j3d.util;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import barsuift.simLife.j3d.Axis;

public final class TransformerHelper {

    private TransformerHelper() {
        // private constructor to enforce static access
    }

    public static TransformGroup getTranslationTransformGroup(Vector3d translationVector) {
        return new TransformGroup(getTranslationTransform3D(translationVector));
    }

    public static Transform3D getTranslationTransform3D(Vector3d translationVector) {
        Transform3D translation = new Transform3D();
        translation.setTranslation(translationVector);
        return translation;
    }

    public static TransformGroup getRotationTransformGroup(double rotationAngle, Axis axis) {
        return new TransformGroup(getRotationTransform3D(rotationAngle, axis));
    }

    public static Transform3D getRotationTransform3D(double rotationAngle, Axis axis) {
        Transform3D rotation = new Transform3D();
        switch (axis) {
        case X:
            rotation.rotX(rotationAngle);
            break;
        case Y:
            rotation.rotY(rotationAngle);
            break;
        case Z:
            rotation.rotZ(rotationAngle);
            break;
        }
        return rotation;
    }

    /**
     * 
     * @param transform3D is supposed to be normalized
     * @param axis
     * @return
     */
    public static double getRotationFromTransform(Transform3D transform3D, Axis axis) {
        Quat4d rotationQuat = new Quat4d();
        transform3D.get(rotationQuat);
        double angle = 2 * Math.acos(rotationQuat.w);
        switch (axis) {
        case X:
            if (rotationQuat.x < 0) {
                angle = 2 * Math.PI - angle;
            }
            break;
        case Y:
            if (rotationQuat.y < 0) {
                angle = 2 * Math.PI - angle;
            }
            break;
        case Z:
            if (rotationQuat.z < 0) {
                angle = 2 * Math.PI - angle;
            }
            break;
        }
        return angle;
    }

}
