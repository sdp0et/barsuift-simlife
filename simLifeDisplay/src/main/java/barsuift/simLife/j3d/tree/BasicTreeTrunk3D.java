package barsuift.simLife.j3d.tree;

import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import barsuift.simLife.j3d.AppearanceFactory;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.j3d.util.ColorConstants;
import barsuift.simLife.j3d.util.TransformerHelper;
import barsuift.simLife.tree.TreeTrunk;

import com.sun.j3d.utils.geometry.Cylinder;

public class BasicTreeTrunk3D implements TreeTrunk3D {

    private Cylinder trunkCylinder;

    private final Group group;

    /**
     * 
     * @param radius
     * @param height
     * @param centralPoint correspond to the center of the bottom
     */
    public BasicTreeTrunk3D(Universe3D universe3D, TreeTrunk3DState state, TreeTrunk trunk) {
        if (universe3D == null) {
            throw new IllegalArgumentException("Null universe 3D");
        }
        if (trunk == null) {
            throw new IllegalArgumentException("Null tree trunk");
        }
        if (state == null) {
            throw new IllegalArgumentException("Null tree trunk 3D state");
        }
        Appearance trunkAppearance = new Appearance();
        AppearanceFactory.setColorWithMaterial(trunkAppearance, ColorConstants.brown, new Color3f(0.15f, 0.15f, 0.15f),
                new Color3f(0.05f, 0.05f, 0.05f));
        trunkCylinder = new Cylinder(trunk.getRadius(), trunk.getHeight(), trunkAppearance);
        Vector3d translationVector = new Vector3d();
        translationVector.setY(trunk.getHeight() / 2);
        TransformGroup transformGroup = TransformerHelper.getTranslationTransformGroup(translationVector);
        transformGroup.addChild(trunkCylinder);
        this.group = new Group();
        group.addChild(transformGroup);
    }

    @Override
    public Cylinder getTrunk() {
        return trunkCylinder;
    }

    @Override
    public TreeTrunk3DState getState() {
        return new TreeTrunk3DState();
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return "BasicTreeTrunk3D [getState()=" + getState() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + getState().hashCode();
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
        BasicTreeTrunk3D other = (BasicTreeTrunk3D) obj;
        if (!getState().equals(other.getState()))
            return false;
        return true;
    }

}
