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
package barsuift.simLife;

/**
 * This interface represents a persistent object.
 * <p>
 * The object is persisted using its State representation, by using the {@link #getClass()} method.
 * </p>
 * <p>
 * This interface also provides a {@link #synchronize()} method, to synchronize the State instance with the current
 * state of the object to persist.
 * </p>
 * 
 * @param <E> the class representing the State that will be persisted
 */
public interface Persistent<E extends State> {

    /**
     * Gets the state of the persistent object.
     * <p>
     * Implementing class should consider systematically calling the {@link #synchronize()} method before returning the
     * actual state. It is not up to clients to synchronize the object themselves.
     * </p>
     * 
     * @return the object state
     */
    public E getState();

    /**
     * Synchronizes the State instance with the current object state.
     * <p>
     * It is not up to clients to synchronize the object themselves. Instead, this method should be called by the
     * {@link #getState()} method.
     * </p>
     * <p>
     * On the other hand, this method is provided to allow aggregation classes to synchronize their aggregated instances
     * without requiring the call to the {@link #getState()} method.
     * </p>
     */
    public void synchronize();

}