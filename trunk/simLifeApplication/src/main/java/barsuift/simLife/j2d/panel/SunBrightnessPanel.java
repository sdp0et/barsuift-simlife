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

import barsuift.simLife.PercentHelper;
import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.j3d.environment.Sun3D;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;

public class SunBrightnessPanel extends JPanel implements Subscriber {

    private static final long serialVersionUID = -6102868842517781193L;

    private static final int LUMINOSITY_MIN = 0;

    private static final int LUMINOSITY_MAX = 100;

    private static final MessageFormat LABEL_FORMAT = new MessageFormat("Sun brightness ({0})");

    private final Sun3D sun3D;

    private final JLabel sliderLabel;

    private final JSlider brightnessSlider;

    public SunBrightnessPanel(Sun3D sun3D) {
        this.sun3D = sun3D;
        sun3D.addSubscriber(this);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        sliderLabel = createLabel();
        brightnessSlider = createSlider();
        add(sliderLabel);
        add(brightnessSlider);
    }

    private JSlider createSlider() {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, LUMINOSITY_MIN, LUMINOSITY_MAX,
                PercentHelper.getIntValue(sun3D.getBrightness()));
        slider.setEnabled(false);
        // Turn on labels at major tick marks.
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        return slider;
    }

    private JLabel createLabel() {
        JLabel sliderLabel = new JLabel(createLabelText(), JLabel.CENTER);
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sliderLabel.setMinimumSize(sliderLabel.getPreferredSize());
        return sliderLabel;
    }

    private String createLabelText() {
        return LABEL_FORMAT.format(new Object[] { PercentHelper.getStringValue(sun3D.getBrightness()) });
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        if (arg == SunUpdateCode.BRIGHTNESS) {
            sliderLabel.setText(createLabelText());
            brightnessSlider.setValue(PercentHelper.getIntValue(sun3D.getBrightness()));
        }
    }

    protected JLabel getLabel() {
        return sliderLabel;
    }

    protected JSlider getSlider() {
        return brightnessSlider;
    }

}
