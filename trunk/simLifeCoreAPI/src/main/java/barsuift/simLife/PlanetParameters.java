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
package barsuift.simLife;




/**
 * The class holds the planet parameters required to create a universe.
 * 
 * Those parameters are :
 * <ul>
 * <li>latitude : the map latitude on the planet.</li>
 * <li>ecliptic obliquity : the angle between the normal to the planet rotation axis and the planet orbital plan</li>
 * </ul>
 * 
 */
public class PlanetParameters implements Parameters {

    public static final float LATITUDE_DEFAULT = 45;

    public static final int LATITUDE_MIN = 0;

    public static final int LATITUDE_MAX = 90;


    public static final float ECLIPTIC_OBLIQUITY_DEFAULT = 23;

    public static final int ECLIPTIC_OBLIQUITY_MIN = 0;

    public static final int ECLIPTIC_OBLIQUITY_MAX = 90;



    private float latitude;

    private float eclipticObliquity;

    /**
     * Empty constructor.
     */
    public PlanetParameters() {
        resetToDefaults();
    }

    public float getLatitude() {
        return latitude;
    }

    /**
     * 
     * @param latitude the latitude must be between {@link #LATITUDE_MIN} and {@link #LATITUDE_MAX}
     * @throws IllegalArgumentException if the latitude is not valid
     */
    public void setLatitude(float latitude) {
        if (latitude < LATITUDE_MIN || latitude > LATITUDE_MAX) {
            throw new IllegalArgumentException("latitude must be comprised between " + LATITUDE_MIN + " and "
                    + LATITUDE_MAX);
        }
        this.latitude = latitude;
    }

    public float getEclipticObliquity() {
        return eclipticObliquity;
    }

    /**
     * 
     * @param eclipticObliquity the ecliptic obliquity must be between {@link #ECLIPTIC_OBLIQUITY_MIN} and
     *            {@link #ECLIPTIC_OBLIQUITY_MAX}
     * @throws IllegalArgumentException if the ecliptic obliquity is not valid
     */
    public void setEclipticObliquity(float eclipticObliquity) {
        if (eclipticObliquity < ECLIPTIC_OBLIQUITY_MIN || eclipticObliquity > ECLIPTIC_OBLIQUITY_MAX) {
            throw new IllegalArgumentException("ecliptic obliquity must be comprised between " + ECLIPTIC_OBLIQUITY_MIN
                    + " and " + ECLIPTIC_OBLIQUITY_MAX);
        }
        this.eclipticObliquity = eclipticObliquity;
    }

    @Override
    public void resetToDefaults() {
        this.latitude = LATITUDE_DEFAULT;
        this.eclipticObliquity = ECLIPTIC_OBLIQUITY_DEFAULT;
    }

    @Override
    public void random() {
        this.latitude = Randomizer.randomBetween(LATITUDE_MIN, LATITUDE_MAX);
        this.eclipticObliquity = Randomizer.randomBetween(ECLIPTIC_OBLIQUITY_MIN, ECLIPTIC_OBLIQUITY_MAX);
    }

    @Override
    public String toString() {
        return "PlanetParameters [latitude=" + latitude + ", eclipticObliquity=" + eclipticObliquity + "]";
    }

}
