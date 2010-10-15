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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import barsuift.simLife.j2d.CalendarDisplay;
import barsuift.simLife.j2d.action.SpeedAction;
import barsuift.simLife.j2d.menu.Accelerators;
import barsuift.simLife.j2d.menu.Mnemonics;
import barsuift.simLife.time.UniverseTimeController;

public class TimeControllerPanel extends JPanel {

    private static final long serialVersionUID = 5530349468986336456L;

    private final UniverseTimeController controller;

    private final CalendarDisplay calendarDisplay;

    private final JButton oneStepButton;

    private final JButton startButton;

    private final JButton pauseButton;

    private final JPanel buttonPanel;

    /**
     * Panel with box layout ordered along the PAGE axis
     * 
     * @param timeController the universe time controller
     */
    public TimeControllerPanel(UniverseTimeController timeController) {
        super();
        this.controller = timeController;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setMaximumSize(new Dimension(220, 100));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        calendarDisplay = createCalendarDisplay();
        calendarDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(calendarDisplay);

        JPanel speedPanel = createSpeedPanel(timeController);
        add(speedPanel);

        oneStepButton = createOneStepButton();
        startButton = createStartButton();
        pauseButton = createPauseButton();

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(oneStepButton);
        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        add(buttonPanel);

        Border blacklineBorder = BorderFactory.createLineBorder(Color.black);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(blacklineBorder, "Time Control");
        setBorder(titledBorder);
    }

    private JPanel createSpeedPanel(UniverseTimeController timeController) {
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

    private CalendarDisplay createCalendarDisplay() {
        return new CalendarDisplay(controller.getCalendar());
    }

    private JButton createOneStepButton() {
        JButton button = new JButton("ONE STEP");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.oneStep();
            }
        });
        return button;
    }

    private JButton createStartButton() {
        JButton button = new JButton("START");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                oneStepButton.setEnabled(false);
                startButton.setVisible(false);
                controller.start();
                pauseButton.setVisible(true);
            }

        });
        return button;
    }

    private JButton createPauseButton() {
        JButton button = new JButton("PAUSE");
        button.setVisible(false);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pauseButton.setVisible(false);
                controller.pause();
                oneStepButton.setEnabled(true);
                startButton.setVisible(true);
            }

        });
        return button;
    }

}
