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
