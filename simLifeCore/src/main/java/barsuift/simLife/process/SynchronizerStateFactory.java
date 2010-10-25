package barsuift.simLife.process;

import barsuift.simLife.time.DateHandlerState;



public class SynchronizerStateFactory {

    public SynchronizerState createSynchronizerState() {
        int speed = 1;
        DateHandlerState dateHandler = new DateHandlerState();
        return new SynchronizerState(speed, dateHandler);
    }

}
