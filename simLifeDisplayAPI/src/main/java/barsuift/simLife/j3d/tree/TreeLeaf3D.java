package barsuift.simLife.j3d.tree;

import java.util.Observer;

import javax.media.j3d.BranchGroup;
import javax.vecmath.Point3d;


public interface TreeLeaf3D extends Observer {

    public double getArea();

    public boolean isMaxSizeReached();

    public void increaseSize();

    public Point3d getAttachPoint();

    public TreeLeaf3DState getState();

    public BranchGroup getBranchGroup();

}