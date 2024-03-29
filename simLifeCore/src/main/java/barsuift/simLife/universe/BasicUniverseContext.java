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

import barsuift.simLife.j3d.universe.BasicUniverseContext3D;
import barsuift.simLife.j3d.universe.UniverseContext3D;
import barsuift.simLife.process.ConditionalTaskState;
import barsuift.simLife.process.ConditionalTaskStateFactory;
import barsuift.simLife.process.FpsTicker;
import barsuift.simLife.time.FpsCounter;

public class BasicUniverseContext implements UniverseContext {

    private final UniverseContextState state;

    private final BasicUniverse universe;

    private boolean fpsShowing;

    private final BasicUniverseContext3D universeContext3D;

    private final FpsCounter fpsCounter = new FpsCounter();

    private FpsTicker fpsTicker;


    public BasicUniverseContext(UniverseContextState state) {
        this.state = state;
        this.universe = new BasicUniverse(state.getUniverse());
        this.universeContext3D = new BasicUniverseContext3D(state.getUniverseContext3D());
        setFpsShowing(state.isFpsShowing());
    }

    public void init() {
        this.universe.init();
        this.universeContext3D.init(this);
    }

    @Override
    public Universe getUniverse() {
        return universe;
    }

    @Override
    public void setFpsShowing(boolean fpsShowing) {
        if (fpsShowing) {
            fpsCounter.reset();
            ConditionalTaskStateFactory taskStateFactory = new ConditionalTaskStateFactory();
            ConditionalTaskState fpsTickerState = taskStateFactory.createConditionalTaskState(FpsTicker.class);
            fpsTicker = new FpsTicker(fpsTickerState);
            fpsTicker.init(fpsCounter);
            universe.getSynchronizer().scheduleSlow(fpsTicker);
        } else {
            if (this.fpsShowing) {
                // only unschedule the fpsTicker if it was previously scheduled
                universe.getSynchronizer().unscheduleSlow(fpsTicker);
            }
        }
        universeContext3D.setFpsShowing(fpsShowing);
        this.fpsShowing = fpsShowing;
    }

    @Override
    public boolean isFpsShowing() {
        return fpsShowing && universeContext3D.isFpsShowing();
    }

    @Override
    public FpsCounter getFpsCounter() {
        return fpsCounter;
    }

    @Override
    public UniverseContext3D getUniverseContext3D() {
        return universeContext3D;
    }

    @Override
    public UniverseContextState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        universe.synchronize();
        state.setFpsShowing(fpsShowing);
        universeContext3D.synchronize();
    }

}
