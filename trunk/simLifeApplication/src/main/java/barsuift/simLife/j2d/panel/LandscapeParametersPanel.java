package barsuift.simLife.j2d.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import barsuift.simLife.landscape.LandscapeParameters;


public class LandscapeParametersPanel extends JPanel {

    private static final long serialVersionUID = 2609564426686409556L;

    private JSlider roughnessSlider;

    private JSlider maxHeightSlider;

    private JSlider erosionSlider;

    public LandscapeParametersPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        Border blacklineBorder = BorderFactory.createLineBorder(Color.black);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(blacklineBorder, "Landscape");
        setBorder(titledBorder);

        roughnessSlider = createRoughnessSlider();
        maxHeightSlider = createMaxHeightSlider();
        erosionSlider = createErosionSlider();

        JLabel roughnessLabel = new JLabel("Roughness", JLabel.CENTER);
        roughnessLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(roughnessLabel);
        add(roughnessSlider);

        add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel maxHeightLabel = new JLabel("Maximum height (meters)", JLabel.CENTER);
        maxHeightLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(maxHeightLabel);
        add(maxHeightSlider);

        add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel erosionLabel = new JLabel("Erosion", JLabel.CENTER);
        erosionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(erosionLabel);
        add(erosionSlider);
    }

    private JSlider createMaxHeightSlider() {
        JSlider maxHeightSlider = new JSlider(JSlider.HORIZONTAL, 0, 50, 20);
        maxHeightSlider.setPaintTicks(true);
        maxHeightSlider.setMajorTickSpacing(10);
        maxHeightSlider.setPaintLabels(true);
        return maxHeightSlider;
    }

    private JSlider createRoughnessSlider() {
        JSlider roughnessSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        roughnessSlider.setPaintTicks(true);
        roughnessSlider.setMajorTickSpacing(20);
        roughnessSlider.setPaintLabels(true);
        // Create the label table
        Dictionary<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
        labels.put(0, new JLabel("Very smooth"));
        labels.put(100, new JLabel("Absolute chaos"));
        roughnessSlider.setLabelTable(labels);

        return roughnessSlider;
    }

    private JSlider createErosionSlider() {
        JSlider erosionSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        erosionSlider.setPaintTicks(true);
        erosionSlider.setMajorTickSpacing(20);
        erosionSlider.setPaintLabels(true);
        // Create the label table
        Dictionary<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
        labels.put(0, new JLabel("Sharp (no erosion)"));
        labels.put(100, new JLabel("Flat (complete erosion)"));
        erosionSlider.setLabelTable(labels);

        return erosionSlider;
    }

    public LandscapeParameters getLandscapeParameters() {
        return new LandscapeParameters((float) roughnessSlider.getValue() / 100,
                (float) erosionSlider.getValue() / 100, maxHeightSlider.getValue());
    }

}
