package barsuift.simLife.j3d.helper;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.Light;
import javax.media.j3d.Locale;
import javax.media.j3d.Node;
import javax.media.j3d.Shape3D;
import javax.media.j3d.VirtualUniverse;

/**
 * This class is an helper to compile given shapes.
 * 
 * It is useful when you want to test that a shape can still be modified once compiled.
 * 
 */
public final class CompilerHelper {

    private CompilerHelper() {
        // private constructor to enforce static access
    }

    /**
     * Add the given shape to a branch group, and compile it.
     * 
     * @param shape a shape to be compiled
     */
    public static void compile(Shape3D shape) {
        BranchGroup bgTemp = new BranchGroup();
        bgTemp.addChild(shape);
        bgTemp.compile();
    }

    /**
     * Add the given light to a branch group, and compile it.
     * 
     * @param light a light to be compiled
     */
    public static void compile(Light light) {
        BranchGroup bgTemp = new BranchGroup();
        bgTemp.addChild(light);
        bgTemp.compile();
    }

    /**
     * Add the given group to a branch group, and compile it.
     * 
     * @param group the group to compile
     */
    public static void compile(Group group) {
        BranchGroup bgTemp = new BranchGroup();
        bgTemp.addChild(group);
        bgTemp.compile();
    }

    public static void addToLocale(Node node) {
        BranchGroup branchGroup = new BranchGroup();
        branchGroup.addChild(node);
        addToLocale(branchGroup);
    }

    public static void addToLocale(BranchGroup branchGroup) {
        Locale locale = new Locale(new VirtualUniverse());
        locale.addBranchGraph(branchGroup);
    }

}
