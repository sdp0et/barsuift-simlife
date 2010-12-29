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
package barsuift.simLife.j3d.environment;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.vecmath.Point3d;

import barsuift.simLife.environment.Environment;
import barsuift.simLife.j3d.terrain.Landscape3D;
import barsuift.simLife.j3d.util.ColorConstants;


public class BasicEnvironment3D implements Environment3D {

    private static BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0, 0), 100);

    private final Environment3DState state;

    private final Environment environment;

    private final AmbientLight ambientLight;

    private final Group group;

    /**
     * Creates the environment3D with given state
     * 
     * @param state the environment3D state
     * @throws IllegalArgumentException if the given environment3D state is null
     */
    public BasicEnvironment3D(Environment3DState state, Environment environment) {
        if (state == null) {
            throw new IllegalArgumentException("Null environment3D state");
        }
        this.state = state;
        this.environment = environment;
        ambientLight = new AmbientLight(ColorConstants.grey);
        ambientLight.setInfluencingBounds(bounds);
        group = new BranchGroup();
        group.addChild(ambientLight);
        group.addChild(getSun3D().getLight());
        group.addChild(getLandscape3D().getBranchGroup());
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public Sun3D getSun3D() {
        return environment.getSun().getSun3D();
    }

    @Override
    public Landscape3D getLandscape3D() {
        return environment.getLandscape().getLandscape3D();
    }

    public Environment3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        // nothing to do
    }

}
