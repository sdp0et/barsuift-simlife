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

import barsuift.simLife.j2d.ParametersDependent;
import barsuift.simLife.landscape.LandscapeParameters;


public class LandscapeParametersPanel extends JPanel implements ParametersDependent {

    private static final long serialVersionUID = 2609564426686409556L;

    private final LandscapeParameters parameters;

    private JSlider roughnessSlider;

    private JSlider maxHeightSlider;

    private JSlider erosionSlider;

    public LandscapeParametersPanel(LandscapeParameters parameters) {
        super();
        this.parameters = parameters;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        Border blacklineBorder = BorderFactory.createLineBorder(Color.black);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(blacklineBorder, "Landscape");
        setBorder(titledBorder);

        roughnessSlider = createRoughnessSlider(parameters);
        maxHeightSlider = createMaxHeightSlider(parameters);
        erosionSlider = createErosionSlider(parameters);

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
        roughnessSlider.setValue(Math.round(parameters.getRoughness() * 100));
        erosionSlider.setValue(Math.round(parameters.getErosion() * 100));
        maxHeightSlider.setValue(Math.round(parameters.getMaximumHeight()));
    }

    @Override
    public void writeIntoParameters() {
        parameters.setRoughness((float) roughnessSlider.getValue() / 100);
        parameters.setErosion((float) erosionSlider.getValue() / 100);
        parameters.setMaximumHeight(maxHeightSlider.getValue());
    }

}
