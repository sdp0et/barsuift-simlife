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
package barsuift.simLife.j3d.landscape;

import javax.media.j3d.BranchGroup;

import barsuift.simLife.j3d.landscape.Landscape3D;
import barsuift.simLife.j3d.landscape.Landscape3DState;



public class MockLandscape3D implements Landscape3D {

    private Landscape3DState state;

    private int nbSynchronizedCalled;

    private double height;

    private boolean inLandscape;

    private BranchGroup bg;

    public MockLandscape3D() {
        reset();
    }

    public void reset() {
        state = new Landscape3DState();
        nbSynchronizedCalled = 0;
        height = 0;
        inLandscape = true;
        bg = new BranchGroup();
    }

    @Override
    public Landscape3DState getState() {
        return state;
    }

    public void setState(Landscape3DState state) {
        this.state = state;
    }

    @Override
    public void synchronize() {
        this.nbSynchronizedCalled++;
    }

    public int getNbSynchronizeCalled() {
        return nbSynchronizedCalled;
    }

    @Override
    public double getHeight(double x, double z) {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public boolean inLandscape(double x, double z) {
        return inLandscape;
    }

    public void setInLandscape(boolean inLandscape) {
        this.inLandscape = inLandscape;
    }

    @Override
    public BranchGroup getBranchGroup() {
        return bg;
    }

    public void setBranchGroup(BranchGroup bg) {
        this.bg = bg;
    }

}
