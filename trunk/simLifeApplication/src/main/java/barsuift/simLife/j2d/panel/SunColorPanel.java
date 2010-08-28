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

import java.awt.Component;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.j3d.universe.environment.Sun3D;

public class SunColorPanel extends JPanel implements Observer {

    private static final long serialVersionUID = -6493265804580797791L;

    private static final int FACTOR_MIN = 0;

    private static final int FACTOR_MAX = 100;

    private final Sun3D sun3D;

    private final JLabel sliderLabel;

    private final JSlider slider;

    public SunColorPanel(Sun3D sun3D) {
        this.sun3D = sun3D;
        sun3D.addObserver(this);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        sliderLabel = createLabel();
        slider = createSlider();
        add(sliderLabel);
        add(slider);
    }

    private JSlider createSlider() {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, FACTOR_MIN, FACTOR_MAX, sun3D.getWhiteFactor().getIntValue());
        // Turn on labels at major tick marks.
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setEnabled(false);

        // Create the label table
        Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable.put(new Integer(FACTOR_MIN), new JLabel("Red"));
        labelTable.put(new Integer(FACTOR_MAX), new JLabel("White"));
        slider.setLabelTable(labelTable);
        slider.setPaintLabels(true);

        return slider;
    }

    private JLabel createLabel() {
        JLabel sliderLabel = new JLabel("Sun color", JLabel.CENTER);
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return sliderLabel;
    }

    protected JLabel getLabel() {
        return sliderLabel;
    }

    protected JSlider getSlider() {
        return slider;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == SunUpdateCode.color) {
            slider.setValue(sun3D.getWhiteFactor().getIntValue());
        }
    }

}
