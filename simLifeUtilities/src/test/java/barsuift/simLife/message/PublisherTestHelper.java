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

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

public class PublisherTestHelper extends Assert {

    private int nbUpdated;

    private List<Object> updateObjects;

    public PublisherTestHelper() {
        reset();
    }

    public void reset() {
        nbUpdated = 0;
        updateObjects = new ArrayList<Object>();
    }

    public void addSubscriberTo(final Publisher publisher) {
        publisher.addSubscriber(new Subscriber() {

            public void update(Publisher publisher_, Object arg) {
                assertEquals(publisher, publisher_);
                nbUpdated++;
                updateObjects.add(arg);
            }

        });
    }

    public int nbUpdated() {
        return nbUpdated;
    }

    public List<Object> getUpdateObjects() {
        return updateObjects;
    }

}
