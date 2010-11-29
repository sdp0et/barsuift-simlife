package barsuift.simLife.j3d;

public class SimLifeCanvas3DStateFactory {

    /**
     * Creates a SimLifeCanvas3DState with the following values :
     * <ul>
     * <li>fpsShowing = false</li>
     * </ul>
     * 
     * @return
     */
    public SimLifeCanvas3DState createCanvas3DState() {
        return new SimLifeCanvas3DState(false);
    }

}
