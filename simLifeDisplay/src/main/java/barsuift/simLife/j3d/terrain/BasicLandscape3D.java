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
package barsuift.simLife.j3d.terrain;


public class BasicLandscape3D implements Landscape3D {

    private final Landscape3DState state;

    public BasicLandscape3D(Landscape3DState state) {
        this.state = state;
    }

    // TODO 003. use a simplified version of the ElevationModel.getElevationAt(x,z) from nav project to adjust the
    // y height.
    @Override
    public double getHeight(double x, double z) {
        return 0;
    }

    @Override
    public boolean inLandscape(double x, double z) {
        return true;
    }

    @Override
    public Landscape3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        // nothing to do
    }

}
