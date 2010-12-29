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

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;

import barsuift.simLife.j3d.AppearanceFactory;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.geometry.Stripifier;


public class BasicLandscape3D implements Landscape3D {

    private final Landscape3DState state;

    private final int size;

    private final float[] coordinates;

    private final BranchGroup branchGroup;

    public BasicLandscape3D(Landscape3DState state) {
        this.state = state;
        this.size = state.getSize();
        this.coordinates = state.getCoordinates();

        GeometryInfo gi = new GeometryInfo(GeometryInfo.TRIANGLE_STRIP_ARRAY);

        gi.setCoordinates(coordinates);
        gi.setCoordinateIndices(state.getCoordinatesIndices());
        gi.setStripCounts(state.getStripCounts());

        NormalGenerator ng = new NormalGenerator();
        ng.generateNormals(gi);

        Stripifier strippy = new Stripifier();
        strippy.stripify(gi);

        Shape3D shape = new Shape3D();
        shape.setGeometry(gi.getGeometryArray(true, true, false));

        Appearance shapeAppearance = new Appearance();
        AppearanceFactory.setColorWithMaterial(shapeAppearance, new Color3f(0.624f, 0.544f, 0.120f), new Color3f(0.4f,
                0.4f, 0.3f), new Color3f(0f, 0f, 0f));
        shape.setAppearance(shapeAppearance);

        branchGroup = new BranchGroup();
        branchGroup.addChild(shape);
    }

    @Override
    // TODO 000. unit test
    public double getHeight(double x, double z) {
        // P1 = the "lower" point (closest top left)
        int x1 = (int) Math.floor(x);
        int z1 = (int) Math.floor(z);

        // P2 = top right point
        int x2 = x1 + 1;
        int z2 = z1;

        // P3 = bottom left point
        int x3 = x1;
        int z3 = z1 + 1;

        // check if the point is closer to the lower point or the upper point
        double distanceFromLower = (x - x1) + (z - z1);
        // if distance is >= 1 then the point is closer to the "upper" point
        if (distanceFromLower >= 1) {
            // P1 = change to "upper" point
            x1 = (int) Math.ceil(x);
            z1 = (int) Math.ceil(z);
            // P2 and P3 are still the same
        }

        double weight1 = getWeight(x, z, x1, z1, (double) 1);
        double weight2 = getWeight(x, z, x2, z2, Math.sqrt(2));
        double weight3 = getWeight(x, z, x3, z3, Math.sqrt(2));

        // get the height of the three points
        int vertexIdx1 = getVertexIndix(x1, z1);
        float y1 = coordinates[vertexIdx1 * 3 + 1];

        int vertexIdx2 = getVertexIndix(x2, z2);
        float y2 = coordinates[vertexIdx2 * 3 + 1];

        int vertexIdx3 = getVertexIndix(x3, z3);
        float y3 = coordinates[vertexIdx3 * 3 + 1];

        // compute the weighted average height
        double weight = ((weight1 * y1) + (weight2 * y2) + (weight3 * y3)) / (weight1 + weight2 + weight3);

        return weight;
    }

    /**
     * Computes the ratio between the distance between 2 points and the maxDistance between these points.
     * 
     * @param x1
     * @param z1
     * @param x2
     * @param z2
     * @return
     */
    // TODO 000. unit test
    private double getWeight(double x1, double z1, int x2, int z2, double maxDistance) {
        double distance = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((z1 - z2), 2));
        double weight = distance / maxDistance;
        return weight;
    }

    // TODO 000. unit test
    private int getVertexIndix(int x, int z) {
        return size * z + x;
    }

    @Override
    public boolean inLandscape(double x, double z) {
        return true;
    }

    @Override
    public BranchGroup getBranchGroup() {
        return branchGroup;
    }

    @Override
    public Landscape3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        // nothing to do
        // the size doesn't change
        // the coordinates don't change
        // the coordinatesIndices don't change
        // the stripCounts don't change
    }

}
