package barsuift.simLife.j2d.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;
import barsuift.simLife.time.TimeController;

// TODO 005. once TimeMessenger is removed, the button classes should use directly the synchronizer
public class OneStepButton extends JButton implements Subscriber {

    private static final long serialVersionUID = 1547061708163353428L;

    public OneStepButton(final TimeController controller) {
        super("ONE STEP");
        controller.addSubscriber(this);
        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabled(false);
                controller.oneStep();
            }
        });
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        if (((TimeController) publisher).isRunning()) {
            setEnabled(false);
        } else {
            setEnabled(true);
        }
    }

}
