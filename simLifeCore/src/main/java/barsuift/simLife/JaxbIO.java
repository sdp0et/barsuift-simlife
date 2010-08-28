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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class JaxbIO<T> {

    private final JAXBContext context;

    public JaxbIO(String packageName) throws JAXBException {
        context = JAXBContext.newInstance(packageName);
    }


    @SuppressWarnings("unchecked")
    public T read(File file) throws JAXBException {
        try {
            FileInputStream fstream = new FileInputStream(file);
            Unmarshaller u = context.createUnmarshaller();
            T p = (T) u.unmarshal(fstream);
            fstream.close();
            return p;
        } catch (IOException e) {
            throw new JAXBException(e);
        }
    }

    public void write(T object, File file) throws JAXBException {
        try {
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            file.getParentFile().mkdirs();
            FileOutputStream outputStream = new FileOutputStream(file);
            m.marshal(object, outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new JAXBException(e);
        }
    }

}
