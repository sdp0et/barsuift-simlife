package barsuift.simLife.j3d.universe;

import barsuift.simLife.process.Synchronizer3DState;
import barsuift.simLife.process.Synchronizer3DStateFactory;

public class Universe3DStateFactory {

    public Universe3DState createUniverse3DState() {
        Synchronizer3DStateFactory synchronizerStateFactory = new Synchronizer3DStateFactory();
        Synchronizer3DState synchronizerState = synchronizerStateFactory.createSynchronizer3DState();
        return new Universe3DState(synchronizerState);
    }

}
