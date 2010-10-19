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
package barsuift.simLife.tree;

import barsuift.simLife.j3d.tree.BasicTreeTrunk3D;
import barsuift.simLife.j3d.tree.TreeTrunk3D;
import barsuift.simLife.universe.Universe;

public class BasicTreeTrunk implements TreeTrunk {

    private final TreeTrunkState state;

    private final long creationMillis;

    private float radius;

    private float height;

    private final TreeTrunk3D trunk3D;

    public BasicTreeTrunk(Universe universe, TreeTrunkState state) {
        if (universe == null) {
            throw new IllegalArgumentException("null universe");
        }
        if (state == null) {
            throw new IllegalArgumentException("null trunk state");
        }
        this.state = state;
        this.creationMillis = state.getCreationMillis();
        this.radius = state.getRadius();
        this.height = state.getHeight();
        this.trunk3D = new BasicTreeTrunk3D(universe.getUniverse3D(), state.getTrunk3DState(), this);
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public float getRadius() {
        return radius;
    }

    @Override
    public TreeTrunkState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setHeight(height);
        state.setRadius(radius);
        trunk3D.synchronize();
    }

    @Override
    public long getCreationMillis() {
        return creationMillis;
    }

    @Override
    public void spendTime() {
        // nothing to do
    }

    @Override
    public TreeTrunk3D getTreeTrunk3D() {
        return trunk3D;
    }

}
