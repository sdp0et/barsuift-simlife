package barsuift.simLife.j3d.tree;

import javax.media.j3d.Group;

import com.sun.j3d.utils.geometry.Cylinder;

public interface TreeTrunk3D {

    public Cylinder getTrunk();

    public TreeTrunk3DState getState();

    public Group getGroup();

}
