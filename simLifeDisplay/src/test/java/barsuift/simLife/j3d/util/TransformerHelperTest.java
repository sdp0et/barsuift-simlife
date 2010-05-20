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

import junit.framework.TestCase;
import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.Axis;


public class TransformerHelperTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetRotationFromTransformX() {
        Transform3D transform3D = new Transform3D();
        double rotationAngle = Randomizer.randomRotation();
        transform3D.rotX(rotationAngle);
        double angle = TransformerHelper.getRotationFromTransform(transform3D, Axis.X);
        assertEquals(rotationAngle, angle, 0.000001);
    }

    public void testGetRotationFromTransformY() {
        Transform3D transform3D = new Transform3D();
        double rotationAngle = Randomizer.randomRotation();
        transform3D.rotY(rotationAngle);
        double angle = TransformerHelper.getRotationFromTransform(transform3D, Axis.Y);
        assertEquals(rotationAngle, angle, 0.000001);
    }

    public void testGetRotationFromTransformZ() {
        Transform3D transform3D = new Transform3D();
        double rotationAngle = Randomizer.randomRotation();
        transform3D.rotZ(rotationAngle);
        double angle = TransformerHelper.getRotationFromTransform(transform3D, Axis.Z);
        assertEquals(rotationAngle, angle, 0.000001);
    }

}
