package barsuift.simLife.j2d.panel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import barsuift.simLife.j3d.terrain.MidPointHeightMapParameters;


public class TerrainParametersPanel extends JPanel {

    private static final long serialVersionUID = 2609564426686409556L;

    private JSlider sizeSlider;

    private JSlider roughnessSlider;

    private JSlider maxHeightSlider;

    private JSlider erosionSlider;

    public TerrainParametersPanel() {
        super(new GridLayout(2, 2, 20, 20));

        sizeSlider = createSizeSlider();
        roughnessSlider = createSlider(0, 100, 50);
        maxHeightSlider = createSlider(0, 50, 20);
        erosionSlider = createSlider(0, 100, 50);

        add(createPanel(createLabel("Size (m)"), sizeSlider));
        add(createPanel(createLabel("Roughness (%)"), roughnessSlider));
        add(createPanel(createLabel("Maximum height (m)"), maxHeightSlider));
        add(createPanel(createLabel("Erosion (%)"), erosionSlider));
    }

    private JPanel createPanel(JLabel label, JSlider slider) {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(label);
        panel.add(slider);
        return panel;
    }

    private JSlider createSizeSlider() {
        JSlider slider = createSlider(5, 9, 7);

        slider.setMajorTickSpacing(1);
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

    private JSlider createSlider(int min, int max, int current) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, current);
        slider.setMaximumSize(new Dimension(80, 180));
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(10);
        slider.setPaintLabels(true);
        return slider;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    public MidPointHeightMapParameters getTerrainParameters() {
        return new MidPointHeightMapParameters((int) Math.pow(2, sizeSlider.getValue()),
                (float) roughnessSlider.getValue() / 100, (float) erosionSlider.getValue() / 100,
                maxHeightSlider.getValue());
    }

}
