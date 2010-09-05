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
package barsuift.simLife;

import java.io.File;

import barsuift.simLife.j2d.MainWindow;
import barsuift.simLife.j3d.universe.UniverseContext;
import barsuift.simLife.universe.BasicUniverseFactory;
import barsuift.simLife.universe.BasicUniverseIO;
import barsuift.simLife.universe.OpenException;
import barsuift.simLife.universe.SaveException;
import barsuift.simLife.universe.Universe;

public class Application {

    private File currentSaveFile;

    private UniverseContext currentUniverseContext;

    private MainWindow window;

    private boolean showFps;

    public Application() {
        this.window = new MainWindow(this);
        this.window.setVisible(true);
    }

    public UniverseContext createEmptyUniverse() {
        BasicUniverseFactory factory = new BasicUniverseFactory();
        Universe universe = factory.createEmpty();
        this.currentUniverseContext = new UniverseContext(universe);
        currentUniverseContext.showFps(showFps);
        this.currentSaveFile = null;
        this.window.changeUniverse(currentUniverseContext);
        return currentUniverseContext;
    }

    public UniverseContext createRandomUniverse() {
        BasicUniverseFactory factory = new BasicUniverseFactory();
        Universe universe = factory.createRandom();
        this.currentUniverseContext = new UniverseContext(universe);
        currentUniverseContext.showFps(showFps);
        this.currentSaveFile = null;
        this.window.changeUniverse(currentUniverseContext);
        return currentUniverseContext;
    }


    public UniverseContext openUniverse(File saveFile) throws OpenException {
        BasicUniverseIO envIO = new BasicUniverseIO(saveFile);
        Universe universe = envIO.read();
        this.currentUniverseContext = new UniverseContext(universe);
        //TODO 010. load the showFps from file, not from current context
        currentUniverseContext.showFps(showFps);
        this.window.changeUniverse(currentUniverseContext);
        this.currentSaveFile = saveFile;
        return currentUniverseContext;
    }

    public void saveUniverse() throws SaveException {
        if (currentSaveFile == null) {
            throw new IllegalStateException("No current save file");
        }
        if (currentUniverseContext == null) {
            throw new IllegalStateException("No current universe context to save");
        }
        BasicUniverseIO envIO = new BasicUniverseIO(currentSaveFile);
        envIO.write(currentUniverseContext.getUniverse());
    }

    public void saveUniverseAs(File saveFile) throws SaveException {
        if (currentUniverseContext == null) {
            throw new IllegalStateException("No current universe context to save");
        }
        BasicUniverseIO envIO = new BasicUniverseIO(saveFile);
        envIO.write(currentUniverseContext.getUniverse());
        this.currentSaveFile = saveFile;
    }

    public void showFps(boolean show) {
        showFps = show;
        if (currentUniverseContext != null) {
            currentUniverseContext.showFps(show);
        }

    }

}
