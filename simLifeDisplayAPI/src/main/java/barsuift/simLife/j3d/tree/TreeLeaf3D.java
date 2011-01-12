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
import javax.vecmath.Point3f;

import barsuift.simLife.Persistent;
import barsuift.simLife.j3d.Mobile;
import barsuift.simLife.message.Subscriber;

public interface TreeLeaf3D extends Subscriber, Mobile, Persistent<TreeLeaf3DState> {

    public float getArea();

    public boolean isMaxSizeReached();

    public void increaseSize();

    public Point3f getPosition();

    /**
     * Gets the BranchGroup of the leaf 3D. This branch group contains a transform group, for relative position of the
     * leaf. The TG contains the leaf shape.
     * 
     * @return the leaf 3D branch group
     */
    public BranchGroup getBranchGroup();

}