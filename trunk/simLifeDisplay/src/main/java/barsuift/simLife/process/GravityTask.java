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
package barsuift.simLife.process;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import barsuift.simLife.j3d.Mobile;
import barsuift.simLife.j3d.MobileEvent;

public class GravityTask extends AbstractSplitConditionalTask {

    private final ConcurrentLinkedQueue<Mobile> mobiles;

    public GravityTask(SplitConditionalTaskState state) {
        super(state);
        this.mobiles = new ConcurrentLinkedQueue<Mobile>();
    }

    public void fall(Mobile mobile) {
        mobiles.add(mobile);
    }

    @Override
    public void executeSplitConditionalStep(int stepSize) {
        for (Mobile mobile : mobiles) {
            TransformGroup currentTG = mobile.getTransformGroup();
            synchronized (currentTG) {
                // get current values
                Transform3D transform = new Transform3D();
                currentTG.getTransform(transform);
                Vector3d translation = new Vector3d();
                transform.get(translation);

                // TODO 001. 001. use landscape to know the height (not always 0)
                // update values
                if (translation.y < (0.025 * stepSize)) {
                    translation.y = 0;
                    mobiles.remove(mobile);
                    mobile.setChanged();
                    mobile.notifySubscribers(MobileEvent.FALLEN);
                } else {
                    translation.y -= (0.025 * stepSize);
                }

                // set the new values
                transform.setTranslation(translation);
                currentTG.setTransform(transform);
            }
        }
    }

    protected Collection<Mobile> getMobiles() {
        return mobiles;
    }

}
