package barsuift.simLife.process;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.time.DateHandlerState;

@XmlRootElement
public class SynchronizerState implements State {

    private int speed;

    private DateHandlerState dateHandler;

    public SynchronizerState() {
        super();
        this.speed = 1;
        this.dateHandler = new DateHandlerState();
    }

    public SynchronizerState(int speed, DateHandlerState dateHandler) {
        super();
        this.speed = speed;
        this.dateHandler = dateHandler;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public DateHandlerState getDateHandler() {
        return dateHandler;
    }

    public void setDateHandler(DateHandlerState dateHandler) {
        this.dateHandler = dateHandler;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + speed;
        result = prime * result + ((dateHandler == null) ? 0 : dateHandler.hashCode());
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
        if (speed != other.speed)
            return false;
        if (dateHandler == null) {
            if (other.dateHandler != null)
                return false;
        } else
            if (!dateHandler.equals(other.dateHandler))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "SynchronizerState [speed=" + speed + ", dateHandler=" + dateHandler + "]";
    }

}
