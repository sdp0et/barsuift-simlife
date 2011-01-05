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
package barsuift.simLife.j2d;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import barsuift.simLife.j2d.panel.UniverseParametersPanel;
import barsuift.simLife.universe.AllParameters;


public class CreationParametersWindow extends JDialog implements ParametersDependent {

    private static final long serialVersionUID = 7855432806983257205L;

    private final AllParameters parameters;

    private boolean closedByOK;

    private final UniverseParametersPanel parametersPanel;

    public CreationParametersWindow(AllParameters parameters) {
        super((JFrame) null, "Creation parameters", true);
        this.parameters = parameters;

        int width = 500;
        int height = 500;
        setBounds(100, 100, width, height);
        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        // Handle window closing correctly.
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                closedByOK = false;
            }
        });


        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        parametersPanel = new UniverseParametersPanel(parameters.getCommon(), parameters.getLandscape());
        tabbedPane.addTab("Universe", null, parametersPanel, "Universe creation parameters");

        tabbedPane.addTab("Ecosystem", null, new JPanel(), "Ecosystem creation parameters");
        tabbedPane.setEnabledAt(1, false);

        JPanel buttonPanel = createButtonPanel();
        contentPane.add(buttonPanel, BorderLayout.PAGE_END);

        setVisible(true);
    }


    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        JButton buttonOk = createButtonOk();
        buttonPanel.add(buttonOk);

        JButton buttonCancel = createButtonCancel();
        buttonPanel.add(buttonCancel);

        JButton buttonRandom = createButtonRandom();
        buttonPanel.add(buttonRandom);

        JButton buttonDefault = createButtonDefault();
        buttonPanel.add(buttonDefault);

        return buttonPanel;
    }

    private JButton createButtonOk() {
        JButton buttonOK = new JButton("OK");
        buttonOK.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                closedByOK = true;
                writeIntoParameters();
                setVisible(false);
            }
        });
        return buttonOK;
    }

    private JButton createButtonCancel() {
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                closedByOK = false;
                setVisible(false);
            }
        });
        return buttonCancel;
    }

    private JButton createButtonRandom() {
        JButton buttonRandom = new JButton("Random");
        buttonRandom.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                parameters.random();
                readFromParameters();
            }
        });
        return buttonRandom;
    }

    private JButton createButtonDefault() {
        JButton buttonDefault = new JButton("Default");
        buttonDefault.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                parameters.resetToDefaults();
                readFromParameters();
            }
        });
        return buttonDefault;
    }

    public boolean isClosedByOK() {
        return closedByOK;
    }

    @Override
    public void readFromParameters() {
        parametersPanel.readFromParameters();
    }

    @Override
    public void writeIntoParameters() {
        parametersPanel.writeIntoParameters();
    }

}
