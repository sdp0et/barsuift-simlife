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
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import barsuift.simLife.j2d.DateDisplay;
import barsuift.simLife.j2d.action.SpeedAction;
import barsuift.simLife.j2d.button.OneStepButton;
import barsuift.simLife.j2d.button.StartButton;
import barsuift.simLife.j2d.button.StopButton;
import barsuift.simLife.j2d.menu.Mnemonics;
import barsuift.simLife.process.MainSynchronizer;
import barsuift.simLife.process.Speed;
import barsuift.simLife.universe.Universe;
import barsuift.simLife.universe.UniverseContext;

public class TimeControllerPanel extends JPanel {

    private static final long serialVersionUID = 5530349468986336456L;

    private final DateDisplay dateDisplay;

    /**
     * Panel with box layout ordered along the PAGE axis
     * 
     * @param synchronizer the synchronizer
     */
    public TimeControllerPanel(UniverseContext universeContext) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setMaximumSize(new Dimension(220, 100));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        Universe universe = universeContext.getUniverse();
        dateDisplay = new DateDisplay(universe.getDate());
        dateDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(dateDisplay);

        MainSynchronizer synchronizer = universeContext.getSynchronizer();
        JPanel speedPanel = createSpeedPanel(synchronizer);
        add(speedPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(new OneStepButton(synchronizer));
        buttonPanel.add(new StartButton(synchronizer));
        buttonPanel.add(new StopButton(synchronizer));
        add(buttonPanel);

        Border blacklineBorder = BorderFactory.createLineBorder(Color.black);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(blacklineBorder, "Time Control");
        setBorder(titledBorder);
    }

    private JPanel createSpeedPanel(MainSynchronizer synchronizer) {
        JPanel speedPanel = new JPanel();
        speedPanel.setLayout(new BoxLayout(speedPanel, BoxLayout.LINE_AXIS));
        SpeedAction actionNormalSpeed = new SpeedAction(synchronizer, "Normal", "Normal speed", Mnemonics.SPEED_NORMAL,
                Speed.NORMAL.name());
        JRadioButton normalSpeed = new JRadioButton(actionNormalSpeed);

        SpeedAction actionFastSpeed = new SpeedAction(synchronizer, "Fast", "Fast speed (about 5 times faster))",
                Mnemonics.SPEED_FAST, Speed.FAST.name());
        JRadioButton fastSpeed = new JRadioButton(actionFastSpeed);

        SpeedAction actionVeryFastSpeed = new SpeedAction(synchronizer, "Very fast",
                "Very fast speed (about 20 times faster)", Mnemonics.SPEED_VERY_FAST, Speed.VERY_FAST.name());
        JRadioButton veryFastSpeed = new JRadioButton(actionVeryFastSpeed);

        Speed speed = synchronizer.getSpeed();
        normalSpeed.setSelected(speed == Speed.NORMAL);
        fastSpeed.setSelected(speed == Speed.FAST);
        veryFastSpeed.setSelected(speed == Speed.VERY_FAST);

        ButtonGroup speedSwitch = new ButtonGroup();
        speedSwitch.add(normalSpeed);
        speedSwitch.add(fastSpeed);
        speedSwitch.add(veryFastSpeed);

        speedPanel.add(new JLabel("Speed"));
        speedPanel.add(normalSpeed);
        speedPanel.add(fastSpeed);
        speedPanel.add(veryFastSpeed);
        return speedPanel;
    }

}
