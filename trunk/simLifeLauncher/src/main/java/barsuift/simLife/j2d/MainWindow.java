package barsuift.simLife.j2d;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import barsuift.simLife.Application;
import barsuift.simLife.j2d.menu.MenuFactory;
import barsuift.simLife.j2d.panel.MainPanel;
import barsuift.simLife.universe.Universe;


public class MainWindow extends JFrame {

    private static final long serialVersionUID = 2791518087247151942L;

    private MainPanel mainPanel;

    public MainWindow(Application application) {
        super("SimLife");
        int width = 768;
        int height = 512;
        setBounds(0, 0, width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MenuFactory menuFactory = new MenuFactory();
        JMenuBar menuBar = menuFactory.createMenuBar(application);
        setJMenuBar(menuBar);
    }

    /**
     * Destroy the current panel (if any) and creates a new one from the given universe.
     * 
     * @param universe the new universe to display
     */
    public void changeUniverse(Universe universe) {
        mainPanel = null;
        mainPanel = new MainPanel(universe);
        setContentPane(mainPanel);
        validate();
    }

}
