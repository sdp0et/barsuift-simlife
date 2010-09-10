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

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.j3d.tree.TreeLeaf3DState;

@XmlRootElement
public class TreeLeafState implements State {

    private BigDecimal efficiency;

    private int age;

    private BigDecimal energy;

    private BigDecimal freeEnergy;

    private TreeLeaf3DState leaf3DState;

    public TreeLeafState() {
        super();
        this.efficiency = new BigDecimal(0);
        this.energy = new BigDecimal(0);
        this.freeEnergy = new BigDecimal(0);
        this.age = 0;
        this.leaf3DState = new TreeLeaf3DState();
    }

    public TreeLeafState(int age, BigDecimal energy, BigDecimal freeEnergy, BigDecimal efficiency,
            TreeLeaf3DState leaf3dState) {
        super();
        this.efficiency = efficiency;
        this.energy = energy;
        this.freeEnergy = freeEnergy;
        this.age = age;
        this.leaf3DState = leaf3dState;
    }

    public BigDecimal getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(BigDecimal efficiency) {
        this.efficiency = efficiency;
    }

    public BigDecimal getEnergy() {
        return energy;
    }

    public void setEnergy(BigDecimal energy) {
        this.energy = energy;
    }

    public BigDecimal getFreeEnergy() {
        return freeEnergy;
    }

    public void setFreeEnergy(BigDecimal freeEnergy) {
        this.freeEnergy = freeEnergy;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public TreeLeaf3DState getLeaf3DState() {
        return leaf3DState;
    }

    public void setLeaf3DState(TreeLeaf3DState leaf3dState) {
        leaf3DState = leaf3dState;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + age;
        result = prime * result + ((efficiency == null) ? 0 : efficiency.hashCode());
        result = prime * result + ((energy == null) ? 0 : energy.hashCode());
        result = prime * result + ((freeEnergy == null) ? 0 : freeEnergy.hashCode());
        result = prime * result + ((leaf3DState == null) ? 0 : leaf3DState.hashCode());
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
        TreeLeafState other = (TreeLeafState) obj;
        if (age != other.age)
            return false;
        if (efficiency == null) {
            if (other.efficiency != null)
                return false;
        } else
            if (!efficiency.equals(other.efficiency))
                return false;
        if (energy == null) {
            if (other.energy != null)
                return false;
        } else
            if (!energy.equals(other.energy))
                return false;
        if (freeEnergy == null) {
            if (other.freeEnergy != null)
                return false;
        } else
            if (!freeEnergy.equals(other.freeEnergy))
                return false;
        if (leaf3DState == null) {
            if (other.leaf3DState != null)
                return false;
        } else
            if (!leaf3DState.equals(other.leaf3DState))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "TreeLeafState [age=" + age + ", efficiency=" + efficiency + ", energy=" + energy + ", freeEnergy="
                + freeEnergy + ", leaf3DState=" + leaf3DState + "]";
    }

}
