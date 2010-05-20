package barsuift.simLife.environment;

public class BasicEnvironment implements Environment {

    private Sun sun;

    /**
     * Creates the environement with given state
     * 
     * @param state the environment state
     * @throws IllegalArgumentException if the given sun state is null
     */
    public BasicEnvironment(EnvironmentState state) {
        if (state == null) {
            throw new IllegalArgumentException("Null environment state");
        }
        sun = new BasicSun(state.getSunState());
    }

    public Sun getSun() {
        return sun;
    }

    public EnvironmentState getState() {
        return new EnvironmentState(sun.getState());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sun == null) ? 0 : sun.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BasicEnvironment other = (BasicEnvironment) obj;
        if (sun == null) {
            if (other.sun != null)
                return false;
        } else
            if (!sun.equals(other.sun))
                return false;
        return true;
    }

    /**
     * Return a String representation of the Environment, in the form
     * 
     * <pre>
     * Environment [
     *    sun=Sun [luminosity=xx.00%]
     * ]
     * </pre>
     * 
     * @return
     */
    @Override
    public String toString() {
        return "Environment [\n\tsun=" + sun + "\n]";
    }

}
