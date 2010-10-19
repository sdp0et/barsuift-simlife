package barsuift.simLife.j2d.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;
import barsuift.simLife.time.TimeController;


public class StopButton extends JButton implements Subscriber {

    private static final long serialVersionUID = 8982250197152953973L;

    public StopButton(final TimeController controller) {
        super("STOP");
        controller.addSubscriber(this);
        setVisible(false);
        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                controller.pause();
            }

        });
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        if (((TimeController) publisher).isRunning()) {
            setVisible(true);
        } else {
            setVisible(false);
        }
    }

}
