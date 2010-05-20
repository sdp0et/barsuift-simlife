package barsuift.simLife.j3d.tree;

import javax.media.j3d.BranchGroup;
import javax.vecmath.Point3d;

import barsuift.simLife.MockObserver;


public class MockTreeLeaf3D extends MockObserver implements TreeLeaf3D {

    private double area = 0;

    private TreeLeaf3DState state = new TreeLeaf3DState();

    private BranchGroup bg = new BranchGroup();

    private Point3d attachPoint = new Point3d();

    private int increaseSizeCalled = 0;

    private boolean isMaxSizeReached = false;

    @Override
    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public Point3d getAttachPoint() {
        return attachPoint;
    }

    public void setAttachPoint(Point3d attachPoint) {
        this.attachPoint = attachPoint;
    }

    @Override
    public TreeLeaf3DState getState() {
        return state;
    }

    public void setState(TreeLeaf3DState state) {
        this.state = state;
    }

    @Override
    public BranchGroup getBranchGroup() {
        return bg;
    }

    public void setBranchGroup(BranchGroup bg) {
        this.bg = bg;
    }

    @Override
    public void increaseSize() {
        increaseSizeCalled++;
    }

    public int getNbnTimesIncreaseSizeCalled() {
        return increaseSizeCalled;
    }

    @Override
    public boolean isMaxSizeReached() {
        return isMaxSizeReached;
    }

    public void setMaxSizeReached(boolean isMaxSizeReached) {
        this.isMaxSizeReached = isMaxSizeReached;
    }

}
