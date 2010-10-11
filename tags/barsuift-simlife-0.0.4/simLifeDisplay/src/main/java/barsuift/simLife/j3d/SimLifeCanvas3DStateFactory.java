package barsuift.simLife.j3d;

public class SimLifeCanvas3DStateFactory {

    /**
     * Creates a random SimLifeCanvas3DState with the following values :
     * <ul>
     * <li>fpsShowing = false</li>
     * </ul>
     * 
     * @return
     */
    public SimLifeCanvas3DState createRandomCanvasState() {
        return new SimLifeCanvas3DState(false);
    }

    /**
     * Creates an empty SimLifeCanvas3DState with the following values :
     * <ul>
     * <li>fpsShowing = false</li>
     * </ul>
     * 
     * @return
     */
    public SimLifeCanvas3DState createEmptyCanvasState() {
        return new SimLifeCanvas3DState(false);
    }

}
