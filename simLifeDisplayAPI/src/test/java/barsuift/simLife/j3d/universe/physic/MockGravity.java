package barsuift.simLife.j3d.universe.physic;

import javax.media.j3d.BranchGroup;


public class MockGravity implements Gravity {

    private BranchGroup fallenGroup;

    @Override
    public void fall(BranchGroup groupToFall) {
        this.fallenGroup = groupToFall;
    }

    public BranchGroup getFallenGroup() {
        return fallenGroup;
    }

}
