package barsuift.simLife;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class MockObserver {

    private int updateCounter;

    private List<Observable> observableObjectsObserved;

    private List<Object> arguments;

    public MockObserver() {
        resetObserver();
    }

    public void resetObserver() {
        updateCounter = 0;
        observableObjectsObserved = new ArrayList<Observable>();
        arguments = new ArrayList<Object>();
    }

    public void update(Observable o, Object arg) {
        updateCounter++;
        observableObjectsObserved.add(o);
        arguments.add(arg);
    }

    public List<Object> getArguments() {
        return arguments;
    }

    public List<Observable> getObservableObjectsObserved() {
        return observableObjectsObserved;
    }

    public int getUpdateCounter() {
        return updateCounter;
    }
}
