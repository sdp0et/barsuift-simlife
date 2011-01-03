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

import barsuift.simLife.j2d.panel.TerrainParametersPanel;
import barsuift.simLife.j3d.terrain.MidPointHeightMapParameters;


public class CreationParametersWindow extends JDialog {

    private static final long serialVersionUID = 7855432806983257205L;

    private boolean closedByOK;

    private TerrainParametersPanel terrainPanel;

    public CreationParametersWindow() {
        super((JFrame) null, "Creation parameters", true);
        int width = 512;
        int height = 340;
        setBounds(128, 128, width, height);
        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);


        // Handle window closing correctly.
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                closedByOK = false;
            }
        });


        terrainPanel = new TerrainParametersPanel();
        contentPane.add(terrainPanel, BorderLayout.CENTER);

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



    public MidPointHeightMapParameters getTerrainParameters() {
        return terrainPanel.getTerrainParameters();
    }

    public boolean isClosedByOK() {
        return closedByOK;
    }

}
