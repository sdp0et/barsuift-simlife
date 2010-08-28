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
package barsuift.simLife.j3d.helper;

import java.util.Enumeration;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;

import junit.framework.Assert;


public final class Structure3DHelper extends Assert {

    private Structure3DHelper() {
        // private constructor to enforce static access
    }

    @SuppressWarnings("rawtypes")
    public static void assertExactlyOneTransformGroup(BranchGroup branchGroup) {
        Enumeration children = branchGroup.getAllChildren();
        assertTrue(children.hasMoreElements());
        assertTrue(children.nextElement() instanceof TransformGroup);
        assertFalse(children.hasMoreElements());
    }

    @SuppressWarnings("rawtypes")
    public static void assertExactlyOneBranchGroup(TransformGroup transformGroup) {
        Enumeration children = transformGroup.getAllChildren();
        assertTrue(children.hasMoreElements());
        assertTrue(children.nextElement() instanceof BranchGroup);
        assertFalse(children.hasMoreElements());
    }

    @SuppressWarnings("rawtypes")
    public static void assertExactlyOneGroup(TransformGroup transformGroup) {
        Enumeration children = transformGroup.getAllChildren();
        assertTrue(children.hasMoreElements());
        assertTrue(children.nextElement() instanceof Group);
        assertFalse(children.hasMoreElements());
    }

    @SuppressWarnings("rawtypes")
    public static void assertExactlyOneShape3D(BranchGroup branchGroup) {
        Enumeration children = branchGroup.getAllChildren();
        assertTrue(children.hasMoreElements());
        assertTrue(children.nextElement() instanceof Shape3D);
        assertFalse(children.hasMoreElements());
    }

}
