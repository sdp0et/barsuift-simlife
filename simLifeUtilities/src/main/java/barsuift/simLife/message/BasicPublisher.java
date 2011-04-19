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
package barsuift.simLife.message;

import java.util.Vector;

/**
 * This implementation will notify subscribers in the order in which they registered interest.
 * 
 */
public class BasicPublisher implements Publisher {

    private boolean changed = false;

    private final Vector<Subscriber> subscribers;

    private final Publisher wrappedPublisher;

    /** Construct an Publisher with zero Subscribers. */

    public BasicPublisher(Publisher publisher) {
        subscribers = new Vector<Subscriber>();
        this.wrappedPublisher = publisher;
    }

    /**
     * The order in which notifications will be delivered to multiple subscribers is the order they were added using
     * this method.
     * 
     * @param subscriber a subscriber to be added.
     * @throws NullPointerException if the subscriber is null.
     */
    public synchronized void addSubscriber(Subscriber subscriber) {
        if (subscriber == null)
            throw new NullPointerException();
        if (!subscribers.contains(subscriber)) {
            subscribers.addElement(subscriber);
        }
    }

    public synchronized void deleteSubscriber(Subscriber subscriber) {
        subscribers.removeElement(subscriber);
    }

    public void notifySubscribers() {
        notifySubscribers(null);
    }

    public void notifySubscribers(Object arg) {
        // a temporary array buffer, used as a snapshot of the state of current Subscribers.
        Object[] arrLocal;

        synchronized (this) {
            /*
             * We don't want the Subscriber doing callbacks into arbitrary code while holding its own Monitor. The code
             * where we extract each Publisher from the Vector and store the state of the Subscriber needs
             * synchronization, but notifying subscribers does not (should not). The worst result of any potential
             * race-condition here is that: 1) a newly-added Subscriber will miss a notification in progress 2) a
             * recently unregistered Subscriber will be wrongly notified when it doesn't care
             */
            if (!changed)
                return;
            arrLocal = subscribers.toArray();
            clearChanged();
        }

        for (int i = arrLocal.length - 1; i >= 0; i--)
            ((Subscriber) arrLocal[i]).update(wrappedPublisher, arg);
    }

    public synchronized void deleteSubscribers() {
        subscribers.removeAllElements();
    }

    public synchronized void setChanged() {
        changed = true;
    }

    public synchronized void clearChanged() {
        changed = false;
    }

    public synchronized boolean hasChanged() {
        return changed;
    }

    public synchronized int countSubscribers() {
        return subscribers.size();
    }

}
