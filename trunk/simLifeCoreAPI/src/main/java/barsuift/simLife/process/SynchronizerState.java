package barsuift.simLife.process;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;

@XmlRootElement
public class SynchronizerState implements State {

    private Speed speed;

    public SynchronizerState() {
        super();
        this.speed = Speed.NORMAL;
    }

    public SynchronizerState(Speed speed) {
        super();
        this.speed = speed;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        if (speed != other.speed)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SynchronizerState [speed=" + speed + "]";
    }

}
