package barsuift.simLife.j3d;

import javax.media.j3d.BranchGroup;


public class Axis3DGroup extends BranchGroup {

    private Axis3D x;

    private Axis3D y;

    private Axis3D z;

    public Axis3DGroup() {
        // allow the branch group to be removed from the root
        setCapability(BranchGroup.ALLOW_DETACH);
        x = new Axis3D(Axis.X);
        y = new Axis3D(Axis.Y);
        z = new Axis3D(Axis.Z);
        addChild(x);
        addChild(y);
        addChild(z);

    }

}
