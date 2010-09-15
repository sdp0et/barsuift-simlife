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
import java.util.Observable;

import javax.swing.JFileChooser;

import barsuift.simLife.j2d.MainWindow;
import barsuift.simLife.universe.BasicUniverseContextFactory;
import barsuift.simLife.universe.OpenException;
import barsuift.simLife.universe.SaveException;
import barsuift.simLife.universe.UniverseContext;
import barsuift.simLife.universe.UniverseContextIO;

public class Application extends Observable {

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
        setChanged();
        notifyObservers(ApplicationUpdateCode.NEW_EMPTY);
        return currentUniverseContext;
    }

    public UniverseContext createRandomUniverse() {
        BasicUniverseContextFactory factory = new BasicUniverseContextFactory();
        this.currentUniverseContext = factory.createRandom();
        this.currentSaveFile = null;
        this.window.changeUniverse(currentUniverseContext);
        setChanged();
        notifyObservers(ApplicationUpdateCode.NEW_RANDOM);
        return currentUniverseContext;
    }


    public UniverseContext openUniverse(File saveFile) throws OpenException {
        UniverseContextIO envIO = new UniverseContextIO(saveFile);
        this.currentUniverseContext = envIO.read();
        this.window.changeUniverse(currentUniverseContext);
        this.currentSaveFile = saveFile;
        setChanged();
        notifyObservers(ApplicationUpdateCode.OPEN);
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
        setChanged();
        notifyObservers(ApplicationUpdateCode.SAVE);
    }

    public void saveUniverseAs() throws SaveException {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(window);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            saveUniverseAs(file);
        }
    }

    public void saveUniverseAs(File saveFile) throws SaveException {
        if (currentUniverseContext == null) {
            throw new IllegalStateException("No current universe context to save");
        }
        UniverseContextIO envIO = new UniverseContextIO(saveFile);
        envIO.write(currentUniverseContext);
        this.currentSaveFile = saveFile;
        setChanged();
        notifyObservers(ApplicationUpdateCode.SAVE_AS);
    }

    // TODO remove Fps and axis showing from application to universeContext directly
    public void setFpsShowing(boolean fpsShowing) {
        if (currentUniverseContext != null) {
            currentUniverseContext.setFpsShowing(fpsShowing);
            setChanged();
            notifyObservers(ApplicationUpdateCode.SHOW_FPS);
        }
    }

    public boolean isFpsShowing() {
        if (currentUniverseContext == null) {
            return false;
        } else {
            return currentUniverseContext.isFpsShowing();
        }
    }

    public void setAxisShowing(boolean fpsShowing) {
        if (currentUniverseContext != null) {
            currentUniverseContext.setAxisShowing(fpsShowing);
            setChanged();
            notifyObservers(ApplicationUpdateCode.SHOW_AXIS);
        }
    }

    public boolean isAxisShowing() {
        if (currentUniverseContext == null) {
            return false;
        } else {
            return currentUniverseContext.isAxisShowing();
        }
    }

    public UniverseContext getUniverseContext() {
        return currentUniverseContext;
    }

}
