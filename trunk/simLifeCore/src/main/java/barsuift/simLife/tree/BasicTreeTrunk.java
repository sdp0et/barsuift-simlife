/**
 * barsuift-simlife is a life simulator programm
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
import barsuift.simLife.j3d.tree.TreeTrunk3DState;
import barsuift.simLife.universe.Universe;

public class BasicTreeTrunk implements TreeTrunk {

    private Long id;

    private int age;

    private float radius;

    private float height;

    private TreeTrunk3D trunk3D;

    public BasicTreeTrunk(Universe universe, TreeTrunkState state) {
        if (universe == null) {
            throw new IllegalArgumentException("null universe");
        }
        if (state == null) {
            throw new IllegalArgumentException("null trunk state");
        }
        this.id = state.getId();
        this.age = state.getAge();
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
        TreeTrunk3DState trunk3DState = new TreeTrunk3DState();
        return new TreeTrunkState(id, age, radius, height, trunk3DState);
    }

    @Override
    public Long getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    @Override
    public void spendTime() {
        age++;
    }

    @Override
    public TreeTrunk3D getTreeTrunkD() {
        return trunk3D;
    }

    @Override
    public String toString() {
        return "BasicTreeTrunk [age=" + age + ", height=" + height + ", id=" + id + ", radius=" + radius + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + age;
        result = prime * result + Float.floatToIntBits(height);
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + Float.floatToIntBits(radius);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BasicTreeTrunk other = (BasicTreeTrunk) obj;
        if (age != other.age)
            return false;
        if (Float.floatToIntBits(height) != Float.floatToIntBits(other.height))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else
            if (!id.equals(other.id))
                return false;
        if (Float.floatToIntBits(radius) != Float.floatToIntBits(other.radius))
            return false;
        return true;
    }

}
