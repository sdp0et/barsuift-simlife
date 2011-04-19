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
package barsuift.simLife.j3d;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;

import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;


public class MockMobile implements Mobile {

    private BranchGroup bg;

    private TransformGroup tg;

    private final Publisher publisher = new BasicPublisher(this);

    public MockMobile() {
        reset();
    }

    public void reset() {
        bg = new BranchGroup();
        tg = new TransformGroup();
        bg.addChild(tg);

    }

    @Override
    public TransformGroup getTransformGroup() {
        return tg;
    }

    public void setTransformGroup(TransformGroup tg) {
        bg.removeChild(this.tg);
        this.tg = tg;
        bg.addChild(tg);
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
