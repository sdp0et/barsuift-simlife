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

import javax.media.j3d.Node;
import javax.media.j3d.Shape3D;
import javax.vecmath.Point3d;

import barsuift.simLife.MockObserver;


public class MockTreeLeaf3D extends MockObserver implements TreeLeaf3D {

    private double area = 0;

    private TreeLeaf3DState state = new TreeLeaf3DState();

    private Node node = new Shape3D();

    private Point3d attachPoint = new Point3d();

    private double rotation = 0;

    private int increaseSizeCalled = 0;

    private boolean isMaxSizeReached = false;

    private int synchronizedCalled;

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
    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
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

    @Override
    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

}
