package barsuift.simLife.j3d.landscape;

import javax.vecmath.Vector3d;

import barsuift.simLife.CommonParameters;
import barsuift.simLife.j3d.Tuple3dState;


public class NavigatorStateFactory {

    public static final double VIEWER_SIZE = 2;

    /**
     * The original viewer position. Be careful that the Y coordinate may not fit with the landscape. Please adjust the
     * height to the landscape when using this constant.
     */
    public static final Vector3d ORIGINAL_POSITION = new Vector3d();

    public static final double ORIGINAL_ROTATION_X = 0;

    public static final double ORIGINAL_ROTATION_Y = 0;

    /**
     * Creates a navigator at its default position : in the middle of the landscape, at 2 meters high. The navigation
     * mode is the default one.
     */
    public NavigatorState createNavigatorState(CommonParameters parameters) {
        ORIGINAL_POSITION.setX(parameters.getSize() / 2);
        ORIGINAL_POSITION.setY(VIEWER_SIZE);
        ORIGINAL_POSITION.setZ(parameters.getSize() / 2);
        return new NavigatorState(new Tuple3dState(ORIGINAL_POSITION), ORIGINAL_ROTATION_X, ORIGINAL_ROTATION_Y,
                NavigationMode.DEFAULT);
    }

}
