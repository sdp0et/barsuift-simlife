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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class JaxbTester<E extends State> {

    private final Class<?> clazz;

    private final File file;

    private JAXBContext context;

    public JaxbTester(Class<?> clazz) {
        this.clazz = clazz;
        String fileName = "testJAXB_" + clazz.getName() + ".xml";
        this.file = new File(fileName);
    }

    public void init() throws Exception {
        context = JAXBContext.newInstance(clazz.getPackage().getName());
    }

    public void clean() {
        context = null;
        file.delete();
    }

    @SuppressWarnings("unchecked")
    public E read() throws Exception {
        FileInputStream fstream = new FileInputStream(file);
        Unmarshaller u = context.createUnmarshaller();
        Object p = u.unmarshal(fstream);
        fstream.close();
        return (E) p;
    }

    public void write(E object) throws Exception {
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        FileOutputStream outputStream = new FileOutputStream(file);
        m.marshal(object, outputStream);
        outputStream.close();
    }

}
