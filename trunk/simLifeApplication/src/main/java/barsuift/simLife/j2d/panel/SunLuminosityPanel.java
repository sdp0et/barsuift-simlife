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

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import barsuift.simLife.PercentHelper;
import barsuift.simLife.environment.Sun;
import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;


public class SunLuminosityPanel extends JPanel implements ChangeListener, Subscriber {

    private static final long serialVersionUID = -6102868842517781193L;

    private static final int LUMINOSITY_MIN = 0;

    private static final int LUMINOSITY_MAX = 100;

    private static final MessageFormat LABEL_FORMAT = new MessageFormat("Sun luminosity ({0})");

    private final Sun sun;

    private final JLabel sliderLabel;

    private final JSlider luminositySlider;

    public SunLuminosityPanel(Sun sun) {
        this.sun = sun;
        sun.addSubscriber(this);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        sliderLabel = createLabel();
        luminositySlider = createSlider();
        add(sliderLabel);
        add(luminositySlider);
    }

    private JSlider createSlider() {
        JSlider luminositySlider = new JSlider(JSlider.HORIZONTAL, LUMINOSITY_MIN, LUMINOSITY_MAX,
                PercentHelper.getIntValue(sun.getLuminosity()));
        luminositySlider.addChangeListener(this);
        // Turn on labels at major tick marks.
        luminositySlider.setMajorTickSpacing(20);
        luminositySlider.setMinorTickSpacing(5);
        luminositySlider.setPaintTicks(true);
        luminositySlider.setPaintLabels(true);
        return luminositySlider;
    }

    private JLabel createLabel() {
        JLabel sliderLabel = new JLabel(createLuminosityLabelText(), JLabel.CENTER);
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sliderLabel.setMinimumSize(sliderLabel.getPreferredSize());
        return sliderLabel;
    }

    private String createLuminosityLabelText() {
        return LABEL_FORMAT.format(new Object[] { PercentHelper.getStringValue(sun.getLuminosity()) });
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        int luminosity = (int) source.getValue();
        sun.setLuminosity(PercentHelper.getDecimalValue(luminosity));
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        if (arg == SunUpdateCode.luminosity) {
            sliderLabel.setText(createLuminosityLabelText());
            luminositySlider.setValue(PercentHelper.getIntValue(sun.getLuminosity()));
        }
    }

    protected JLabel getLabel() {
        return sliderLabel;
    }

    protected JSlider getSlider() {
        return luminositySlider;
    }

}
