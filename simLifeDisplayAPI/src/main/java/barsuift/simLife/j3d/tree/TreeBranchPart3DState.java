package barsuift.simLife.j3d.tree;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.j3d.Point3dState;

@XmlRootElement
public class TreeBranchPart3DState {

    private Point3dState endPoint;

    public TreeBranchPart3DState() {
        super();
        this.endPoint = new Point3dState();
    }

    public TreeBranchPart3DState(Point3dState endPoint) {
        super();
        this.endPoint = endPoint;
    }

    public TreeBranchPart3DState(TreeBranchPart3DState copy) {
        super();
        this.endPoint = new Point3dState(copy.endPoint);
    }

    public Point3dState getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point3dState endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endPoint == null) ? 0 : endPoint.hashCode());
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
        TreeBranchPart3DState other = (TreeBranchPart3DState) obj;
        if (endPoint == null) {
            if (other.endPoint != null)
                return false;
        } else
            if (!endPoint.equals(other.endPoint))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "TreeBranchPart3DState [endPoint=" + endPoint + "]";
    }

}
