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
package barsuift.simLife.j3d.universe;

import java.util.Set;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;

import barsuift.simLife.j3d.universe.environment.Lights;
import barsuift.simLife.j3d.universe.physic.Physics;

public interface Universe3D {

    public BranchGroup getUniverseRoot();

    public void addElement3D(Node element3D);

    public Set<Node> getElements3D();

    public Physics getPhysics();

    public Lights getLights();

}
