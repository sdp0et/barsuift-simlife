/**
 * barsuift-simlife is a life simulator programm
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

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import barsuift.simLife.Percent;
import barsuift.simLife.environment.Sun;

public class SunRisePanel extends JPanel implements ChangeListener {

    private static final long serialVersionUID = -6102868842517781193L;

    private static final int ANGLE_MIN = 0;

    private static final int ANGLE_MAX = 100;

    private final Sun sun;

    private final JLabel sliderLabel;

    private final JSlider riseSlider;

    public SunRisePanel(Sun sun) {
        this.sun = sun;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        sliderLabel = createLabel();
        riseSlider = createSlider();
        add(sliderLabel);
        add(riseSlider);
    }

    private JSlider createSlider() {
        JSlider riseSlider = new JSlider(JSlider.HORIZONTAL, ANGLE_MIN, ANGLE_MAX, sun.getRiseAngle().getIntValue());
        riseSlider.addChangeListener(this);
        // Turn on labels at major tick marks.
        riseSlider.setMajorTickSpacing(50);
        riseSlider.setMinorTickSpacing(10);
        riseSlider.setPaintTicks(true);

        // Create the label table
        Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable.put(new Integer(ANGLE_MIN), new JLabel("Sunrise"));
        labelTable.put(new Integer(ANGLE_MAX / 2), new JLabel("Zenith"));
        labelTable.put(new Integer(ANGLE_MAX), new JLabel("Sunset"));
        riseSlider.setLabelTable(labelTable);
        riseSlider.setPaintLabels(true);

        return riseSlider;
    }

    private JLabel createLabel() {
        JLabel sliderLabel = new JLabel("Rise angle", JLabel.CENTER);
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return sliderLabel;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        int riseAngle = (int) source.getValue();
        sun.setRiseAngle(new Percent(riseAngle));
    }

    protected JLabel getLabel() {
        return sliderLabel;
    }

    protected JSlider getSlider() {
        return riseSlider;
    }

}
