package barsuift.simLife.message;

import java.util.Observer;


/**
 * This interface is the equivalent of the {@link Observer} interface.
 * <p>
 * A class can implement the <code>Subscriber</code> interface when it wants to be informed of changes in
 * <code>Publisher</code> objects.
 * </p>
 */
public interface Subscriber {

    /**
     * This method is called whenever the publisher is changed. An application calls a <code>Publisher</code> object's
     * <code>notifySubscribers</code> method to have all the object's subscribers notified of the change.
     * 
     * @param publisher the publisher object.
     * @param arg an argument passed to the <code>notifySubscribers</code> method.
     */
    void update(Publisher publisher, Object arg);

}
