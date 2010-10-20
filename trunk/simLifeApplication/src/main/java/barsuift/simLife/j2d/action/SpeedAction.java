package barsuift.simLife.j2d.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import barsuift.simLife.time.TimeController;


public class SpeedAction extends AbstractAction {

    private static final long serialVersionUID = -8115155709776072647L;

    private final TimeController timeController;

    public SpeedAction(TimeController timeController, String name, String description, int mnemonic,
            String actionCommand) {
        super();
        this.timeController = timeController;
        putValue(MNEMONIC_KEY, mnemonic);
        putValue(ACTION_COMMAND_KEY, actionCommand);
        putValue(NAME, name);
        putValue(SHORT_DESCRIPTION, description);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean wasRunning = stopApp();
        setSpeed(e);
        if (wasRunning) {
            timeController.start();
        }
    }

    /**
     * If the application is running, stop it and return true. Else, simply return false;
     * 
     * @return true if the application was running, false otherwise
     */
    private boolean stopApp() {
        if (timeController.isRunning()) {
            timeController.stop();
            return true;
        }
        return false;
    }

    private void setSpeed(ActionEvent e) {
        String actionCmd = e.getActionCommand();
        int speed = Integer.valueOf(actionCmd).intValue();
        timeController.setSpeed(speed);
    }

}
