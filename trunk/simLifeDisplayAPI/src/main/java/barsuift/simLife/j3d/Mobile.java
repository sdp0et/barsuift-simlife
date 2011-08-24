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

import barsuift.simLife.message.Publisher;

/**
 * A mobile is something you can move, using its transformGroup.
 * <p>
 * This interface extends Publisher so that moved objects can notify others when they reach certain points of interests,
 * like colliding with another object, or when the movement stops for any reasons.
 * </p>
 * 
 */
public interface Mobile extends Publisher {

    /**
     * Gets the transform group of the mobile. This transform group is used to move the mobile.
     * 
     * @return the mobile transform group
     */
    public TransformGroup getTransformGroup();

    /**
     * Gets the branch group of the mobile. This branch group contains the transform group, returned by
     * {@link #getTransformGroup()}.
     * 
     * @return the mobile branch group
     */
    public BranchGroup getBranchGroup();

}
