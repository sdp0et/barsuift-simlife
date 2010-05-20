package barsuift.simLife.j2d.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import barsuift.simLife.Application;


public class NewRandomAction extends AbstractAction {

    private static final long serialVersionUID = -7620926200302148499L;

    private final Application application;

    public NewRandomAction(Application application) {
        super();
        this.application = application;
        putValue(Action.NAME, "New (random)");
        putValue(SHORT_DESCRIPTION, "Create a new random universe");
        putValue(MNEMONIC_KEY, KeyEvent.VK_R);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        application.createRandomUniverse();
    }

}
