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
