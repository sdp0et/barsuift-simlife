package barsuift.simLife.j2d.panel;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import barsuift.simLife.environment.Environment;


public class EnvironmentPanel extends JPanel {

    private static final long serialVersionUID = 2499187337226355527L;

    // private final Environment environment;

    /**
     * Creates the environment panel, with box layout, ordered along the PAGE axis
     * 
     * @param environment the universe environment
     */
    public EnvironmentPanel(Environment environment) {
        super();
        // this.environment = environment;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(new SunPanel(environment.getSun()));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        Border blacklineBorder = BorderFactory.createLineBorder(Color.black);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(blacklineBorder, "Environment");
        setBorder(titledBorder);

    }

}
