package barsuift.simLife.j3d.environment;

import barsuift.simLife.environment.Environment;


public class BasicEnvironment3D implements Environment3D {

    private final Environment3DState state;

    private final Environment environment;

    /**
     * Creates the environment3D with given state
     * 
     * @param state the environment3D state
     * @throws IllegalArgumentException if the given environment3D state is null
     */
    public BasicEnvironment3D(Environment3DState state, Environment environment) {
        if (state == null) {
            throw new IllegalArgumentException("Null environment3D state");
        }
        this.state = state;
        this.environment = environment;
    }


    @Override
    public Sun3D getSun3D() {
        return environment.getSun().getSun3D();
    }

    public Environment3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        // nothing to do
    }


}
