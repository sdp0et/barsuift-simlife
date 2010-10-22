package barsuift.simLife.process;



public class SynchronizerStateFactory {

    public SynchronizerState createSynchronizerState() {
        int speed = 1;
        return new SynchronizerState(speed);
    }

}
