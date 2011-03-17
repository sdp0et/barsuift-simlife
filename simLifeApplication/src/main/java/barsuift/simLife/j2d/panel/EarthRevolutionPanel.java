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

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import barsuift.simLife.environment.Sun;
import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;

public class EarthRevolutionPanel extends JPanel implements ChangeListener, Subscriber {

    private static final long serialVersionUID = -6102868842517781193L;

    private static final int ANGLE_MIN = 0;

    private static final int ANGLE_MAX = 100;

    private final Sun sun;

    private final JLabel sliderLabel;

    private final JSlider earthRevolutionSlider;

    public EarthRevolutionPanel(Sun sun) {
        this.sun = sun;
        sun.addSubscriber(this);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        sliderLabel = createLabel();
        earthRevolutionSlider = createSlider();
        add(sliderLabel);
        add(earthRevolutionSlider);
    }

    private JSlider createSlider() {
        JSlider earthRevolutionSlider = new JSlider(JSlider.HORIZONTAL, ANGLE_MIN, ANGLE_MAX, Math.round(sun
                .getEarthRevolution() * 100));
        earthRevolutionSlider.addChangeListener(this);
        // Turn on labels at major tick marks.
        earthRevolutionSlider.setMajorTickSpacing(50);
        earthRevolutionSlider.setMinorTickSpacing(25);
        earthRevolutionSlider.setPaintTicks(true);

        // Create the label table
        Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable.put(new Integer(ANGLE_MIN), new JLabel("Wim"));
        labelTable.put(new Integer(ANGLE_MAX / 4), new JLabel("Sprim"));
        labelTable.put(new Integer(ANGLE_MAX / 2), new JLabel("Sum"));
        labelTable.put(new Integer(3 * ANGLE_MAX / 4), new JLabel("Tom"));
        labelTable.put(new Integer(ANGLE_MAX), new JLabel("Wim"));
        earthRevolutionSlider.setLabelTable(labelTable);
        earthRevolutionSlider.setPaintLabels(true);

        return earthRevolutionSlider;
    }

    private JLabel createLabel() {
        JLabel sliderLabel = new JLabel("Earth revolution", JLabel.CENTER);
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return sliderLabel;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        int earthRevolution = source.getValue();
        sun.setEarthRevolution((float) earthRevolution / 100);
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        if (arg == SunUpdateCode.EARTH_REVOLUTION) {
            earthRevolutionSlider.setValue(Math.round(sun.getEarthRevolution() * 100));
        }
    }

    protected JLabel getLabel() {
        return sliderLabel;
    }

    protected JSlider getSlider() {
        return earthRevolutionSlider;
    }

}
