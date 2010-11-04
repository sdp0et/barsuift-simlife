package barsuift.simLife.j3d.universe;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.process.Synchronizer3DState;

@XmlRootElement
public class Universe3DState implements State {

    private Synchronizer3DState synchronizerState;

    public Universe3DState() {
        super();
        this.synchronizerState = new Synchronizer3DState();
    }

    public Universe3DState(Synchronizer3DState synchronizerState) {
        super();
        this.synchronizerState = synchronizerState;
    }


    public Synchronizer3DState getSynchronizerState() {
        return synchronizerState;
    }


    public void setSynchronizerState(Synchronizer3DState synchronizerState) {
        this.synchronizerState = synchronizerState;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((synchronizerState == null) ? 0 : synchronizerState.hashCode());
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
        Universe3DState other = (Universe3DState) obj;
        if (synchronizerState == null) {
            if (other.synchronizerState != null)
                return false;
        } else
            if (!synchronizerState.equals(other.synchronizerState))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "Universe3DState [synchronizerState=" + synchronizerState + "]";
    }

}
