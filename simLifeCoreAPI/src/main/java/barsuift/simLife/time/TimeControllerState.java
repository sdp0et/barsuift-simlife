package barsuift.simLife.time;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.process.SynchronizerState;

@XmlRootElement
public class TimeControllerState implements State {

    private SimLifeDateState date;

    private SynchronizerState synchronizer;

    public TimeControllerState() {
        super();
        this.date = new SimLifeDateState();
        this.synchronizer = new SynchronizerState();
    }

    public TimeControllerState(SimLifeDateState date, SynchronizerState synchronizer) {
        super();
        this.date = date;
        this.synchronizer = synchronizer;
    }

    public SimLifeDateState getDate() {
        return date;
    }

    public void setDate(SimLifeDateState date) {
        this.date = date;
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
        result = prime * result + ((date == null) ? 0 : date.hashCode());
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
        if (date == null) {
            if (other.date != null)
                return false;
        } else
            if (!date.equals(other.date))
                return false;
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
        return "TimeControllerState [date=" + date + ", synchronizer=" + synchronizer + "]";
    }

}
