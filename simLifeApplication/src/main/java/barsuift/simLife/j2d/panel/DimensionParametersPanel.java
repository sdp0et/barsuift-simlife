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
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import barsuift.simLife.DimensionParameters;
import barsuift.simLife.MathHelper;
import barsuift.simLife.j2d.ParametersDependent;


public class DimensionParametersPanel extends JPanel implements ParametersDependent {

    private static final long serialVersionUID = 4471355030694189610L;

    private final DimensionParameters parameters;

    private JSlider sizeSlider;

    public DimensionParametersPanel(DimensionParameters parameters) {
        super();
        this.parameters = parameters;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        Border blacklineBorder = BorderFactory.createLineBorder(Color.black);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(blacklineBorder, "Dimension");
        setBorder(titledBorder);

        sizeSlider = createSizeSlider(parameters);

        add(createLabel("Size (meters)"));
        add(sizeSlider);
    }

    private JSlider createSizeSlider(DimensionParameters parameters) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, DimensionParameters.SIZE_MIN_EXPONENT,
                DimensionParameters.SIZE_MAX_EXPONENT, MathHelper.getPowerOfTwoExponent(parameters.getSize()));
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(1);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        // Create the label table
        Dictionary<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
        for (int exponent = DimensionParameters.SIZE_MIN_EXPONENT; exponent <= DimensionParameters.SIZE_MAX_EXPONENT; exponent++) {
            labels.put(exponent, new JLabel(Integer.toString(1 << exponent)));
        }
        slider.setLabelTable(labels);

        return slider;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    @Override
    public void readFromParameters() {
        sizeSlider.setValue(MathHelper.getPowerOfTwoExponent(parameters.getSize()));
    }

    @Override
    public void writeIntoParameters() {
        parameters.setSize(1 << sizeSlider.getValue());
    }

}
