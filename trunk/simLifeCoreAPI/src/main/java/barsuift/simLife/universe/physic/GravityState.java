package barsuift.simLife.universe.physic;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.j3d.universe.physic.Gravity3DState;

@XmlRootElement
public class GravityState implements State {

    private Gravity3DState gravity3D;

    public GravityState() {
        super();
        gravity3D = new Gravity3DState();
    }

    public GravityState(Gravity3DState gravity3D) {
        super();
        this.gravity3D = gravity3D;
    }

    public Gravity3DState getGravity3D() {
        return gravity3D;
    }

    public void setGravity3D(Gravity3DState gravity3d) {
        gravity3D = gravity3d;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gravity3D == null) ? 0 : gravity3D.hashCode());
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
        GravityState other = (GravityState) obj;
        if (gravity3D == null) {
            if (other.gravity3D != null)
                return false;
        } else
            if (!gravity3D.equals(other.gravity3D))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "GravityState [gravity3D=" + gravity3D + "]";
    }

}
