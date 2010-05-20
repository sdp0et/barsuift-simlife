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

    @SuppressWarnings("unchecked")
    public static void assertExactlyOneTransformGroup(BranchGroup branchGroup) {
        Enumeration children = branchGroup.getAllChildren();
        assertTrue(children.hasMoreElements());
        assertTrue(children.nextElement() instanceof TransformGroup);
        assertFalse(children.hasMoreElements());
    }

    @SuppressWarnings("unchecked")
    public static void assertExactlyOneBranchGroup(TransformGroup transformGroup) {
        Enumeration children = transformGroup.getAllChildren();
        assertTrue(children.hasMoreElements());
        assertTrue(children.nextElement() instanceof BranchGroup);
        assertFalse(children.hasMoreElements());
    }

    @SuppressWarnings("unchecked")
    public static void assertExactlyOneGroup(TransformGroup transformGroup) {
        Enumeration children = transformGroup.getAllChildren();
        assertTrue(children.hasMoreElements());
        assertTrue(children.nextElement() instanceof Group);
        assertFalse(children.hasMoreElements());
    }

    @SuppressWarnings("unchecked")
    public static void assertExactlyOneShape3D(BranchGroup branchGroup) {
        Enumeration children = branchGroup.getAllChildren();
        assertTrue(children.hasMoreElements());
        assertTrue(children.nextElement() instanceof Shape3D);
        assertFalse(children.hasMoreElements());
    }

}
