package barsuift.simLife.j2d.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;
import barsuift.simLife.time.TimeController;


public class StartButton extends JButton implements Subscriber {

    private static final long serialVersionUID = -264984224317451654L;

    public StartButton(final TimeController controller) {
        super("START");
        controller.addSubscriber(this);
        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                controller.start();
            }

        });
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        if (((TimeController) publisher).isRunning()) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }

}
