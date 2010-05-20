package barsuift.simLife.j2d.panel;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import barsuift.simLife.environment.Sun;


public class SunPanel extends JPanel {

    private static final long serialVersionUID = -6102868842517781193L;

    public SunPanel(Sun sun) {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));

        rightPanel.add(new SunLuminosityPanel(sun));
        rightPanel.add(new SunColorPanel(sun.getSun3D()));
        rightPanel.add(new SunRisePanel(sun));

        add(rightPanel);
        add(new SunZenithPanel(sun));

    }

}
