package barsuift.simLife.j3d.tree;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TreeTrunk3DState {

    public TreeTrunk3DState() {
        super();
    }

    public TreeTrunk3DState(TreeTrunk3DState copy) {
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
        return "Tree3DState []";
    }

}
