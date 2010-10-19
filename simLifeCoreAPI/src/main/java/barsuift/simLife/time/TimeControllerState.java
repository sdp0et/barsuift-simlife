package barsuift.simLife.time;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.process.SynchronizerState;

@XmlRootElement
public class TimeControllerState implements State {

    private SynchronizerState synchronizer;

    public TimeControllerState() {
        super();
        this.synchronizer = new SynchronizerState();
    }

    public TimeControllerState(SynchronizerState synchronizer) {
        super();
        this.synchronizer = synchronizer;
    }

    public SynchronizerState getSynchronizer() {
        return synchronizer;
    }

    public void setSynchronizer(SynchronizerState synchronizer) {
        this.synchronizer = synchronizer;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((synchronizer == null) ? 0 : synchronizer.hashCode());
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
        TimeControllerState other = (TimeControllerState) obj;
        if (synchronizer == null) {
            if (other.synchronizer != null)
                return false;
        } else
            if (!synchronizer.equals(other.synchronizer))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "TimeControllerState [synchronizer=" + synchronizer + "]";
    }

}
