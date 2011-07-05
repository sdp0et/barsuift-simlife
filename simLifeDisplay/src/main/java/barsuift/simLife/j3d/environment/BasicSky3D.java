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
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.vecmath.Color3f;

import barsuift.simLife.environment.Sky;
import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.j3d.util.ColorConstants;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;

public class BasicSky3D implements Sky3D, Subscriber {

    private final Sky3DState state;

    private Sky sky;

    private final AmbientLight ambientLight;

    private final Group group;

    private final Background background;

    /**
     * Creates the sky3D with given state
     * 
     * @param state the sky3D state
     * @param sky the sky
     * @throws IllegalArgumentException if the given sky3D state is null
     */
    public BasicSky3D(Sky3DState state) {
        if (state == null) {
            throw new IllegalArgumentException("Null sky3D state");
        }
        this.state = state;
        ambientLight = new AmbientLight(ColorConstants.grey);
        ambientLight.setInfluencingBounds(state.getAmbientLightBounds().toBoundingBox());
        group = new BranchGroup();
        group.addChild(ambientLight);
        background = new Background();
        background.setApplicationBounds(state.getSkyBounds().toBoundingBox());
        background.setCapability(Background.ALLOW_COLOR_WRITE);
        group.addChild(background);
    }

    public void init(Sky sky) {
        this.sky = sky;
        group.addChild(getSun3D().getLight());
        getSun3D().addSubscriber(this);
        background.setGeometry(getSun3D().getGroup());
        updateColor();
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public Sun3D getSun3D() {
        return sky.getSun().getSun3D();
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        if (arg == SunUpdateCode.BRIGHTNESS) {
            updateColor();
        }

    }

    private void updateColor() {
        Color3f backgroundColor = new Color3f();
        backgroundColor.interpolate(ColorConstants.black, ColorConstants.skyBlue, getSun3D().getBrightness()
                .floatValue());
        background.setColor(backgroundColor);
    }

    public Sky3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        // nothing to do
    }

}
