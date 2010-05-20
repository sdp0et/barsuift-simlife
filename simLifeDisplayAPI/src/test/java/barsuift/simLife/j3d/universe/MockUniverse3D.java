package barsuift.simLife.j3d.universe;

import java.util.HashSet;
import java.util.Set;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;

import barsuift.simLife.j3d.universe.environment.Lights;
import barsuift.simLife.j3d.universe.environment.MockLights;
import barsuift.simLife.j3d.universe.physic.MockPhysics;
import barsuift.simLife.j3d.universe.physic.Physics;


public class MockUniverse3D implements Universe3D {

    private Set<Node> elements3D = new HashSet<Node>();

    private Lights lights = new MockLights();

    private Physics physics = new MockPhysics();

    private BranchGroup branchGroup = new BranchGroup();

    @Override
    public void addElement3D(Node element3d) {
        elements3D.add(element3d);
    }

    @Override
    public Set<Node> getElements3D() {
        return elements3D;
    }

    @Override
    public Lights getLights() {
        return lights;
    }

    public void setLights(Lights lights) {
        this.lights = lights;
    }

    @Override
    public Physics getPhysics() {
        return physics;
    }

    public void setPhysics(Physics physics) {
        this.physics = physics;
    }

    @Override
    public BranchGroup getUniverseRoot() {
        return branchGroup;
    }

    public void setBranchGroup(BranchGroup branchGroup) {
        this.branchGroup = branchGroup;
    }

}
