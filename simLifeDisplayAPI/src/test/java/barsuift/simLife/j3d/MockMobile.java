package barsuift.simLife.j3d;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;

import barsuift.simLife.message.BasicPublisher;


public class MockMobile extends BasicPublisher implements Mobile {

    private BranchGroup bg;

    private TransformGroup tg;


    public MockMobile() {
        super(null);
        reset();
    }

    public void reset() {
        BranchGroup bg = new BranchGroup();
        TransformGroup tg = new TransformGroup();
        bg.addChild(tg);

    }

    @Override
    public TransformGroup getTransformGroup() {
        return tg;
    }

    public void setTransformGroup(TransformGroup tg) {
        bg.removeChild(this.tg);
        this.tg = tg;
        bg.addChild(tg);
    }

    @Override
    public BranchGroup getBranchGroup() {
        return bg;
    }

    public void setBranchGroup(BranchGroup bg) {
        this.bg.removeChild(tg);
        this.bg = bg;
        bg.addChild(tg);
    }

}
