package barsuift.simLife.j2d.panel;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import barsuift.simLife.Percent;
import barsuift.simLife.environment.Sun;

public class SunZenithPanel extends JPanel implements ChangeListener {

    private static final long serialVersionUID = -6102868842517781193L;

    private static final int ANGLE_MIN = 0;

    private static final int ANGLE_MAX = 100;

    private final Sun sun;

    private final JLabel sliderLabel;

    private final JSlider zenithSlider;

    public SunZenithPanel(Sun sun) {
        this.sun = sun;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        sliderLabel = createLabel();
        zenithSlider = createSlider();
        add(sliderLabel);
        add(zenithSlider);
    }

    private JSlider createSlider() {
        JSlider zenithSlider = new JSlider(JSlider.VERTICAL, ANGLE_MIN, ANGLE_MAX, sun.getZenithAngle().getIntValue());
        zenithSlider.setMaximumSize(new Dimension(80, 180));
        zenithSlider.addChangeListener(this);
        // Turn on labels at major tick marks.
        zenithSlider.setMajorTickSpacing(20);
        // luminositySlider.setMinorTickSpacing(5);
        zenithSlider.setPaintTicks(true);

        // Create the label table
        Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable.put(new Integer(ANGLE_MIN), new JLabel("Horizon"));
        labelTable.put(new Integer(ANGLE_MAX), new JLabel("Zenith"));
        zenithSlider.setLabelTable(labelTable);
        zenithSlider.setPaintLabels(true);

        return zenithSlider;
    }

    private JLabel createLabel() {
        JLabel sliderLabel = new JLabel("Zenith angle", JLabel.CENTER);
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return sliderLabel;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        int zenithAngle = (int) source.getValue();
        sun.setZenithAngle(new Percent(zenithAngle));
    }

    protected JLabel getLabel() {
        return sliderLabel;
    }

    protected JSlider getSlider() {
        return zenithSlider;
    }

}
