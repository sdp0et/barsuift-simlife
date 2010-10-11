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

import barsuift.simLife.j3d.tree.MockTreeTrunk3D;
import barsuift.simLife.j3d.tree.TreeTrunk3D;


public class MockTreeTrunk implements TreeTrunk {

    private int age = 0;

    private float height = 0;

    private float radius = 0;

    private int spendTimeCalled = 0;

    private TreeTrunkState state = new TreeTrunkState();

    private TreeTrunk3D trunk3D = new MockTreeTrunk3D();

    private int synchronizedCalled;

    @Override
    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public TreeTrunkState getState() {
        return state;
    }

    public void setState(TreeTrunkState state) {
        this.state = state;
    }

    @Override
    public TreeTrunk3D getTreeTrunk3D() {
        return trunk3D;
    }

    public void setTreeTrunk3D(TreeTrunk3D trunk3D) {
        this.trunk3D = trunk3D;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void spendTime() {
        spendTimeCalled++;
    }

    public int howManyTimesSpendTimeCalled() {
        return spendTimeCalled;
    }

    public void resetSpendTimeCalled() {
        this.spendTimeCalled = 0;
    }

    @Override
    public void synchronize() {
        this.synchronizedCalled++;
    }

    public int getNbSynchronize() {
        return synchronizedCalled;
    }

}
