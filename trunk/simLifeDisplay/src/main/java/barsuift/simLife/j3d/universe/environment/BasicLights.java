package barsuift.simLife.j3d.universe.environment;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.vecmath.Point3d;

import barsuift.simLife.j3d.util.ColorConstants;


public class BasicLights implements Lights {

    private static BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0, 0), 100);

    private final BranchGroup lightsGroup;

    private final AmbientLight ambientLight;

    private final Sun3D sun3D;

    public BasicLights(Sun3D sun3D) {
        lightsGroup = new BranchGroup();
        ambientLight = new AmbientLight(ColorConstants.grey);
        ambientLight.setInfluencingBounds(bounds);
        lightsGroup.addChild(ambientLight);

        this.sun3D = sun3D;
        lightsGroup.addChild(sun3D.getLight());
    }

    @Override
    public Sun3D getSun3D() {
        return sun3D;
    }

    @Override
    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    @Override
    public BranchGroup getLightsGroup() {
        return lightsGroup;
    }

}
