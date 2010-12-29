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
    public double getHeight(double x, double z) {
        if (!inLandscape(x, z)) {
            return 0;
        }
        // P1 = the "lower" point (closest top left)
        int x1 = (int) Math.floor(x);
        int z1 = (int) Math.floor(z);

        // P2 = top right point
        int x2 = x1 + 1;
        int z2 = z1;

        // P3 = bottom left point
        int x3 = x1;
        int z3 = z1 + 1;

        double weight1;
        double weight2;
        double weight3;

        // "distance" is the distance between P1 and Pi, where Pi is the intersection point between the orthogonal to
        // the opposite angle of P1 and its own orthogonal passing by the given point (x,y).
        // check if the point is closer to the lower point or the upper point
        double side = ((x - x1) + (z - z1)) / 2;
        // if side is >= 0.5 then the point is closer to the "upper" point
        if (side < 0.5) {
            weight1 = 1 - (side * 2);
            weight2 = 1 - (x2 - x);
            weight3 = 1 - (z3 - z);
        } else {
            // P1 = change to "upper" point
            // P2 and P3 are still the same
            x1 = (int) Math.ceil(x);
            z1 = (int) Math.ceil(z);
            weight1 = 1 - ((1 - side) * 2);
            weight2 = 1 - (z - z2);
            weight3 = 1 - (x - x3);
        }

        // get the height of the three points
        int vertexIdx1 = getVertexIndix(x1, z1);
        float y1 = coordinates[vertexIdx1 * 3 + 1];

        int vertexIdx2 = getVertexIndix(x2, z2);
        float y2 = coordinates[vertexIdx2 * 3 + 1];

        int vertexIdx3 = getVertexIndix(x3, z3);
        float y3 = coordinates[vertexIdx3 * 3 + 1];

        // compute the weighted average height
        double height = ((weight1 * y1) + (weight2 * y2) + (weight3 * y3)) / (weight1 + weight2 + weight3);

        return height;
    }

    int getVertexIndix(int x, int z) {
        return size * z + x;
    }

    /**
     * the landscape bounds are inclusive for minimum bounds, but EXCLUSIVE for maximum bounds
     */
    @Override
    public boolean inLandscape(double x, double z) {
        return (x >= 0 && z >= 0 && x < (size - 1) && z < (size - 1));
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
