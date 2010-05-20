package barsuift.simLife.time;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import junit.framework.Assert;
import barsuift.simLife.IObservable;


public class ObservableTestHelper extends Assert {

    private int nbUpdated;

    private List<Object> updateObjects;

    public ObservableTestHelper() {
        reset();
    }

    public void reset() {
        nbUpdated = 0;
        updateObjects = new ArrayList<Object>();
    }

    public void addObserver(final Observable obs) {
        obs.addObserver(new Observer() {

            public void update(Observable o, Object arg) {
                assertEquals(obs, o);
                nbUpdated++;
                updateObjects.add(arg);
            }

        });
    }

    public void addIObserver(final IObservable obs) {
        obs.addObserver(new Observer() {

            public void update(Observable o, Object arg) {
                assertEquals(obs, o);
                nbUpdated++;
                updateObjects.add(arg);
            }

        });
    }

    public int nbUpdated() {
        return nbUpdated;
    }

    public List<Object> getUpdateObjects() {
        return updateObjects;
    }

}
