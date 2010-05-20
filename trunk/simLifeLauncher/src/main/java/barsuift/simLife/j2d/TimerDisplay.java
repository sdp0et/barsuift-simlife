package barsuift.simLife.j2d;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import barsuift.simLife.time.TimeCounter;

public class TimerDisplay extends JLabel implements Observer {

    private static final long serialVersionUID = 6381218933947453660L;

    private TimeCounter counter;

    public TimerDisplay(TimeCounter counter) {
        counter.addObserver(this);
        this.counter = counter;
        setText(counter.toString());
        setSize(200, 40);
    }

    @Override
    public void update(Observable o, Object arg) {
        setText(counter.toString());
    }

}
