package barsuift.simLife.universe.physic;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.j3d.universe.physic.Gravity3DState;
import barsuift.simLife.tree.TreeLeafState;

@XmlRootElement
public class GravityState implements State {

    private Gravity3DState gravity3D;

    private Set<TreeLeafState> fallingLeaves;

    public GravityState() {
        super();
        gravity3D = new Gravity3DState();
        this.fallingLeaves = new HashSet<TreeLeafState>();
    }

    public GravityState(Gravity3DState gravity3D, Set<TreeLeafState> fallingLeaves) {
        super();
        this.gravity3D = gravity3D;
        this.fallingLeaves = fallingLeaves;
    }

    public Gravity3DState getGravity3D() {
        return gravity3D;
    }

    public void setGravity3D(Gravity3DState gravity3d) {
        gravity3D = gravity3d;
    }

    public Set<TreeLeafState> getFallingLeaves() {
        return fallingLeaves;
    }

    public void setFallingLeaves(Set<TreeLeafState> fallingLeaves) {
        this.fallingLeaves = fallingLeaves;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gravity3D == null) ? 0 : gravity3D.hashCode());
        result = prime * result + ((fallingLeaves == null) ? 0 : fallingLeaves.hashCode());
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
        if (fallingLeaves == null) {
            if (other.fallingLeaves != null)
                return false;
        } else
            if (!fallingLeaves.equals(other.fallingLeaves))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "GravityState [gravity3D=" + gravity3D + ", fallingLeaves=" + fallingLeaves + "]";
    }

}
