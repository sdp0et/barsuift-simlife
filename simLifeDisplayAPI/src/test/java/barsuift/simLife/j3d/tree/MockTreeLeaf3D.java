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
import javax.vecmath.Point3f;

import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.MockSubscriber;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;


public class MockTreeLeaf3D extends MockSubscriber implements TreeLeaf3D {

    private float area;

    private TreeLeaf3DState state;

    private BranchGroup bg;

    private TransformGroup tg;

    private Point3f attachPoint;

    private int increaseSizeCalled;

    private boolean isMaxSizeReached;

    private int synchronizedCalled;

    private final Publisher publisher = new BasicPublisher(this);

    public MockTreeLeaf3D() {
        reset();
    }

    public void reset() {
        area = 0;
        state = new TreeLeaf3DState();
        Shape3D shape = new Shape3D();
        tg = new TransformGroup();
        tg.addChild(shape);
        bg = new BranchGroup();
        bg.addChild(tg);
        attachPoint = new Point3f();
        increaseSizeCalled = 0;
        isMaxSizeReached = false;
        synchronizedCalled = 0;
    }

    @Override
    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public Point3f getPosition() {
        return attachPoint;
    }

    public void setAttachPoint(Point3f attachPoint) {
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
        this.bg.removeChild(tg);
        this.bg = bg;
        bg.addChild(tg);
    }

    @Override
    public TransformGroup getTransformGroup() {
        return tg;
    }

    public void setTransformGroup(TransformGroup tg) {
        bg.removeChild(tg);
        this.tg = tg;
        bg.addChild(tg);
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

    public void addSubscriber(Subscriber subscriber) {
        publisher.addSubscriber(subscriber);
    }

    public void deleteSubscriber(Subscriber subscriber) {
        publisher.deleteSubscriber(subscriber);
    }

    public void notifySubscribers() {
        publisher.notifySubscribers();
    }

    public void notifySubscribers(Object arg) {
        publisher.notifySubscribers(arg);
    }

    public void deleteSubscribers() {
        publisher.deleteSubscribers();
    }

    public boolean hasChanged() {
        return publisher.hasChanged();
    }

    public int countSubscribers() {
        return publisher.countSubscribers();
    }

    public void setChanged() {
        publisher.setChanged();
    }

    public void clearChanged() {
        publisher.clearChanged();
    }

}
