package barsuift.simLife.j3d;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;

import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;


public class MockMobile implements Mobile {

    private BranchGroup bg;

    private TransformGroup tg;

    private final Publisher publisher = new BasicPublisher(this);

    public MockMobile() {
        reset();
    }

    public void reset() {
        bg = new BranchGroup();
        tg = new TransformGroup();
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


    public void addSubscriber(Subscriber subscriber) {
        publisher.addSubscriber(subscriber);
    }

    public void deleteSubscriber(Subscriber subscriber) {
        publisher.deleteSubscriber(subscriber);
    }

    public void notifySubscribers() {
        publisher.notifySubscribers();
    }

    public void notifySubscribers(Object arg) {
        publisher.notifySubscribers(arg);
    }

    public void deleteSubscribers() {
        publisher.deleteSubscribers();
    }

    public boolean hasChanged() {
        return publisher.hasChanged();
    }

    public int countSubscribers() {
        return publisher.countSubscribers();
    }

    public void setChanged() {
        publisher.setChanged();
    }

    public void clearChanged() {
        publisher.clearChanged();
    }

}
