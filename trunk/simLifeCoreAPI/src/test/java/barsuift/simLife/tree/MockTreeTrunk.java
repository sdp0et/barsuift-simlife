package barsuift.simLife.tree;

import barsuift.simLife.j3d.tree.MockTreeTrunk3D;
import barsuift.simLife.j3d.tree.TreeTrunk3D;


public class MockTreeTrunk implements TreeTrunk {

    private Long id = new Long(1);

    private int age = 0;

    private float height = 0;

    private float radius = 0;

    private int spendTimeCalled = 0;

    private TreeTrunkState state = new TreeTrunkState();

    private TreeTrunk3D trunk3D = new MockTreeTrunk3D();

    @Override
    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public TreeTrunkState getState() {
        return state;
    }

    public void setState(TreeTrunkState state) {
        this.state = state;
    }

    @Override
    public TreeTrunk3D getTreeTrunkD() {
        return trunk3D;
    }

    public void setTreeTrunk3D(TreeTrunk3D trunk3D) {
        this.trunk3D = trunk3D;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void spendTime() {
        spendTimeCalled++;
    }

    public int howManyTimesSpendTimeCalled() {
        return spendTimeCalled;
    }

    public void resetSpendTimeCalled() {
        this.spendTimeCalled = 0;
    }

}
