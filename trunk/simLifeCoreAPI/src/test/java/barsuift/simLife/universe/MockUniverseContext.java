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
package barsuift.simLife.universe;

import barsuift.simLife.j3d.universe.MockUniverseContext3D;
import barsuift.simLife.j3d.universe.UniverseContext3D;
import barsuift.simLife.process.MainSynchronizer;
import barsuift.simLife.process.MockMainSynchronizer;
import barsuift.simLife.time.FpsCounter;

public class MockUniverseContext implements UniverseContext {

    private UniverseContextState state;

    private int nbSynchronizedCalled;

    private Universe universe;

    private MainSynchronizer synchronizer;

    private boolean fpsShowing;

    private FpsCounter fpsCounter;

    private UniverseContext3D universeContext3D;

    public MockUniverseContext() {
        reset();
    }

    public void reset() {
        this.state = new UniverseContextState();
        this.nbSynchronizedCalled = 0;
        this.universe = new MockUniverse();
        this.synchronizer = new MockMainSynchronizer();
        this.fpsShowing = false;
        this.fpsCounter = new FpsCounter();
        this.universeContext3D = new MockUniverseContext3D();
    }

    @Override
    public UniverseContextState getState() {
        return state;
    }

    public void setState(UniverseContextState state) {
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
    public Universe getUniverse() {
        return universe;
    }

    public void setUniverse(Universe universe) {
        this.universe = universe;
    }

    @Override
    public MainSynchronizer getSynchronizer() {
        return synchronizer;
    }

    public void setSynchronizer(MainSynchronizer synchronizer) {
        this.synchronizer = synchronizer;
    }

    @Override
    public void setFpsShowing(boolean fpsShowing) {
        this.fpsShowing = fpsShowing;
    }

    @Override
    public boolean isFpsShowing() {
        return fpsShowing;
    }

    @Override
    public FpsCounter getFpsCounter() {
        return fpsCounter;
    }

    public void setFpsCounter(FpsCounter fpsCounter) {
        this.fpsCounter = fpsCounter;
    }

    @Override
    public UniverseContext3D getUniverseContext3D() {
        return universeContext3D;
    }

    public void setUniverseContext3D(UniverseContext3D universeContext3D) {
        this.universeContext3D = universeContext3D;
    }

}
