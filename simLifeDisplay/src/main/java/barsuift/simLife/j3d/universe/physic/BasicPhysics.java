package barsuift.simLife.j3d.universe.physic;

import barsuift.simLife.j3d.universe.Universe3D;


public class BasicPhysics implements Physics {

    // private Universe3D universe3D;

    private Gravity gravity;

    public BasicPhysics(Universe3D universe3D) {
        // this.universe3D = universe3D;
        this.gravity = new BasicGravity(universe3D);
    }

    public Gravity getGravity() {
        return gravity;
    }

    @Override
    public String toString() {
        return "BasicPhysics [gravity=" + gravity + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gravity == null) ? 0 : gravity.hashCode());
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
        BasicPhysics other = (BasicPhysics) obj;
        if (gravity == null) {
            if (other.gravity != null)
                return false;
        } else
            if (!gravity.equals(other.gravity))
                return false;
        return true;
    }

}
