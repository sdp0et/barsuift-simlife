package barsuift.simLife.j2d.menu;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import barsuift.simLife.Application;
import barsuift.simLife.j2d.action.NewEmptyAction;
import barsuift.simLife.j2d.action.NewRandomAction;
import barsuift.simLife.j2d.action.OpenAction;
import barsuift.simLife.j2d.action.SaveAction;
import barsuift.simLife.j2d.action.SaveAsAction;


public class MenuFactory {

    public JMenuBar createMenuBar(Application application) {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        NewEmptyAction newEmptyAction = new NewEmptyAction(application);
        JMenuItem newEmptyItem = new JMenuItem(newEmptyAction);
        fileMenu.add(newEmptyItem);

        NewRandomAction newRandomAction = new NewRandomAction(application);
        JMenuItem newRandomItem = new JMenuItem(newRandomAction);
        fileMenu.add(newRandomItem);

        SaveAction saveAction = new SaveAction(application);
        JMenuItem saveItem = new JMenuItem(saveAction);
        fileMenu.add(saveItem);

        SaveAsAction saveAsAction = new SaveAsAction(application);
        JMenuItem saveAsItem = new JMenuItem(saveAsAction);
        fileMenu.add(saveAsItem);

        OpenAction openAction = new OpenAction(application);
        JMenuItem openItem = new JMenuItem(openAction);
        fileMenu.add(openItem);

        return menuBar;
    }

}
