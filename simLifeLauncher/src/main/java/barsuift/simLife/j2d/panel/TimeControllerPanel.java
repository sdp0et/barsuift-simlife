package barsuift.simLife.j2d.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import barsuift.simLife.j2d.TimerDisplay;
import barsuift.simLife.time.UniverseTimeController;

public class TimeControllerPanel extends JPanel {

    private static final long serialVersionUID = 5530349468986336456L;

    private final UniverseTimeController controller;

    private final TimerDisplay timerDisplay;

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
        setMaximumSize(new Dimension(220, 80));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        timerDisplay = createTimerDisplay();
        timerDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(timerDisplay);

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

    private TimerDisplay createTimerDisplay() {
        return new TimerDisplay(controller.getTimeCounter());
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
