package barsuift.simLife.universe.physic;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.j3d.universe.physic.Physics3DState;

@XmlRootElement
public class PhysicsState implements State {

    private GravityState gravity;

    private Physics3DState physics3D;

    public PhysicsState() {
        super();
        this.gravity = new GravityState();
        this.physics3D = new Physics3DState();
    }

    public PhysicsState(GravityState gravity, Physics3DState physics3D) {
        super();
        this.gravity = gravity;
        this.physics3D = physics3D;
    }

    public GravityState getGravity() {
        return gravity;
    }

    public void setGravity(GravityState gravity) {
        this.gravity = gravity;
    }

    public Physics3DState getPhysics3D() {
        return physics3D;
    }

    public void setPhysics3D(Physics3DState physics3d) {
        physics3D = physics3d;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gravity == null) ? 0 : gravity.hashCode());
        result = prime * result + ((physics3D == null) ? 0 : physics3D.hashCode());
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
        PhysicsState other = (PhysicsState) obj;
        if (gravity == null) {
            if (other.gravity != null)
                return false;
        } else
            if (!gravity.equals(other.gravity))
                return false;
        if (physics3D == null) {
            if (other.physics3D != null)
                return false;
        } else
            if (!physics3D.equals(other.physics3D))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "PhysicsState [gravity=" + gravity + ", physics3D=" + physics3D + "]";
    }

}
