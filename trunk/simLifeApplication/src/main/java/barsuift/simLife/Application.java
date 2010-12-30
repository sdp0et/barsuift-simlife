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

import javax.swing.JFileChooser;

import barsuift.simLife.j2d.CreationParametersWindow;
import barsuift.simLife.j2d.MainWindow;
import barsuift.simLife.j3d.terrain.MidPointHeightMapParameters;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;
import barsuift.simLife.universe.BasicUniverseContextFactory;
import barsuift.simLife.universe.OpenException;
import barsuift.simLife.universe.SaveException;
import barsuift.simLife.universe.UniverseContext;
import barsuift.simLife.universe.UniverseContextIO;

public class Application implements Publisher {

    private File currentSaveFile;

    private UniverseContext currentUniverseContext;

    private MainWindow window;

    private final Publisher publisher = new BasicPublisher(this);

    public Application() {
        this.window = new MainWindow();
        this.window.createMenuBar(this);
        this.window.setVisible(true);
    }

    public void createEmptyRandomUniverse() {
        BasicUniverseContextFactory factory = new BasicUniverseContextFactory();
        this.currentUniverseContext = factory.createEmptyRandom();
        this.currentSaveFile = null;
        this.window.changeUniverse(currentUniverseContext);
        setChanged();
        notifySubscribers(ApplicationUpdateCode.NEW_RANDOM_EMPTY);
    }

    // TODO 001. implement a parameter window in addition to this method (+ the 2 actions)
    public void createPopulatedRandomUniverse() {
        BasicUniverseContextFactory factory = new BasicUniverseContextFactory();
        this.currentUniverseContext = factory.createPopulatedRandom();
        this.currentSaveFile = null;
        this.window.changeUniverse(currentUniverseContext);
        setChanged();
        notifySubscribers(ApplicationUpdateCode.NEW_RANDOM_POPULATED);
    }

    public void createEmptyRandomUniverseWithParameters() {
        CreationParametersWindow parametersWindow = new CreationParametersWindow();
        int sizeValue = parametersWindow.getSizeValue();
        boolean closedByOK = parametersWindow.isClosedByOK();
        System.out.println("closedByOK=" + closedByOK);
        System.out.println("Size = " + sizeValue);


        if (closedByOK) {
            BasicUniverseContextFactory factory = new BasicUniverseContextFactory();
            // FIXME fake code to remove (to create from window)
            MidPointHeightMapParameters parameters = new MidPointHeightMapParameters(64, 0.5f, 0.5f, 25);
            this.currentUniverseContext = factory.createEmptyRandomWithParameters(parameters);
            this.currentSaveFile = null;
            this.window.changeUniverse(currentUniverseContext);
            setChanged();
            notifySubscribers(ApplicationUpdateCode.NEW_RANDOM_EMPTY);
        }
    }

    public void createPopulatedRandomUniverseWithParameters() {
        BasicUniverseContextFactory factory = new BasicUniverseContextFactory();
        // FIXME fake code to remove (to create from window)
        MidPointHeightMapParameters parameters = new MidPointHeightMapParameters(64, 0.5f, 0.5f, 25);
        this.currentUniverseContext = factory.createPopulatedRandomWithParameters(parameters);
        this.currentSaveFile = null;
        this.window.changeUniverse(currentUniverseContext);
        setChanged();
        notifySubscribers(ApplicationUpdateCode.NEW_RANDOM_POPULATED);
    }


    public void openUniverse(File saveFile) throws OpenException {
        UniverseContextIO envIO = new UniverseContextIO(saveFile);
        this.currentUniverseContext = envIO.read();
        this.window.changeUniverse(currentUniverseContext);
        this.currentSaveFile = saveFile;
        setChanged();
        notifySubscribers(ApplicationUpdateCode.OPEN);
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
        notifySubscribers(ApplicationUpdateCode.SAVE);
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
        notifySubscribers(ApplicationUpdateCode.SAVE_AS);
    }

    public UniverseContext getUniverseContext() {
        return currentUniverseContext;
    }

    public MainWindow getMainWindow() {
        return window;
    }

    public void addSubscriber(Subscriber subscriber) {
        publisher.addSubscriber(subscriber);
    }

    public void deleteSubscriber(Subscriber subscriber) {
        publisher.deleteSubscriber(subscriber);
    }

    public void notifySubscribers() {
        publisher.notifySubscribers();
    }

    public void notifySubscribers(Object arg) {
        publisher.notifySubscribers(arg);
    }

    public void deleteSubscribers() {
        publisher.deleteSubscribers();
    }

    public boolean hasChanged() {
        return publisher.hasChanged();
    }

    public int countSubscribers() {
        return publisher.countSubscribers();
    }

    public void setChanged() {
        publisher.setChanged();
    }

    public void clearChanged() {
        publisher.clearChanged();
    }

}
