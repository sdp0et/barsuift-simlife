package barsuift.simLife.j2d.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import barsuift.simLife.time.TimeController;


public class SpeedAction extends AbstractAction {

    private static final long serialVersionUID = -8115155709776072647L;

    private final TimeController timeController;

    public SpeedAction(TimeController timeController, String name, String description, int mnemonic,
            KeyStroke accelerator, String actionCommand) {
        super();
        this.timeController = timeController;
        putValue(MNEMONIC_KEY, mnemonic);
        putValue(ACCELERATOR_KEY, accelerator);
        putValue(ACTION_COMMAND_KEY, actionCommand);
        putValue(NAME, name);
        putValue(SHORT_DESCRIPTION, description);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCmd = e.getActionCommand();
        int speed = Integer.valueOf(actionCmd).intValue();
        timeController.setSpeed(speed);
    }

}
