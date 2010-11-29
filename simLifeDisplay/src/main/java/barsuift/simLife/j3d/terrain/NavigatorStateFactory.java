package barsuift.simLife.j3d.terrain;

import javax.vecmath.Vector3d;

import barsuift.simLife.j3d.Tuple3dState;


public class NavigatorStateFactory {

    public static final Vector3d ORIGINAL_POSITION = new Vector3d(4, 2, 20);

    public static final double ORIGINAL_ROTATION_X = 0;

    public static final double ORIGINAL_ROTATION_Y = 0;

    /**
     * Creates a navigator at its default position : 4 meters right, 2 meters high, 20 meters back.
     */
    public NavigatorState createNavigatorState() {
        return new NavigatorState(new Tuple3dState(ORIGINAL_POSITION), ORIGINAL_ROTATION_X, ORIGINAL_ROTATION_Y);
    }

}
