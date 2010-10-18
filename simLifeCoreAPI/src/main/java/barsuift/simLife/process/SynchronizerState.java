package barsuift.simLife.process;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;

@XmlRootElement
public class SynchronizerState implements State {

    private int speed;

    private List<SynchronizedRunnableState> synchroRunnables;

    private List<UnfrequentRunnableState> unfrequentRunnables;

    public SynchronizerState() {
        super();
        this.speed = 1;
        this.synchroRunnables = new ArrayList<SynchronizedRunnableState>();
        this.unfrequentRunnables = new ArrayList<UnfrequentRunnableState>();
    }

    public SynchronizerState(int speed, List<SynchronizedRunnableState> synchroRunnables,
            List<UnfrequentRunnableState> unfrequentRunnables) {
        super();
        this.speed = speed;
        this.synchroRunnables = synchroRunnables;
        this.unfrequentRunnables = unfrequentRunnables;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public List<SynchronizedRunnableState> getSynchronizedRunnables() {
        return synchroRunnables;
    }

    public void setSynchronizedRunnables(List<SynchronizedRunnableState> synchroRunnables) {
        this.synchroRunnables = synchroRunnables;
    }

    public List<UnfrequentRunnableState> getUnfrequentRunnables() {
        return unfrequentRunnables;
    }

    public void setUnfrequentRunnables(List<UnfrequentRunnableState> unfrequentRunnables) {
        this.unfrequentRunnables = unfrequentRunnables;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((synchroRunnables == null) ? 0 : synchroRunnables.hashCode());
        result = prime * result + ((unfrequentRunnables == null) ? 0 : unfrequentRunnables.hashCode());
        result = prime * result + speed;
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
        SynchronizerState other = (SynchronizerState) obj;
        if (synchroRunnables == null) {
            if (other.synchroRunnables != null)
                return false;
        } else
            if (!synchroRunnables.equals(other.synchroRunnables))
                return false;
        if (unfrequentRunnables == null) {
            if (other.unfrequentRunnables != null)
                return false;
        } else
            if (!unfrequentRunnables.equals(other.unfrequentRunnables))
                return false;
        if (speed != other.speed)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SynchronizerState [speed=" + speed + ", synchroRunnables=" + synchroRunnables
                + ", unfrequentRunnables=" + unfrequentRunnables + "]";
    }

}
