package barsuift.simLife.j2d.panel;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;


public class TerrainParametersPanel extends JPanel {

    private static final long serialVersionUID = 2609564426686409556L;

    private JSlider sizeSlider;

    public TerrainParametersPanel() {
        sizeSlider = createSizeSlider();
        JLabel sizeSliderLabel = createSizeSliderLabel();
        add(sizeSliderLabel);
        add(sizeSlider);
    }

    private JSlider createSizeSlider() {
        JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL, 5, 9, 7);
        sizeSlider.setMaximumSize(new Dimension(80, 180));

        //TODO 001. see if the "ticks" could "attract" the knob
        
        // Create the label table
        Dictionary<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
        labels.put(5, new JLabel("32"));
        labels.put(6, new JLabel("64"));
        labels.put(7, new JLabel("128"));
        labels.put(8, new JLabel("256"));
        labels.put(9, new JLabel("512"));
        sizeSlider.setLabelTable(labels);
        sizeSlider.setPaintLabels(true);
        
        sizeSlider.setSnapToTicks(true);

        return sizeSlider;
    }

    private JLabel createSizeSliderLabel() {
        JLabel sizeSliderLabel = new JLabel("Size", JLabel.CENTER);
        sizeSliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return sizeSliderLabel;
    }



    public int getSizeValue() {
        return sizeSlider.getValue();
    }

}
