package barsuift.simLife.tree;

import java.util.Comparator;

import barsuift.simLife.j3d.util.DistanceHelper;


public class TreeLeafComparator implements Comparator<TreeLeaf> {

    /**
     * Returns the difference of distance of the 2 arguments from the origin.
     * <p>
     * <ul>
     * <li>If <code>o1</code> is closer to the origin than <code>o2</code>, it returns a negative value</li>
     * <li>If <code>o1</code> is at the same distance from the origin as <code>o2</code>, it returns 0</li>
     * <li>If <code>o1</code> is more far than <code>o2</code> from the origin point, it returns a positive value</li>
     * </ul>
     * </p>
     */
    @Override
    public int compare(TreeLeaf o1, TreeLeaf o2) {
        // distances are computed in millimeters for more precision
        double distance1 = DistanceHelper.distanceFromOrigin(o1.getTreeLeaf3D().getAttachPoint()) * 1000;
        double distance2 = DistanceHelper.distanceFromOrigin(o2.getTreeLeaf3D().getAttachPoint()) * 1000;
        return (int) (distance1 - distance2);
    }

}
