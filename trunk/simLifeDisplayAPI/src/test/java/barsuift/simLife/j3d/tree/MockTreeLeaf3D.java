/**
 * barsuift-simlife is a life simulator program
 * 
 * Copyright (C) 2010 Cyrille GACHOT
 * 
 * This file is part of barsuift-simlife.
 * 
 * barsuift-simlife is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * barsuift-simlife is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with barsuift-simlife. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package barsuift.simLife.j3d.tree;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;

import barsuift.simLife.message.MockSubscriber;


public class MockTreeLeaf3D extends MockSubscriber implements TreeLeaf3D {

    private double area;

    private TreeLeaf3DState state;

    private BranchGroup bg;

    private Point3d attachPoint;

    private int increaseSizeCalled;

    private boolean isMaxSizeReached;

    private int synchronizedCalled;

    public MockTreeLeaf3D() {
        reset();
    }

    public void reset() {
        area = 0;
        state = new TreeLeaf3DState();
        Shape3D shape = new Shape3D();
        TransformGroup tg = new TransformGroup();
        tg.addChild(shape);
        bg = new BranchGroup();
        bg.addChild(tg);
        attachPoint = new Point3d();
        increaseSizeCalled = 0;
        isMaxSizeReached = false;
        synchronizedCalled = 0;
    }

    @Override
    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public Point3d getPosition() {
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

    @Override
    public void synchronize() {
        this.synchronizedCalled++;
    }

    public int getNbSynchronize() {
        return synchronizedCalled;
    }

}
