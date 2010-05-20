package barsuift.simLife.environment;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EnvironmentState {

    private SunState sunState;

    public EnvironmentState() {
        this.sunState = new SunState();
    }

    public EnvironmentState(SunState sunState) {
        this.sunState = sunState;
    }

    public EnvironmentState(EnvironmentState copy) {
        this.sunState = new SunState(copy.sunState);
    }

    public void setSunState(SunState sunState) {
        this.sunState = sunState;
    }

    public SunState getSunState() {
        return sunState;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sunState == null) ? 0 : sunState.hashCode());
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
        EnvironmentState other = (EnvironmentState) obj;
        if (sunState == null) {
            if (other.sunState != null)
                return false;
        } else
            if (!sunState.equals(other.sunState))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "BasicEnvironmentState [sunState=" + sunState + "]";
    }

}
