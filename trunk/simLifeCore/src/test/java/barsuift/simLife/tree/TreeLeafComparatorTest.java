package barsuift.simLife.tree;

import javax.vecmath.Point3d;

import junit.framework.TestCase;
import barsuift.simLife.j3d.tree.MockTreeLeaf3D;


public class TreeLeafComparatorTest extends TestCase {

    private TreeLeafComparator comparator;

    protected void setUp() throws Exception {
        super.setUp();
        comparator = new TreeLeafComparator();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        comparator = null;
    }

    // The implementor must ensure that sgn(compare(x, y)) == -sgn(compare(y, x)) for all x and y. (This implies that
    // compare(x, y) must throw an exception if and only if compare(y, x) throws an exception.)
    //
    // The implementor must also ensure that the relation is transitive: ((compare(x, y)>0) && (compare(y, z)>0))
    // implies compare(x, z)>0.
    //
    // Finally, the implementor must ensure that compare(x, y)==0 implies that sgn(compare(x, z))==sgn(compare(y, z))
    // for all z.
    //
    // It is generally the case, but not strictly required that (compare(x, y)==0) == (x.equals(y)).

    public void testCompare() {
        MockTreeLeaf o1 = new MockTreeLeaf();
        ((MockTreeLeaf3D) o1.getTreeLeaf3D()).setAttachPoint(new Point3d(2, 0, 0));
        MockTreeLeaf o2 = new MockTreeLeaf();
        ((MockTreeLeaf3D) o2.getTreeLeaf3D()).setAttachPoint(new Point3d(3, 0, 0));
        MockTreeLeaf o3 = new MockTreeLeaf();
        ((MockTreeLeaf3D) o3.getTreeLeaf3D()).setAttachPoint(new Point3d(5.0005, 0, 0));
        MockTreeLeaf o4 = new MockTreeLeaf();
        ((MockTreeLeaf3D) o4.getTreeLeaf3D()).setAttachPoint(new Point3d(5.001, 0, 0));

        assertEquals(0, comparator.compare(o1, o1));
        assertEquals(-1000, comparator.compare(o1, o2));
        assertEquals(-3000, comparator.compare(o1, o3));
        assertEquals(-3001, comparator.compare(o1, o4));

        assertEquals(1000, comparator.compare(o2, o1));
        assertEquals(0, comparator.compare(o2, o2));
        assertEquals(-2000, comparator.compare(o2, o3));
        assertEquals(-2001, comparator.compare(o2, o4));

        assertEquals(3000, comparator.compare(o3, o1));
        assertEquals(2000, comparator.compare(o3, o2));
        assertEquals(0, comparator.compare(o3, o3));
        assertEquals(0, comparator.compare(o3, o4));

        assertEquals(3001, comparator.compare(o4, o1));
        assertEquals(2001, comparator.compare(o4, o2));
        assertEquals(0, comparator.compare(o4, o3));
        assertEquals(0, comparator.compare(o4, o4));
    }
}
