package barsuift.simLife.tree;

import barsuift.simLife.Percent;
import barsuift.simLife.tree.LeafUpdateCode;
import barsuift.simLife.tree.TreeLeaf;


public class MockFallingTreeLeaf extends MockTreeLeaf implements TreeLeaf {

    public MockFallingTreeLeaf() {
        setEfficiency(new Percent(10));
    }

    public void spendTime() {
        super.spendTime();
        setChanged();
        notifyObservers(LeafUpdateCode.fall);
    }

}
