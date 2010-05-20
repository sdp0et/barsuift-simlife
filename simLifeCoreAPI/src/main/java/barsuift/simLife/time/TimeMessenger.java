package barsuift.simLife.time;

import barsuift.simLife.universe.Universe;

public class TimeMessenger implements Runnable {

    private final Universe universe;

    public TimeMessenger(Universe universe) {
        this.universe = universe;
    }

    @Override
    public void run() {
        universe.spendTime();
    }

    public TimeCounter getTimeCounter() {
        return universe.getCounter();
    }

}
