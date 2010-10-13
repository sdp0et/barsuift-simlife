package barsuift.simLife.j3d.universe;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Universe3DState {

    public Universe3DState() {
        super();
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
        return "Universe3DState []";
    }

}
