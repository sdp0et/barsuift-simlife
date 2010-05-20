package barsuift.simLife.environment;


public class MockEnvironment implements Environment {

    private Sun sun = new MockSun();

    private EnvironmentState state = new EnvironmentState();

    @Override
    public Sun getSun() {
        return sun;
    }

    public void setSun(Sun sun) {
        this.sun = sun;
    }

    @Override
    public EnvironmentState getState() {
        return state;
    }

    public void setEnvironmentState(EnvironmentState state) {
        this.state = state;
    }

}
