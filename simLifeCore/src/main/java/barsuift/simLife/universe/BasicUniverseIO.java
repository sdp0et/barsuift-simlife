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
