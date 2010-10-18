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

import barsuift.simLife.InitException;
import barsuift.simLife.JaxbIO;

public class UniverseContextIO {

    private final File file;

    public UniverseContextIO(File file) {
        this.file = file;
    }

    public UniverseContext read() throws OpenException {
        try {
            JaxbIO<UniverseContextState> jaxb = new JaxbIO<UniverseContextState>(getClass().getPackage().getName());
            UniverseContextState universeContextState = jaxb.read(file);
            UniverseContext universeContext = new BasicUniverseContext(universeContextState);
            return universeContext;
        } catch (JAXBException e) {
            throw new OpenException(e);
        } catch (InitException e) {
            throw new OpenException(e);
        }
    }

    public void write(UniverseContext universeContext) throws SaveException {
        try {
            JaxbIO<UniverseContextState> jaxb = new JaxbIO<UniverseContextState>(getClass().getPackage().getName());
            jaxb.write(universeContext.getState(), file);
        } catch (JAXBException e) {
            throw new SaveException(e);
        }
    }

}
