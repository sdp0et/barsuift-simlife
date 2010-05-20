package barsuift.simLife.environment;


public class BasicEnvironmentFactory {

    public Environment create() {
        EnvironmentStateFactory envStateFactory = new EnvironmentStateFactory();
        EnvironmentState envState = envStateFactory.createEnvironmentState();
        Environment environment = new BasicEnvironment(envState);
        return environment;
    }

}
