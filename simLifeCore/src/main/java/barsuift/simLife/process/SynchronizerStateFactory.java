package barsuift.simLife.process;

import barsuift.simLife.time.DateHandlerState;



public class SynchronizerStateFactory {

    public SynchronizerState createSynchronizerState() {
        Speed speed = Speed.NORMAL;
        DateHandlerState dateHandler = new DateHandlerState();
        return new SynchronizerState(speed, dateHandler);
    }

}
