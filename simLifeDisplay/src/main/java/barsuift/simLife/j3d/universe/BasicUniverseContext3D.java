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
package barsuift.simLife.j3d.universe;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;

import barsuift.simLife.j3d.Axis3DGroup;
import barsuift.simLife.j3d.BasicSimLifeCanvas3D;
import barsuift.simLife.j3d.SimLifeCanvas3D;
import barsuift.simLife.j3d.landscape.BasicNavigator;
import barsuift.simLife.j3d.landscape.Landscape3D;
import barsuift.simLife.j3d.landscape.Navigator;
import barsuift.simLife.universe.UniverseContext;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class BasicUniverseContext3D implements UniverseContext3D {

    private final UniverseContext3DState state;

    private boolean axisShowing;

    private final BasicSimLifeCanvas3D canvas3D;

    private final BasicNavigator navigator;


    private final BranchGroup root;

    private final Axis3DGroup axisGroup = new Axis3DGroup();

    private SimpleUniverse simpleU;

    public BasicUniverseContext3D(UniverseContext3DState state) {
        this.state = state;
        this.axisShowing = state.isAxisShowing();

        canvas3D = new BasicSimLifeCanvas3D(state.getCanvas());
        simpleU = new SimpleUniverse(canvas3D);

        // limit the graphic engine to 40 FPS (interval = 1000ms / 40 = 25)
        simpleU.getViewer().getView().setMinimumFrameCycleTime(25);

        // double the back clip distance (distance where objects start to disappear)
        simpleU.getViewer().getView().setBackClipDistance(40);

        root = new BranchGroup();
        // allow to add children to the root
        root.setCapability(Group.ALLOW_CHILDREN_EXTEND);
        // allow the remove children from the root
        root.setCapability(Group.ALLOW_CHILDREN_WRITE);

        navigator = new BasicNavigator(state.getNavigator());
        simpleU.getViewingPlatform().setViewPlatformBehavior(navigator);

        setAxisShowing(state.isAxisShowing());
    }

    public void init(UniverseContext universeContext) {
        canvas3D.init(universeContext.getFpsCounter());

        Landscape3D landscape3D = universeContext.getUniverse().getEnvironment().getLandscape().getLandscape3D();
        navigator.init(landscape3D);
        simpleU.getViewingPlatform().setViewPlatformBehavior(navigator);

        root.addChild(universeContext.getUniverse().getUniverse3D().getUniverseRoot());

        root.compile();
        simpleU.addBranchGraph(root);
    }

    @Override
    public SimLifeCanvas3D getCanvas3D() {
        return canvas3D;
    }

    @Override
    public void setFpsShowing(boolean fpsShowing) {
        canvas3D.setFpsShowing(fpsShowing);
    }

    @Override
    public boolean isFpsShowing() {
        return canvas3D.isFpsShowing();
    }

    @Override
    public void setAxisShowing(boolean axisShowing) {
        if (axisShowing) {
            root.addChild(axisGroup);
        } else {
            root.removeChild(axisGroup);
        }
        this.axisShowing = axisShowing;
    }

    @Override
    public boolean isAxisShowing() {
        return axisShowing;
    }

    @Override
    public Navigator getNavigator() {
        return navigator;
    }

    @Override
    public UniverseContext3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setAxisShowing(axisShowing);
        canvas3D.synchronize();
        navigator.synchronize();
    }

}
