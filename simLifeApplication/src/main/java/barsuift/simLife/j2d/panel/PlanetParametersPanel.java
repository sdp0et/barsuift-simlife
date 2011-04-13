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
import java.text.MessageFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import barsuift.simLife.MathHelper;
import barsuift.simLife.PlanetParameters;
import barsuift.simLife.j2d.ParametersDependent;


public class PlanetParametersPanel extends JPanel implements ChangeListener, ParametersDependent {

    private static final long serialVersionUID = 4471355030694189610L;

    private static final MessageFormat LATITUDE_LABEL_FORMAT = new MessageFormat("Latitude ({0}°)");

    private static final MessageFormat ECLIPTIC_OBLIQUITY_LABEL_FORMAT = new MessageFormat(
            "Planet Ecliptic Obliquity ({0}°)");


    private final PlanetParameters parameters;

    /**
     * the slider value is in degree
     */
    private final JSlider latitudeSlider;

    private final JLabel latitudeLabel;

    /**
     * the slider value is in degree
     */
    private final JSlider eclipticObliquitySlider;

    private final JLabel eclipticObliquityLabel;

    public PlanetParametersPanel(PlanetParameters parameters) {
        super();
        this.parameters = parameters;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        Border blacklineBorder = BorderFactory.createLineBorder(Color.black);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(blacklineBorder, "Planet");
        setBorder(titledBorder);

        this.latitudeSlider = createLatitudeSlider(parameters);
        this.latitudeLabel = createLabel(createLatitudeLabelText());
        this.eclipticObliquitySlider = createEclipticObliquitySlider(parameters);
        this.eclipticObliquityLabel = createLabel(createEclipticObliquityLabelText());

        add(latitudeLabel);
        add(latitudeSlider);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(eclipticObliquityLabel);
        add(eclipticObliquitySlider);
    }

    private JSlider createLatitudeSlider(PlanetParameters parameters) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL,
                Math.round(MathHelper.toDegree(PlanetParameters.LATITUDE_MIN)), Math.round(MathHelper
                        .toDegree(PlanetParameters.LATITUDE_MAX)), Math.round(MathHelper.toDegree(parameters
                        .getLatitude())));
        slider.addChangeListener(this);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(10);
        slider.setPaintLabels(true);
        return slider;
    }

    private JSlider createEclipticObliquitySlider(PlanetParameters parameters) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, Math.round(MathHelper
                .toDegree(PlanetParameters.ECLIPTIC_OBLIQUITY_MIN)), Math.round(MathHelper
                .toDegree(PlanetParameters.ECLIPTIC_OBLIQUITY_MAX)), Math.round(MathHelper.toDegree(parameters
                .getEclipticObliquity())));
        slider.addChangeListener(this);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(10);
        slider.setPaintLabels(true);
        return slider;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private String createLatitudeLabelText() {
        return LATITUDE_LABEL_FORMAT.format(new Object[] { latitudeSlider.getValue() });
    }

    private String createEclipticObliquityLabelText() {
        return ECLIPTIC_OBLIQUITY_LABEL_FORMAT.format(new Object[] { eclipticObliquitySlider.getValue() });
    }

    protected String getLatitudeText() {
        return latitudeLabel.getText();
    }

    protected JSlider getLatitudeSlider() {
        return latitudeSlider;
    }

    protected String getEclipticObliquityText() {
        return eclipticObliquityLabel.getText();
    }

    protected JSlider getEclipticObliquitySlider() {
        return eclipticObliquitySlider;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == latitudeSlider) {
            latitudeLabel.setText(createLatitudeLabelText());
        }
        if (e.getSource() == eclipticObliquitySlider) {
            eclipticObliquityLabel.setText(createEclipticObliquityLabelText());
        }
    }

    @Override
    public void readFromParameters() {
        latitudeSlider.setValue(Math.round(MathHelper.toDegree(parameters.getLatitude())));
        eclipticObliquitySlider.setValue(Math.round(MathHelper.toDegree(parameters.getEclipticObliquity())));
    }

    @Override
    public void writeIntoParameters() {
        parameters.setLatitude((float) MathHelper.toRadian(latitudeSlider.getValue()));
        parameters.setEclipticObliquity((float) MathHelper.toRadian(eclipticObliquitySlider.getValue()));
    }

}
