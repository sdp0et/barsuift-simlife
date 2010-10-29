package barsuift.simLife.process;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.time.DateHandlerState;

@XmlRootElement
public class SynchronizerState implements State {

    private Speed speed;

    private DateHandlerState dateHandler;

    public SynchronizerState() {
        super();
        this.speed = Speed.NORMAL;
        this.dateHandler = new DateHandlerState();
    }

    public SynchronizerState(Speed speed, DateHandlerState dateHandler) {
        super();
        this.speed = speed;
        this.dateHandler = dateHandler;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
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
        result = prime * result + ((dateHandler == null) ? 0 : dateHandler.hashCode());
        result = prime * result + ((speed == null) ? 0 : speed.hashCode());
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
        if (dateHandler == null) {
            if (other.dateHandler != null)
                return false;
        } else
            if (!dateHandler.equals(other.dateHandler))
                return false;
        if (speed != other.speed)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SynchronizerState [speed=" + speed + ", dateHandler=" + dateHandler + "]";
    }

}
