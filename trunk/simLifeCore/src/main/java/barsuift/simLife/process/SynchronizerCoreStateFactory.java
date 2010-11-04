package barsuift.simLife.process;


public class SynchronizerCoreStateFactory {

    public SynchronizerCoreState createSynchronizerState() {
        Speed speed = Speed.NORMAL;
        return new SynchronizerCoreState(speed);
    }

}
