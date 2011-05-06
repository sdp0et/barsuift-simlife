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
import java.util.logging.Logger;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import barsuift.simLife.j3d.Mobile;
import barsuift.simLife.j3d.landscape.Landscape3D;

//FIXME unit test, add state, and factory
public class WindTask extends AbstractSplitConditionalTask {

    private static final Logger logger = Logger.getLogger(WindTask.class.getName());

    /**
     * Wind speed is thus 2m/sec = 1m/cycle
     */
    private static final float WIND_SPEED = 1f / BasicSynchronizerFast.RATIO_SLOW_FAST;

    private final ConcurrentLinkedQueue<Mobile> mobiles;

    private final Landscape3D landscape3D;

    public WindTask(SplitConditionalTaskState state, Landscape3D landscape3D) {
        super(state);
        this.mobiles = new ConcurrentLinkedQueue<Mobile>();
        this.landscape3D = landscape3D;
    }

    public void fall(Mobile mobile) {
        mobiles.add(mobile);
    }

    @Override
    public void executeSplitConditionalStep(int stepSize) {
        for (Mobile mobile : mobiles) {
            logger.info("Wind - Before getting current group");
            TransformGroup currentTG = mobile.getTransformGroup();
            logger.info("Wind - After getting current group");
            synchronized (currentTG) {
                logger.info("Wind - in synchronized block");
                boolean outOfLandscape = false;
                boolean onTheGround = false;
                // get current values
                Transform3D transform = new Transform3D();
                currentTG.getTransform(transform);
                Vector3f translation = new Vector3f();
                transform.get(translation);

                float stepMove = WIND_SPEED * stepSize;
                translation.x += stepMove;
                // cap the movement to the size of the landscape
                if (translation.x > landscape3D.getSize()) {
                    translation.x = landscape3D.getSize();
                    outOfLandscape = true;
                }

                // ugly hack to ensure the mobile won't go under the ground
                float landscapeHeight = landscape3D.getHeight(translation.x, translation.z);
                if (translation.y < landscapeHeight) {
                    translation.y = landscapeHeight;
                    onTheGround = true;
                }

                if (onTheGround || outOfLandscape) {
                    mobiles.remove(mobile);
                }
                logger.info("Wind - finished to notify FALLEN");

                // set the new values
                transform.setTranslation(translation);
                currentTG.setTransform(transform);
                logger.info("Wind - at the end of the synchronized block");
            }
            logger.info("Wind - after the synchronized block");
        }
    }

    protected Collection<Mobile> getMobiles() {
        return mobiles;
    }

}
