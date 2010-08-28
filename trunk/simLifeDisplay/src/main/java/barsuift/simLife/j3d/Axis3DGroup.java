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
package barsuift.simLife.j3d;

import javax.media.j3d.BranchGroup;


public class Axis3DGroup extends BranchGroup {

    private Axis3D x;

    private Axis3D y;

    private Axis3D z;

    public Axis3DGroup() {
        // allow the branch group to be removed from the root
        setCapability(BranchGroup.ALLOW_DETACH);
        x = new Axis3D(Axis.X);
        y = new Axis3D(Axis.Y);
        z = new Axis3D(Axis.Z);
        addChild(x);
        addChild(y);
        addChild(z);

    }

}
