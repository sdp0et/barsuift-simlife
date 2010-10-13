package barsuift.simLife.j3d.environment;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;

@XmlRootElement
public class Environment3DState implements State {

    public Environment3DState() {
    }

    @Override
    public int hashCode() {
        int result = 1;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Environment3DState []";
    }

}
