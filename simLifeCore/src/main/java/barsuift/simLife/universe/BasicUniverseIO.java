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
package barsuift.simLife.universe;

import java.io.File;

import javax.xml.bind.JAXBException;

import barsuift.simLife.JaxbIO;

public class BasicUniverseIO {

    private final File file;

    public BasicUniverseIO(File file) {
        this.file = file;
    }

    public Universe read() throws OpenException {
        try {
            JaxbIO<UniverseState> jaxb = new JaxbIO<UniverseState>(getClass().getPackage().getName());
            UniverseState universeState = jaxb.read(file);
            Universe universe = new BasicUniverse(universeState);
            return universe;
        } catch (JAXBException e) {
            throw new OpenException(e);
        }
    }

    public void write(Universe universe) throws SaveException {
        try {
            JaxbIO<UniverseState> jaxb = new JaxbIO<UniverseState>(getClass().getPackage().getName());
            jaxb.write(universe.getState(), file);
        } catch (JAXBException e) {
            throw new SaveException(e);
        }
    }

}
