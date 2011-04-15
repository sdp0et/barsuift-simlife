package barsuift.simLife.j3d.landscape;

import barsuift.simLife.j3d.BoundingBoxState;
import barsuift.simLife.j3d.Tuple3fState;
import barsuift.simLife.j3d.util.BoundingBoxHelper;
import barsuift.simLife.landscape.LandscapeParameters;


public class NavigatorStateFactory {

    public static final float VIEWER_SIZE = 2f;

    public static final double ORIGINAL_ROTATION_X = 0;

    public static final double ORIGINAL_ROTATION_Y = 0;

    /**
     * Creates a navigator at its default position : in the middle of the landscape, at 2 meters high. The navigation
     * mode is the default one.
     */
    public NavigatorState createNavigatorState(LandscapeParameters landscapeParameters) {
        Tuple3fState originalPosition = new Tuple3fState(landscapeParameters.getSize() / 2, VIEWER_SIZE,
                landscapeParameters.getSize() / 2);
        BoundingBoxState bounds = BoundingBoxHelper.createBoundingBox(landscapeParameters);
        return new NavigatorState(originalPosition, originalPosition, ORIGINAL_ROTATION_X, ORIGINAL_ROTATION_Y,
                NavigationMode.DEFAULT, bounds);
    }

}
