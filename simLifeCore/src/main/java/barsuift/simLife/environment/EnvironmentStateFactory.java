package barsuift.simLife.environment;


public class EnvironmentStateFactory {

    public EnvironmentState createEnvironmentState() {
        SunStateFactory sunStateFactory = new SunStateFactory();
        SunState sunState = sunStateFactory.createSunState();
        return new EnvironmentState(sunState);
    }
}
