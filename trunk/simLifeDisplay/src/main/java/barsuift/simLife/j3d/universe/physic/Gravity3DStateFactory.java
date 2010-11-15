package barsuift.simLife.j3d.universe.physic;

import barsuift.simLife.process.ConditionalTaskStateFactory;
import barsuift.simLife.process.GravityTask;
import barsuift.simLife.process.SplitConditionalTaskState;


public class Gravity3DStateFactory {

    public Gravity3DState createGravity3DState() {
        ConditionalTaskStateFactory taskFactory = new ConditionalTaskStateFactory();
        SplitConditionalTaskState gravityTaskState = taskFactory.createSplitConditionalTaskState(GravityTask.class);
        return new Gravity3DState(gravityTaskState);
    }

}
