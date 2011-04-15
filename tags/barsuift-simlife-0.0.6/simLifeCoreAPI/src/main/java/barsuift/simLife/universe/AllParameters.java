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

import barsuift.simLife.Parameters;
import barsuift.simLife.PlanetParameters;
import barsuift.simLife.landscape.LandscapeParameters;

/**
 * This class gathers together all the parameters.
 * 
 */
public class AllParameters implements Parameters {

    private final PlanetParameters planet;

    private final LandscapeParameters landscape;

    public AllParameters() {
        super();
        this.planet = new PlanetParameters();
        this.landscape = new LandscapeParameters();
    }

    public PlanetParameters getPlanet() {
        return planet;
    }

    public LandscapeParameters getLandscape() {
        return landscape;
    }

    @Override
    public void resetToDefaults() {
        planet.resetToDefaults();
        landscape.resetToDefaults();
    }

    @Override
    public void random() {
        planet.random();
        landscape.random();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((landscape == null) ? 0 : landscape.hashCode());
        result = prime * result + ((planet == null) ? 0 : planet.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AllParameters other = (AllParameters) obj;
        if (landscape == null) {
            if (other.landscape != null)
                return false;
        } else
            if (!landscape.equals(other.landscape))
                return false;
        if (planet == null) {
            if (other.planet != null)
                return false;
        } else
            if (!planet.equals(other.planet))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "AllParameters [planet=" + planet + ", landscape=" + landscape + "]";
    }

}
