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
