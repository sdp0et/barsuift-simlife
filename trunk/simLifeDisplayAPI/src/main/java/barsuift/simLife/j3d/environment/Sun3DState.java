package barsuift.simLife.j3d.environment;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.j3d.BoundingBoxState;

@XmlRootElement
public class Sun3DState implements State {

    private BoundingBoxState bounds;

    public Sun3DState() {
        this.bounds = new BoundingBoxState();
    }

    public Sun3DState(BoundingBoxState bounds) {
        this.bounds = bounds;
    }

    public BoundingBoxState getBounds() {
        return bounds;
    }

    public void setBounds(BoundingBoxState bounds) {
        this.bounds = bounds;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bounds == null) ? 0 : bounds.hashCode());
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
        Sun3DState other = (Sun3DState) obj;
        if (bounds == null) {
            if (other.bounds != null)
                return false;
        } else
            if (!bounds.equals(other.bounds))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "Sun3DState [bounds=" + bounds + "]";
    }

}
