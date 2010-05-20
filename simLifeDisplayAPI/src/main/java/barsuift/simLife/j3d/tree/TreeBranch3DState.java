package barsuift.simLife.j3d.tree;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.j3d.Point3dState;

@XmlRootElement
public class TreeBranch3DState {

    private Point3dState translationVector;

    public TreeBranch3DState() {
        super();
        this.translationVector = new Point3dState();
    }

    public TreeBranch3DState(Point3dState translationVector) {
        super();
        this.translationVector = translationVector;
    }

    public TreeBranch3DState(TreeBranch3DState copy) {
        super();
        this.translationVector = new Point3dState(copy.translationVector);
    }

    public Point3dState getTranslationVector() {
        return translationVector;
    }

    public void setTranslationVector(Point3dState translationVector) {
        this.translationVector = translationVector;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((translationVector == null) ? 0 : translationVector.hashCode());
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
        TreeBranch3DState other = (TreeBranch3DState) obj;
        if (translationVector == null) {
            if (other.translationVector != null)
                return false;
        } else
            if (!translationVector.equals(other.translationVector))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "TreeBranch3DState [translationVector=" + translationVector + "]";
    }

}
