package barsuift.simLife.j3d.terrain;

import javax.vecmath.Vector3d;

import barsuift.simLife.j3d.Tuple3dState;


public class NavigatorStateFactory {

    public static final double VIEWER_SIZE = 2;

    //TODO 000. check this comment
    /**
     * The original viewer position. Be careful that the Y coordinate may not fit with the landscape. Please adjust the
     * height to the terrain when using this constant.
     */
    public static final Vector3d ORIGINAL_POSITION = new Vector3d(4, VIEWER_SIZE, 20);

    public static final double ORIGINAL_ROTATION_X = 0;

    public static final double ORIGINAL_ROTATION_Y = 0;

    /**
     * Creates a navigator at its default position : 4 meters right, 2 meters high, 20 meters back. The navigation mode
     * is the default one.
     */
    public NavigatorState createNavigatorState() {
        return new NavigatorState(new Tuple3dState(ORIGINAL_POSITION), ORIGINAL_ROTATION_X, ORIGINAL_ROTATION_Y,
                NavigationMode.DEFAULT);
    }

}
