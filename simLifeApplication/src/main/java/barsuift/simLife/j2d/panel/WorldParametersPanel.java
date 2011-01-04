package barsuift.simLife.j2d.panel;

import java.awt.Color;
import java.awt.Component;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import barsuift.simLife.CommonParameters;


public class WorldParametersPanel extends JPanel {

    private static final long serialVersionUID = 4471355030694189610L;

    private JSlider sizeSlider;

    public WorldParametersPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        Border blacklineBorder = BorderFactory.createLineBorder(Color.black);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(blacklineBorder, "World");
        setBorder(titledBorder);

        sizeSlider = createSizeSlider();

        add(createLabel("Size (meters)"));
        add(sizeSlider);
    }

    private JSlider createSizeSlider() {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 5, 9, 7);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(1);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        // Create the label table
        Dictionary<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
        labels.put(5, new JLabel("32"));
        labels.put(6, new JLabel("64"));
        labels.put(7, new JLabel("128"));
        labels.put(8, new JLabel("256"));
        labels.put(9, new JLabel("512"));
        slider.setLabelTable(labels);

        return slider;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    public CommonParameters getCommonParameters() {
        return new CommonParameters((int) Math.pow(2, sizeSlider.getValue()));
    }

}
