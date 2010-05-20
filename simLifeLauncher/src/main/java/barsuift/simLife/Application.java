package barsuift.simLife;

import java.io.File;

import barsuift.simLife.j2d.MainWindow;
import barsuift.simLife.universe.BasicUniverseFactory;
import barsuift.simLife.universe.BasicUniverseIO;
import barsuift.simLife.universe.OpenException;
import barsuift.simLife.universe.SaveException;
import barsuift.simLife.universe.Universe;

public class Application {

    private File currentSaveFile;

    private Universe currentUniverse;

    private MainWindow window;

    public Application() {
        this.window = new MainWindow(this);
        this.window.setVisible(true);
    }

    public Universe createEmptyUniverse() {
        BasicUniverseFactory factory = new BasicUniverseFactory();
        Universe universe = factory.createEmpty();
        this.currentUniverse = universe;
        this.currentSaveFile = null;
        this.window.changeUniverse(currentUniverse);
        return currentUniverse;
    }

    public Universe createRandomUniverse() {
        BasicUniverseFactory factory = new BasicUniverseFactory();
        Universe universe = factory.createRandom();
        this.currentUniverse = universe;
        this.currentSaveFile = null;
        this.window.changeUniverse(currentUniverse);
        return currentUniverse;
    }


    public Universe openUniverse(File saveFile) throws OpenException {
        BasicUniverseIO envIO = new BasicUniverseIO(saveFile);
        Universe universe = envIO.read();
        this.currentUniverse = universe;
        this.window.changeUniverse(currentUniverse);
        this.currentSaveFile = saveFile;
        return currentUniverse;
    }

    public void saveUniverse() throws SaveException {
        if (currentSaveFile == null) {
            throw new IllegalStateException("No current save file");
        }
        if (currentUniverse == null) {
            throw new IllegalStateException("No current universe to save");
        }
        BasicUniverseIO envIO = new BasicUniverseIO(currentSaveFile);
        envIO.write(currentUniverse);
    }

    public void saveUniverseAs(File saveFile) throws SaveException {
        if (currentUniverse == null) {
            throw new IllegalStateException("No current universe to save");
        }
        BasicUniverseIO envIO = new BasicUniverseIO(saveFile);
        envIO.write(currentUniverse);
        this.currentSaveFile = saveFile;
    }

}
