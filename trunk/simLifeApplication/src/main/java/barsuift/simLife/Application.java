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
import barsuift.simLife.universe.BasicUniverseContextFactory;
import barsuift.simLife.universe.OpenException;
import barsuift.simLife.universe.SaveException;
import barsuift.simLife.universe.UniverseContext;
import barsuift.simLife.universe.UniverseContextIO;

// TODO005. add a changelog file to trace what is added in a version of the application
public class Application {

    private File currentSaveFile;

    private UniverseContext currentUniverseContext;

    private MainWindow window;

    public Application() {
        this.window = new MainWindow(this);
        this.window.setVisible(true);
    }

    public UniverseContext createEmptyUniverse() {
        BasicUniverseContextFactory factory = new BasicUniverseContextFactory();
        this.currentUniverseContext = factory.createEmpty();
        this.currentSaveFile = null;
        this.window.changeUniverse(currentUniverseContext);
        return currentUniverseContext;
    }

    public UniverseContext createRandomUniverse() {
        BasicUniverseContextFactory factory = new BasicUniverseContextFactory();
        this.currentUniverseContext = factory.createRandom();
        this.currentSaveFile = null;
        this.window.changeUniverse(currentUniverseContext);
        return currentUniverseContext;
    }


    public UniverseContext openUniverse(File saveFile) throws OpenException {
        UniverseContextIO envIO = new UniverseContextIO(saveFile);
        this.currentUniverseContext = envIO.read();
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
        UniverseContextIO envIO = new UniverseContextIO(currentSaveFile);
        envIO.write(currentUniverseContext);
    }

    public void saveUniverseAs(File saveFile) throws SaveException {
        if (currentUniverseContext == null) {
            throw new IllegalStateException("No current universe context to save");
        }
        UniverseContextIO envIO = new UniverseContextIO(saveFile);
        envIO.write(currentUniverseContext);
        this.currentSaveFile = saveFile;
    }

    public void showFps(boolean show) {
        if (currentUniverseContext != null) {
            currentUniverseContext.showFps(show);
        }

    }

}
