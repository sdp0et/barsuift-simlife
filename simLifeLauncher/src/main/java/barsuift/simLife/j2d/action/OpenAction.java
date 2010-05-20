package barsuift.simLife.j2d.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

import barsuift.simLife.Application;
import barsuift.simLife.universe.OpenException;


public class OpenAction extends AbstractAction {

    private static final long serialVersionUID = -7706268023944038274L;

    private final Application application;

    public OpenAction(Application application) {
        super();
        this.application = application;
        putValue(Action.NAME, "Open");
        putValue(SHORT_DESCRIPTION, "Open another universe");
        putValue(MNEMONIC_KEY, KeyEvent.VK_O);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                application.openUniverse(file);
            } catch (OpenException oe) {
                System.out.println("Unable to open the given file : " + file.getAbsolutePath() + " because "
                        + oe.getMessage());
            }
        }
    }

}
