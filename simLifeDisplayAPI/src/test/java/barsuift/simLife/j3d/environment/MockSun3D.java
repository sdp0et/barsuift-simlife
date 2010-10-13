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

import java.math.BigDecimal;
import java.util.Observable;

import javax.media.j3d.DirectionalLight;

import barsuift.simLife.PercentHelper;
import barsuift.simLife.j3d.environment.Sun3D;


public class MockSun3D extends Observable implements Sun3D {

    private BigDecimal whiteFactor = PercentHelper.getDecimalValue(100);

    private DirectionalLight light = new DirectionalLight();

    @Override
    public BigDecimal getWhiteFactor() {
        return whiteFactor;
    }

    public void setWhiteFactor(BigDecimal whiteFactor) {
        this.whiteFactor = whiteFactor;
    }

    @Override
    public DirectionalLight getLight() {
        return light;
    }

    public void setLight(DirectionalLight light) {
        this.light = light;
    }

}
