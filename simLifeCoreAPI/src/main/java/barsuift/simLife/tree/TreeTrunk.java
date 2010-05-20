package barsuift.simLife.tree;

import barsuift.simLife.LivingPart;
import barsuift.simLife.j3d.tree.TreeTrunk3D;

public interface TreeTrunk extends LivingPart {

    public float getHeight();

    public float getRadius();

    public TreeTrunkState getState();

    public TreeTrunk3D getTreeTrunkD();

}
