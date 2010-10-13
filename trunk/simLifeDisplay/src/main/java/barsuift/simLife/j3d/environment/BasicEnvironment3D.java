package barsuift.simLife.j3d.environment;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.vecmath.Point3d;

import barsuift.simLife.environment.Environment;
import barsuift.simLife.j3d.util.ColorConstants;


public class BasicEnvironment3D implements Environment3D {

    private static BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0, 0), 100);

    private final Environment3DState state;

    private final Environment environment;

    private final AmbientLight ambientLight;

    private final Group group;

    /**
     * Creates the environment3D with given state
     * 
     * @param state the environment3D state
     * @throws IllegalArgumentException if the given environment3D state is null
     */
    public BasicEnvironment3D(Environment3DState state, Environment environment) {
        if (state == null) {
            throw new IllegalArgumentException("Null environment3D state");
        }
        this.state = state;
        this.environment = environment;
        ambientLight = new AmbientLight(ColorConstants.grey);
        ambientLight.setInfluencingBounds(bounds);
        group = new BranchGroup();
        group.addChild(ambientLight);
        group.addChild(environment.getSun().getSun3D().getLight());
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public Sun3D getSun3D() {
        return environment.getSun().getSun3D();
    }

    public Environment3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        // nothing to do
    }


}
