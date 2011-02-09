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
import javax.media.j3d.Background;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import barsuift.simLife.environment.Sky;
import barsuift.simLife.j3d.util.ColorConstants;

// TODO unit test
public class BasicSky3D implements Sky3D {

    private final Sky3DState state;

    private final Sky sky;

    private final AmbientLight ambientLight;

    private final Group group;

    /**
     * Creates the sky3D with given state
     * 
     * @param state the sky3D state
     * @param sky the sky
     * @throws IllegalArgumentException if the given sky3D state is null
     */
    public BasicSky3D(Sky3DState state, Sky sky) {
        if (state == null) {
            throw new IllegalArgumentException("Null sky3D state");
        }
        this.state = state;
        this.sky = sky;
        ambientLight = new AmbientLight(ColorConstants.grey);
        ambientLight.setInfluencingBounds(state.getAmbientLightBounds().toBoundingBox());
        group = new BranchGroup();
        group.addChild(ambientLight);
        group.addChild(getSun3D().getLight());
        addSkyBackGround();
    }

    private void addSkyBackGround() {
        Background background = new Background();
        background.setColor(ColorConstants.skyBlue);
        // FIXME fix bounds
        background.setApplicationBounds(new BoundingBox(new Point3d(0, 0, 0), new Point3d(1000, 1000, 1000)));

        // FIXME the rest of this method should be in BasicSun3D !!
        BranchGroup bgGroup = new BranchGroup();
        TransformGroup bgTg = new TransformGroup();
        Transform3D bgTransf = new Transform3D();
        // FIXME put proper rotation here
        bgTransf.setRotation(new AxisAngle4d(new Vector3d(0, 1, 0), Math.PI));
        bgTg.setTransform(bgTransf);
        bgGroup.addChild(bgTg);
        SunDisk3D sunDisk = new SunDisk3D();
        bgTg.addChild(sunDisk);
        background.setGeometry(bgGroup);
        group.addChild(background);
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public Sun3D getSun3D() {
        return sky.getSun().getSun3D();
    }

    public Sky3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        // FIXME 002 nothing to do ??
    }

}
