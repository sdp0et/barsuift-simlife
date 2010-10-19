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

import barsuift.simLife.j2d.CalendarDisplay;
import barsuift.simLife.j2d.action.SpeedAction;
import barsuift.simLife.j2d.button.OneStepButton;
import barsuift.simLife.j2d.button.StartButton;
import barsuift.simLife.j2d.button.StopButton;
import barsuift.simLife.j2d.menu.Accelerators;
import barsuift.simLife.j2d.menu.Mnemonics;
import barsuift.simLife.time.TimeController;

public class TimeControllerPanel extends JPanel {

    private static final long serialVersionUID = 5530349468986336456L;

    private final TimeController controller;

    private final CalendarDisplay calendarDisplay;

    /**
     * Panel with box layout ordered along the PAGE axis
     * 
     * @param timeController the universe time controller
     */
    public TimeControllerPanel(TimeController timeController) {
        super();
        this.controller = timeController;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setMaximumSize(new Dimension(220, 100));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        calendarDisplay = new CalendarDisplay(controller.getCalendar());
        calendarDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(calendarDisplay);

        JPanel speedPanel = createSpeedPanel(timeController);
        add(speedPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(new OneStepButton(controller));
        buttonPanel.add(new StartButton(controller));
        buttonPanel.add(new StopButton(controller));
        add(buttonPanel);

        Border blacklineBorder = BorderFactory.createLineBorder(Color.black);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(blacklineBorder, "Time Control");
        setBorder(titledBorder);
    }

    // FIXME display bug : set on 1 at init time even if speed = 10
    private JPanel createSpeedPanel(TimeController timeController) {
        JPanel speedPanel = new JPanel();
        speedPanel.setLayout(new BoxLayout(speedPanel, BoxLayout.LINE_AXIS));
        SpeedAction action1 = new SpeedAction(timeController, "1", "1 cycle / sec", Mnemonics.SPEED_1,
                Accelerators.SPEED_1, "1");
        JRadioButton speed1 = new JRadioButton(action1);
        speed1.setSelected(true);

        SpeedAction action10 = new SpeedAction(timeController, "10", "10 cycles / sec", Mnemonics.SPEED_10,
                Accelerators.SPEED_10, "10");
        JRadioButton speed10 = new JRadioButton(action10);

        ButtonGroup speedSwitch = new ButtonGroup();
        speedSwitch.add(speed1);
        speedSwitch.add(speed10);

        speedPanel.add(new JLabel("Speed"));
        speedPanel.add(speed1);
        speedPanel.add(speed10);
        return speedPanel;
    }

}
