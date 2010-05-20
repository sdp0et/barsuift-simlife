package barsuift.simLife.j2d.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import barsuift.simLife.Application;
import barsuift.simLife.universe.SaveException;


public class SaveAction extends AbstractAction {

    private static final long serialVersionUID = 8223229157394283604L;

    private final Application application;

    public SaveAction(Application application) {
        super();
        this.application = application;
        putValue(Action.NAME, "Save");
        putValue(SHORT_DESCRIPTION, "Save the current universe");
        putValue(MNEMONIC_KEY, KeyEvent.VK_S);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            application.saveUniverse();
        } catch (SaveException se) {
            System.out.println("Unable to save the universe to the current save file because " + se.getMessage());
        }
    }

}
