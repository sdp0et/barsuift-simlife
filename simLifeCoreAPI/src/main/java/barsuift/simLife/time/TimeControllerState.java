package barsuift.simLife.time;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.process.SynchronizerState;

@XmlRootElement
public class TimeControllerState implements State {

    private SynchronizerState synchronizer;

    private SimLifeCalendarState calendar;

    public TimeControllerState() {
        super();
        this.synchronizer = new SynchronizerState();
        this.calendar = new SimLifeCalendarState();
    }

    public TimeControllerState(SynchronizerState synchronizer, SimLifeCalendarState calendar) {
        super();
        this.synchronizer = synchronizer;
        this.calendar = calendar;
    }

    public SynchronizerState getSynchronizer() {
        return synchronizer;
    }

    public void setSynchronizer(SynchronizerState synchronizer) {
        this.synchronizer = synchronizer;
    }

    public SimLifeCalendarState getCalendar() {
        return calendar;
    }

    public void setCalendar(SimLifeCalendarState calendar) {
        this.calendar = calendar;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((calendar == null) ? 0 : calendar.hashCode());
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
        if (calendar == null) {
            if (other.calendar != null)
                return false;
        } else
            if (!calendar.equals(other.calendar))
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
        return "TimeControllerState [synchronizer=" + synchronizer + ", calendar=" + calendar + "]";
    }

}
