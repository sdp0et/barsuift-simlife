package barsuift.simLife.j2d.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

import barsuift.simLife.Application;
import barsuift.simLife.universe.SaveException;


public class SaveAsAction extends AbstractAction {

    private static final long serialVersionUID = -2391532464769897167L;

    private final Application application;

    public SaveAsAction(Application application) {
        super();
        this.application = application;
        putValue(Action.NAME, "Save As ...");
        putValue(SHORT_DESCRIPTION, "Save the current universe in a new file");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.SHIFT_MASK | ActionEvent.CTRL_MASK));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                application.saveUniverseAs(file);
            } catch (SaveException se) {
                System.out.println("Unable to save the universe to the given file : " + file.getAbsolutePath()
                        + " because " + se.getMessage());
            }
        }
    }

}
