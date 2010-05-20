/**
 * barsuift-simlife is a life simulator programm
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

import junit.framework.TestCase;


public abstract class JaxbTestCase extends TestCase {

    private static final String fileName = "testJAXB.xml";

    private static final File file = new File(fileName);

    private JAXBContext context;

    protected void setUp() throws Exception {
        super.setUp();
        context = JAXBContext.newInstance(getPackage());
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        context = null;
        file.delete();
    }

    public Object read() throws Exception {
        FileInputStream fstream = new FileInputStream(file);
        Unmarshaller u = context.createUnmarshaller();
        Object p = u.unmarshal(fstream);
        fstream.close();
        return p;
    }

    public void write(Object object) throws Exception {
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        FileOutputStream outputStream = new FileOutputStream(file);
        m.marshal(object, outputStream);
        outputStream.close();
    }

    protected abstract String getPackage();

}
