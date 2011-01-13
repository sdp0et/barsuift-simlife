package barsuift.simLife.j3d.landscape;

import javax.vecmath.Vector3f;

import barsuift.simLife.DimensionParameters;
import barsuift.simLife.j3d.BoundingBoxState;
import barsuift.simLife.j3d.Tuple3fState;
import barsuift.simLife.j3d.util.BoundingBoxHelper;


public class NavigatorStateFactory {

    public static final float VIEWER_SIZE = 2f;

    /**
     * The original viewer position. Be careful that the Y coordinate may not fit with the landscape. Please adjust the
     * height to the landscape when using this constant.
     */
    public static final Vector3f ORIGINAL_POSITION = new Vector3f();

    public static final double ORIGINAL_ROTATION_X = 0;

    public static final double ORIGINAL_ROTATION_Y = 0;

    /**
     * Creates a navigator at its default position : in the middle of the landscape, at 2 meters high. The navigation
     * mode is the default one.
     */
    public NavigatorState createNavigatorState(DimensionParameters parameters) {
        ORIGINAL_POSITION.setX(parameters.getSize() / 2);
        ORIGINAL_POSITION.setY(VIEWER_SIZE);
        ORIGINAL_POSITION.setZ(parameters.getSize() / 2);
        BoundingBoxState bounds = BoundingBoxHelper.createBoundingBox(parameters);
        return new NavigatorState(new Tuple3fState(ORIGINAL_POSITION), ORIGINAL_ROTATION_X, ORIGINAL_ROTATION_Y,
                NavigationMode.DEFAULT, bounds);
    }

}
