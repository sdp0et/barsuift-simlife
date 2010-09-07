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

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.j3d.tree.TreeTrunk3DState;

@XmlRootElement
public class TreeTrunkState {

    private Long id;

    private int age;

    private float radius;

    private float height;

    private TreeTrunk3DState trunk3DState;

    public TreeTrunkState() {
        super();
        this.id = new Long(0);
        this.age = 0;
        this.radius = 0;
        this.height = 0;
        this.trunk3DState = new TreeTrunk3DState();
    }

    public TreeTrunkState(Long id, int age, float radius, float height, TreeTrunk3DState trunk3DState) {
        super();
        this.id = id;
        this.age = age;
        this.radius = radius;
        this.height = height;
        this.trunk3DState = trunk3DState;
    }

    public TreeTrunkState(TreeTrunkState copy) {
        super();
        this.id = copy.id;
        this.age = copy.age;
        this.radius = copy.radius;
        this.height = copy.height;
        this.trunk3DState = new TreeTrunk3DState(copy.trunk3DState);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public TreeTrunk3DState getTrunk3DState() {
        return trunk3DState;
    }

    public void setTrunk3DState(TreeTrunk3DState trunk3dState) {
        trunk3DState = trunk3dState;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(height);
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + age;
        result = prime * result + Float.floatToIntBits(radius);
        result = prime * result + ((trunk3DState == null) ? 0 : trunk3DState.hashCode());
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
        TreeTrunkState other = (TreeTrunkState) obj;
        if (Float.floatToIntBits(height) != Float.floatToIntBits(other.height))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else
            if (!id.equals(other.id))
                return false;
        if (age != other.age)
            return false;
        if (Float.floatToIntBits(radius) != Float.floatToIntBits(other.radius))
            return false;
        if (trunk3DState == null) {
            if (other.trunk3DState != null)
                return false;
        } else
            if (!trunk3DState.equals(other.trunk3DState))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "TreeTrunkState [height=" + height + ", id=" + id + ", age=" + age + ", radius=" + radius
                + ", trunk3DState=" + trunk3DState + "]";
    }

}
