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

import barsuift.simLife.CommonParameters;
import barsuift.simLife.Parameters;
import barsuift.simLife.landscape.LandscapeParameters;

/**
 * This class gathers together all the parameters.
 * 
 */
public class AllParameters implements Parameters {

    private final CommonParameters common;

    private final LandscapeParameters landscape;
    
    //TODO 301. add forest parameters

    public AllParameters() {
        super();
        this.common = new CommonParameters();
        this.landscape = new LandscapeParameters();
    }

    public CommonParameters getCommon() {
        return common;
    }

    public LandscapeParameters getLandscape() {
        return landscape;
    }

    @Override
    public void resetToDefaults() {
        common.resetToDefaults();
        landscape.resetToDefaults();
    }

    @Override
    public void random() {
        common.random();
        landscape.random();
    }

    @Override
    public String toString() {
        return "AllParameters [common=" + common + ", landscape=" + landscape + "]";
    }

}
