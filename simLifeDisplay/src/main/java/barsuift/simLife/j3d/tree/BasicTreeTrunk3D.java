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
package barsuift.simLife.j3d.tree;

import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import barsuift.simLife.j3d.AppearanceFactory;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.j3d.util.ColorConstants;
import barsuift.simLife.j3d.util.TransformerHelper;
import barsuift.simLife.tree.TreeTrunk;

import com.sun.j3d.utils.geometry.Cylinder;

/**
 * Group -> TransformGroup -> Cylinder
 */
public class BasicTreeTrunk3D implements TreeTrunk3D {

    private final TreeTrunk3DState state;

    private Cylinder trunkCylinder;

    private final Group group;

    public BasicTreeTrunk3D(TreeTrunk3DState state) {
        if (state == null) {
            throw new IllegalArgumentException("Null tree trunk 3D state");
        }
        this.state = state;
        this.group = new Group();
    }

    public void init(Universe3D universe3D, TreeTrunk trunk) {
        if (universe3D == null) {
            throw new IllegalArgumentException("Null universe 3D");
        }
        if (trunk == null) {
            throw new IllegalArgumentException("Null tree trunk");
        }
        Appearance trunkAppearance = new Appearance();
        AppearanceFactory.setColorWithMaterial(trunkAppearance, ColorConstants.brown, new Color3f(0.15f, 0.15f, 0.15f),
                new Color3f(0.05f, 0.05f, 0.05f));
        trunkCylinder = new Cylinder(trunk.getRadius(), trunk.getHeight(), trunkAppearance);
        Vector3f translationVector = new Vector3f();
        translationVector.setY(trunk.getHeight() / 2);
        TransformGroup transformGroup = TransformerHelper.getTranslationTransformGroup(translationVector);
        transformGroup.addChild(trunkCylinder);
        group.addChild(transformGroup);
    }

    @Override
    public Cylinder getTrunk() {
        return trunkCylinder;
    }

    @Override
    public TreeTrunk3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        // nothing to do
    }

    @Override
    public Group getGroup() {
        return group;
    }

}
