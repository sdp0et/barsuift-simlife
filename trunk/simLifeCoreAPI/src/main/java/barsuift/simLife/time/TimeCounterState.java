package barsuift.simLife.time;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TimeCounterState {

    private int seconds;

    public TimeCounterState() {
        this.seconds = 0;
    }

    public TimeCounterState(int seconds) {
        this.seconds = seconds;
    }

    public TimeCounterState(TimeCounterState copy) {
        this.seconds = copy.seconds;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + seconds;
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
        TimeCounterState other = (TimeCounterState) obj;
        if (seconds != other.seconds)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TimeCounterState [seconds=" + seconds + "]";
    }

}
