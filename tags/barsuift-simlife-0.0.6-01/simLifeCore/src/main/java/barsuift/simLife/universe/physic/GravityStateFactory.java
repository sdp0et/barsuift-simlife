package barsuift.simLife.universe.physic;

import java.util.HashSet;
import java.util.Set;

import barsuift.simLife.j3d.universe.physic.Gravity3DState;
import barsuift.simLife.j3d.universe.physic.Gravity3DStateFactory;
import barsuift.simLife.tree.TreeLeafState;


public class GravityStateFactory {

    public GravityState createGravityState() {
        Gravity3DStateFactory gravity3DFactory = new Gravity3DStateFactory();
        Gravity3DState gravity3D = gravity3DFactory.createGravity3DState();
        Set<TreeLeafState> fallingLeaves = new HashSet<TreeLeafState>();
        return new GravityState(gravity3D, fallingLeaves);
    }

}
