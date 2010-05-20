package barsuift.simLife.j2d.panel;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import barsuift.simLife.time.UniverseTimeController;
import barsuift.simLife.universe.Universe;


public class MainPanel extends JPanel {

    private static final long serialVersionUID = -3396495972510779750L;

    /**
     * Creates the main panel, with box layout, ordered along the LINE axis
     * 
     * @param universe the universe
     */
    public MainPanel(Universe universe) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));

        JPanel environmentPanel = new EnvironmentPanel(universe.getEnvironment());
        rightPanel.add(environmentPanel);

        UniverseTimeController timeController = new UniverseTimeController(universe);
        JPanel controllerPanel = new TimeControllerPanel(timeController);
        rightPanel.add(controllerPanel);

        add(rightPanel);

        Universe3DPanel universe3DPanel = new Universe3DPanel(universe.getUniverse3D());
        universe3DPanel.setAxis();
        add(universe3DPanel);
    }

}
