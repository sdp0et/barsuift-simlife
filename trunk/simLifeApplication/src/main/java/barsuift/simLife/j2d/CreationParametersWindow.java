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


public class CreationParametersWindow extends JDialog {

    private static final long serialVersionUID = 7855432806983257205L;

    private boolean closedByOK;

    private final UniverseParametersPanel parametersPanel;

    public CreationParametersWindow(AllParameters parameters) {
        super((JFrame) null, "Creation parameters", true);
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

        JButton buttonOK = new JButton("OK");
        buttonOK.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                closedByOK = true;
                writeIntoParameters();
                setVisible(false);
            }
        });
        buttonPanel.add(buttonOK);

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                closedByOK = false;
                setVisible(false);
            }
        });
        buttonPanel.add(buttonCancel);
        return buttonPanel;
    }

    public boolean isClosedByOK() {
        return closedByOK;
    }

    // TODO see if this class should implement the ParametersPanel interface
    public void writeIntoParameters() {
        parametersPanel.writeIntoParameters();
    }

}
