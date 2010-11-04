package barsuift.simLife.process;




public class SynchronizerStateFactory {

    public SynchronizerState createSynchronizerState() {
        Speed speed = Speed.NORMAL;
        return new SynchronizerState(speed);
    }

}
