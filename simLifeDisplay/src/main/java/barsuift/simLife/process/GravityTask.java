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

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

// TODO 001. 007. unit test
public class GravityTask extends AbstractSplitConditionalTask {

    private final ConcurrentLinkedQueue<TransformGroup> transforms;

    public GravityTask(SplitConditionalTaskState state) {
        super(state);
        this.transforms = new ConcurrentLinkedQueue<TransformGroup>();
    }

    // TODO 001. 001. create Mobile interface extending Publisher, with getTransformGroup() method
    // TODO 001. 002. make TreeLeaf3D extends Mobile
    // TODO 001. 003. pass a Mobile as a parameter
    public void fall(BranchGroup groupToFall) {
        TransformGroup tg = (TransformGroup) groupToFall.getChild(0);
        transforms.add(tg);
    }

    @Override
    public void executeSplitConditionalStep(int stepSize) {
        for (TransformGroup currentTG : transforms) {
            // get current values
            Transform3D transform = new Transform3D();
            currentTG.getTransform(transform);
            Vector3d translation = new Vector3d();
            transform.get(translation);

            // update values
            if (translation.y < (0.025 * stepSize)) {
                translation.y = 0;
                // TODO 001. 004. mobile.notifySubscriber(FALLEN)
                transforms.remove(currentTG);
            } else {
                translation.y -= (0.025 * stepSize);
            }

            // set the new values
            transform.setTranslation(translation);
            currentTG.setTransform(transform);
        }
    }
}
