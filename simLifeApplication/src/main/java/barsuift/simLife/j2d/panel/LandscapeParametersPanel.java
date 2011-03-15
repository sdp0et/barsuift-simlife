/**
 * barsuift-simlife is a life simulator program
 * 
 * Copyright (C) 2010 Cyrille GACHOT
 * 
 * This file is part of barsuift-simlife.
 * 
 * barsuift-simlife is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * barsuift-simlife is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with barsuift-simlife. If not, see
 * <http://www.gnu.org/licenses/>.
 */
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

import barsuift.simLife.MathHelper;
import barsuift.simLife.j2d.ParametersDependent;
import barsuift.simLife.landscape.LandscapeParameters;


public class LandscapeParametersPanel extends JPanel implements ParametersDependent {

    private static final long serialVersionUID = 2609564426686409556L;

    private final LandscapeParameters parameters;

    private final JSlider sizeSlider;

    private final JSlider maxHeightSlider;

    private JSlider roughnessSlider;

    private JSlider erosionSlider;

    public LandscapeParametersPanel(LandscapeParameters parameters) {
        super();
        this.parameters = parameters;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        Border blacklineBorder = BorderFactory.createLineBorder(Color.black);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(blacklineBorder, "Landscape");
        setBorder(titledBorder);

        sizeSlider = createSizeSlider(parameters);
        maxHeightSlider = createMaxHeightSlider(parameters);
        roughnessSlider = createRoughnessSlider(parameters);
        erosionSlider = createErosionSlider(parameters);

        add(createLabel("Size (meters)"));
        add(sizeSlider);

        add(Box.createRigidArea(new Dimension(0, 20)));

        add(createLabel("Maximum height (meters)"));
        add(maxHeightSlider);

        add(Box.createRigidArea(new Dimension(0, 20)));

        add(createLabel("Roughness"));
        add(roughnessSlider);

        add(Box.createRigidArea(new Dimension(0, 20)));

        add(createLabel("Erosion"));
        add(erosionSlider);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JSlider createSizeSlider(LandscapeParameters parameters) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, LandscapeParameters.SIZE_MIN_EXPONENT,
                LandscapeParameters.SIZE_MAX_EXPONENT, MathHelper.getPowerOfTwoExponent(parameters.getSize()));
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(1);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        // Create the label table
        Dictionary<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
        for (int exponent = LandscapeParameters.SIZE_MIN_EXPONENT; exponent <= LandscapeParameters.SIZE_MAX_EXPONENT; exponent++) {
            labels.put(exponent, new JLabel(Integer.toString(1 << exponent)));
        }
        slider.setLabelTable(labels);

        return slider;
    }

    private JSlider createMaxHeightSlider(LandscapeParameters parameters) {
        JSlider maxHeightSlider = new JSlider(JSlider.HORIZONTAL, LandscapeParameters.MAX_HEIGHT_MIN,
                LandscapeParameters.MAX_HEIGHT_MAX, Math.round(parameters.getMaximumHeight()));
        maxHeightSlider.setPaintTicks(true);
        maxHeightSlider.setMajorTickSpacing(10);
        maxHeightSlider.setPaintLabels(true);
        return maxHeightSlider;
    }

    private JSlider createRoughnessSlider(LandscapeParameters parameters) {
        int min = Math.round(LandscapeParameters.ROUGHNESS_MIN * 100);
        int max = Math.round(LandscapeParameters.ROUGHNESS_MAX * 100);
        int current = Math.round(parameters.getRoughness() * 100);
        JSlider roughnessSlider = new JSlider(JSlider.HORIZONTAL, min, max, current);
        roughnessSlider.setPaintTicks(true);
        roughnessSlider.setMajorTickSpacing((max - min) / 5);
        roughnessSlider.setPaintLabels(true);
        // Create the label table
        Dictionary<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
        labels.put(min, new JLabel("Very smooth"));
        labels.put(max, new JLabel("Absolute chaos"));
        roughnessSlider.setLabelTable(labels);

        return roughnessSlider;
    }

    private JSlider createErosionSlider(LandscapeParameters parameters) {
        int min = Math.round(LandscapeParameters.EROSION_MIN * 100);
        int max = Math.round(LandscapeParameters.EROSION_MAX * 100);
        int current = Math.round(parameters.getErosion() * 100);
        JSlider erosionSlider = new JSlider(JSlider.HORIZONTAL, min, max, current);
        erosionSlider.setPaintTicks(true);
        erosionSlider.setMajorTickSpacing((max - min) / 5);
        erosionSlider.setPaintLabels(true);
        // Create the label table
        Dictionary<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
        labels.put(min, new JLabel("Sharp (no erosion)"));
        labels.put(max, new JLabel("Flat (complete erosion)"));
        erosionSlider.setLabelTable(labels);

        return erosionSlider;
    }

    @Override
    public void readFromParameters() {
        sizeSlider.setValue(MathHelper.getPowerOfTwoExponent(parameters.getSize()));
        maxHeightSlider.setValue(Math.round(parameters.getMaximumHeight()));
        roughnessSlider.setValue(Math.round(parameters.getRoughness() * 100));
        erosionSlider.setValue(Math.round(parameters.getErosion() * 100));
    }

    @Override
    public void writeIntoParameters() {
        parameters.setSize(1 << sizeSlider.getValue());
        parameters.setMaximumHeight(maxHeightSlider.getValue());
        parameters.setRoughness((float) roughnessSlider.getValue() / 100);
        parameters.setErosion((float) erosionSlider.getValue() / 100);
    }

}
