package barsuift.simLife;


import javax.swing.UIManager;

public class Launcher {

    public void start() {
        new Application();
    }

    /**
     * Switch to the platform specific look & feel (Windows, or Linux, depending on which platform is used to run the
     * application)
     * 
     * @throws Exception
     */
    public void switchToSystemLookAndFeel() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Application has been launched");
        Launcher launcher = new Launcher();
        launcher.switchToSystemLookAndFeel();
        launcher.start();
    }

}
