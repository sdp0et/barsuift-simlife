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
import java.text.MessageFormat;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import barsuift.simLife.Automatable;
import barsuift.simLife.MathHelper;
import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.j2d.action.menu.AutomaticAction;
import barsuift.simLife.j3d.environment.Sun3D;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;

public class EarthRotationPanel extends JPanel implements ChangeListener, Subscriber, Automatable {

    private static final long serialVersionUID = -6102868842517781193L;

    private static final int ANGLE_MIN = 0;

    private static final int ANGLE_MAX = 360;

    private static final MessageFormat LABEL_FORMAT = new MessageFormat("Earth rotation ({0}°)");

    private final Sun3D sun3D;

    private final JSlider earthRotationSlider;

    private boolean automatic;

    private JCheckBox checkbox;

    public EarthRotationPanel(Sun3D sun3D) {
        this.sun3D = sun3D;
        sun3D.addSubscriber(this);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        earthRotationSlider = createSlider();

        checkbox = new JCheckBox();
        checkbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        setAutomatic(sun3D.isEarthRotationTaskAutomatic());
        checkbox.setAction(new AutomaticAction(this));
        checkbox.setText(createLabelText());

        add(checkbox);
        add(earthRotationSlider);
    }

    private JSlider createSlider() {
        JSlider earthRotationSlider = new JSlider(JSlider.HORIZONTAL, ANGLE_MIN, ANGLE_MAX, Math.round(MathHelper
                .toDegree(sun3D.getEarthRotation())));
        earthRotationSlider.addChangeListener(this);
        // Turn on labels at major tick marks.
        earthRotationSlider.setMajorTickSpacing(90);
        earthRotationSlider.setMinorTickSpacing(45);
        earthRotationSlider.setPaintTicks(true);

        // Create the label table
        Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable.put(new Integer(ANGLE_MIN), new JLabel("Midnight"));
        labelTable.put(new Integer(ANGLE_MAX / 4), new JLabel("Sunrise"));
        labelTable.put(new Integer(ANGLE_MAX / 2), new JLabel("Noon"));
        labelTable.put(new Integer(3 * ANGLE_MAX / 4), new JLabel("Sunset"));
        labelTable.put(new Integer(ANGLE_MAX), new JLabel("Midnight"));
        earthRotationSlider.setLabelTable(labelTable);
        earthRotationSlider.setPaintLabels(true);

        return earthRotationSlider;
    }

    private String createLabelText() {
        return LABEL_FORMAT.format(new Object[] { Math.round(MathHelper.toDegree(sun3D.getEarthRotation())) });
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (!automatic) {
            int earthRotation = earthRotationSlider.getValue();
            sun3D.setEarthRotation(MathHelper.toRadian(earthRotation));
            checkbox.setText(createLabelText());
        }
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        if (arg == SunUpdateCode.EARTH_ROTATION) {
            if (automatic) {
                checkbox.setText(createLabelText());
                earthRotationSlider.setValue(Math.round(MathHelper.toDegree(sun3D.getEarthRotation())));
            }
        }
    }

    protected JCheckBox getCheckBox() {
        return checkbox;
    }

    protected JSlider getSlider() {
        return earthRotationSlider;
    }

    @Override
    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
        earthRotationSlider.setEnabled(!automatic);
        sun3D.setEarthRotationTaskAutomatic(automatic);
    }

    @Override
    public boolean isAutomatic() {
        return automatic;
    }

}
