package barsuift.simLife.j2d;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import barsuift.simLife.j2d.panel.LandscapeParametersPanel;
import barsuift.simLife.j2d.panel.WorldParametersPanel;
import barsuift.simLife.universe.AllParameters;


public class CreationParametersWindow extends JDialog {

    private static final long serialVersionUID = 7855432806983257205L;

    private boolean closedByOK;

    private LandscapeParametersPanel landscapePanel;

    private WorldParametersPanel worldPanel;

    public CreationParametersWindow() {
        super((JFrame) null, "Creation parameters", true);
        int width = 500;
        int height = 400;
        setBounds(100, 100, width, height);
        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);


        // Handle window closing correctly.
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                closedByOK = false;
            }
        });

        // TODO 000. make a class out of this JPanel
        JPanel parametersPanel = new JPanel();
        parametersPanel.setLayout(new BoxLayout(parametersPanel, BoxLayout.PAGE_AXIS));
        contentPane.add(parametersPanel, BorderLayout.CENTER);


        worldPanel = new WorldParametersPanel();
        parametersPanel.add(worldPanel);

        parametersPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        landscapePanel = new LandscapeParametersPanel();
        parametersPanel.add(landscapePanel);

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



    public AllParameters getParameters() {
        return new AllParameters(worldPanel.getCommonParameters(), landscapePanel.getLandscapeParameters());
    }

    public boolean isClosedByOK() {
        return closedByOK;
    }

}
