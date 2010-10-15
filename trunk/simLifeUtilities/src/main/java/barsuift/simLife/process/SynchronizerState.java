package barsuift.simLife.process;

import java.util.ArrayList;
import java.util.List;

import barsuift.simLife.State;


public class SynchronizerState implements State {

    private int speed;

    private List<SynchronizedRunnableState> runnables;

    public SynchronizerState() {
        super();
        this.speed = 1;
        this.runnables = new ArrayList<SynchronizedRunnableState>();
    }

    public SynchronizerState(int speed, List<SynchronizedRunnableState> runnables) {
        super();
        this.speed = speed;
        this.runnables = runnables;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public List<SynchronizedRunnableState> getRunnables() {
        return runnables;
    }

    public void setRunnables(List<SynchronizedRunnableState> runnables) {
        this.runnables = runnables;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((runnables == null) ? 0 : runnables.hashCode());
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
        if (runnables == null) {
            if (other.runnables != null)
                return false;
        } else
            if (!runnables.equals(other.runnables))
                return false;
        if (speed != other.speed)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SynchronizerState [speed=" + speed + ", runnables=" + runnables + "]";
    }

}
