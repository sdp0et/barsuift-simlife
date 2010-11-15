package barsuift.simLife.j3d.universe.physic;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.process.SplitConditionalTaskState;

@XmlRootElement
public class Gravity3DState implements State {

    private SplitConditionalTaskState gravityTask;

    public Gravity3DState() {
        this.gravityTask = new SplitConditionalTaskState();
    }

    public Gravity3DState(SplitConditionalTaskState gravityTask) {
        this.gravityTask = gravityTask;
    }

    public SplitConditionalTaskState getGravityTask() {
        return gravityTask;
    }

    public void setGravityTask(SplitConditionalTaskState gravityTask) {
        this.gravityTask = gravityTask;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gravityTask == null) ? 0 : gravityTask.hashCode());
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
        Gravity3DState other = (Gravity3DState) obj;
        if (gravityTask == null) {
            if (other.gravityTask != null)
                return false;
        } else
            if (!gravityTask.equals(other.gravityTask))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "Gravity3DState [gravityTask=" + gravityTask + "]";
    }

}
