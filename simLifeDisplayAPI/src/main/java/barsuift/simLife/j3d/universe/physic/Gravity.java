package barsuift.simLife.j3d.universe.physic;

import javax.media.j3d.BranchGroup;



public interface Gravity {

    /**
     * Make the given group fall.
     * 
     * @param groupToFall
     * @return the global transform of the group to fall
     */
    public void fall(BranchGroup groupToFall);

}