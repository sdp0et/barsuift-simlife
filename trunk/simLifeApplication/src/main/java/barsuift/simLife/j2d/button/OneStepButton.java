package barsuift.simLife.j2d.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;
import barsuift.simLife.process.SynchronizerCore;

public class OneStepButton extends JButton implements Subscriber {

    private static final long serialVersionUID = 1547061708163353428L;

    public OneStepButton(final SynchronizerCore synchronizer) {
        super("ONE STEP");
        synchronizer.addSubscriber(this);
        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabled(false);
                synchronizer.oneStep();
            }
        });
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        if (((SynchronizerCore) publisher).isRunning()) {
            setEnabled(false);
        } else {
            setEnabled(true);
        }
    }

}
