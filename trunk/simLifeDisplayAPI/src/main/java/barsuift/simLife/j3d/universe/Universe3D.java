package barsuift.simLife.j3d.universe;

import java.util.Set;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;

import barsuift.simLife.j3d.universe.environment.Lights;
import barsuift.simLife.j3d.universe.physic.Physics;

public interface Universe3D {

    public BranchGroup getUniverseRoot();

    public void addElement3D(Node element3D);

    public Set<Node> getElements3D();

    public Physics getPhysics();

    public Lights getLights();

}
