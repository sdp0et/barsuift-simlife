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

import java.util.Observer;


/**
 * This interface is the equivalent of the {@link Observer} interface.
 * <p>
 * A class can implement the <code>Subscriber</code> interface when it wants to be informed of changes in
 * <code>Publisher</code> objects.
 * </p>
 */
public interface Subscriber {

    /**
     * This method is called whenever the publisher is changed. An application calls a <code>Publisher</code> object's
     * <code>notifySubscribers</code> method to have all the object's subscribers notified of the change.
     * 
     * @param publisher the publisher object.
     * @param arg an argument passed to the <code>notifySubscribers</code> method.
     */
    void update(Publisher publisher, Object arg);

}
