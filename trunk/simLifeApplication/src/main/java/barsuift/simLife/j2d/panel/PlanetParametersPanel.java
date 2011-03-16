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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import barsuift.simLife.MathHelper;
import barsuift.simLife.PlanetParameters;
import barsuift.simLife.j2d.ParametersDependent;


public class PlanetParametersPanel extends JPanel implements ParametersDependent {

    private static final long serialVersionUID = 4471355030694189610L;

    private final PlanetParameters parameters;

    private final JSlider latitudeSlider;

    private final JSlider eclipticObliquitySlider;

    public PlanetParametersPanel(PlanetParameters parameters) {
        super();
        this.parameters = parameters;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        Border blacklineBorder = BorderFactory.createLineBorder(Color.black);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(blacklineBorder, "Planet");
        setBorder(titledBorder);

        latitudeSlider = createLatitudeSlider(parameters);
        eclipticObliquitySlider = createEclipticObliquitySlider(parameters);

        add(createLabel("Latitude (degrees)"));
        add(latitudeSlider);

        add(Box.createRigidArea(new Dimension(0, 20)));

        add(createLabel("Planet Ecliptic Obliquity (degrees)"));
        add(eclipticObliquitySlider);
    }

    private JSlider createLatitudeSlider(PlanetParameters parameters) {
        JSlider latitudeSlider = new JSlider(JSlider.HORIZONTAL, PlanetParameters.LATITUDE_MIN,
                PlanetParameters.LATITUDE_MAX, Math.round(parameters.getLatitude()));
        latitudeSlider.setPaintTicks(true);
        latitudeSlider.setMajorTickSpacing(10);
        latitudeSlider.setPaintLabels(true);
        return latitudeSlider;
    }

    private JSlider createEclipticObliquitySlider(PlanetParameters parameters) {
        JSlider eclipticObliquitySlider = new JSlider(JSlider.HORIZONTAL, PlanetParameters.ECLIPTIC_OBLIQUITY_MIN,
                PlanetParameters.ECLIPTIC_OBLIQUITY_MAX, Math.round(parameters.getEclipticObliquity()));
        eclipticObliquitySlider.setPaintTicks(true);
        eclipticObliquitySlider.setMajorTickSpacing(10);
        eclipticObliquitySlider.setPaintLabels(true);
        return eclipticObliquitySlider;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    @Override
    public void readFromParameters() {
        latitudeSlider.setValue(Math.round((float) MathHelper.toRadian(parameters.getLatitude())));
        eclipticObliquitySlider.setValue(Math.round(parameters.getEclipticObliquity()));
    }

    @Override
    public void writeIntoParameters() {
        parameters.setLatitude((float) MathHelper.toRadian(latitudeSlider.getValue()));
        parameters.setEclipticObliquity(eclipticObliquitySlider.getValue());
    }

}
