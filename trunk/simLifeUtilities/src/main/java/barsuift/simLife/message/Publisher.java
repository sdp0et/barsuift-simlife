package barsuift.simLife.message;

import java.util.Observable;


/**
 * This interface is the equivalent of the {@link Observable} class.
 * <p>
 * This class represents an publisher object, or "data" in the model-view paradigm. It can be implemented to represent
 * an object that the application wants to subscribe to.
 * </p>
 * <p>
 * A publisher object can have one or more subscribers. A subscriber may be any object that implements interface
 * {@link Subscriber}. After a publisher instance changes, an application calling the <code>Publisher</code>'s
 * <code>notifySubscribers</code> method causes all of its subscribers to be notified of the change by a call to their
 * <code>update</code> method.
 * </p>
 * <p>
 * The order in which notifications will be delivered is unspecified. Implementing classes can use the registration
 * order, use no guaranteed order, deliver notifications on separate threads, or any other way they choose.
 * </p>
 * <p>
 * Note that this notification mechanism has nothing to do with threads and is completely separate from the
 * <tt>wait</tt> and <tt>notify</tt> mechanism of class <tt>Object</tt>.
 * </p>
 * <p>
 * When a publisher object is newly created, its set of subscribers is empty. Two subscribers are considered the same if
 * and only if the <tt>equals</tt> method returns true for them.
 * 
 */
public interface Publisher {

    /**
     * Adds an subscriber to the set of subscribers for this object, provided that it is not the same as some subscriber
     * already in the set. The order in which notifications will be delivered to multiple subscribers is not specified.
     * See the class comment.
     * 
     * @param subscriber a subscriber to be added.
     * @throws NullPointerException if the subscriber is null.
     */
    public void addSubscriber(Subscriber subscriber);

    /**
     * Deletes a subscriber from the set of subscribers of this object. Passing <code>null</code> to this method will
     * have no effect.
     * 
     * @param subscriber the subscriber to be deleted.
     */
    public void deleteSubscriber(Subscriber subscriber);

    /**
     * If this object has changed, as indicated by the <code>hasChanged</code> method, then notify all of its
     * subscribers and then call the <code>clearChanged</code> method to indicate that this object has no longer
     * changed.
     * <p>
     * Each subscriber has its {@link Subscriber#update(Publisher, Object) update} method called with two arguments:
     * this publisher object and <code>null</code>. In other words, this method is equivalent to: <blockquote><code>
     * notifySubscribers(null)</code> </blockquote>
     * </p>
     * 
     * @see Publisher#clearChanged()
     * @see Publisher#hasChanged()
     * @see Subscriber#update(java.util.Publisher, java.lang.Object)
     */
    public void notifySubscribers();

    /**
     * If this object has changed, as indicated by the <code>hasChanged</code> method, then notify all of its
     * subscribers and then call the <code>clearChanged</code> method to indicate that this object has no longer
     * changed.
     * <p>
     * Each subscriber has its {@link Subscriber#update(Publisher, Object) update} method called with two arguments:
     * this publisher object and the <code>arg</code> argument.
     * </p>
     * 
     * @param arg any object.
     * @see Publisher#clearChanged()
     * @see Publisher#hasChanged()
     * @see Subscriber#update(java.util.Publisher, java.lang.Object)
     */
    public void notifySubscribers(Object arg);

    /**
     * Clears the subscriber list so that this object no longer has any subscribers.
     */
    public void deleteSubscribers();

    /**
     * Tests if this object has changed.
     * 
     * @return <code>true</code> if and only if the <code>setChanged</code> method has been called more recently than
     *         the <code>clearChanged</code> method on this object; <code>false</code> otherwise.
     * @see Publisher#clearChanged()
     * @see Publisher#setChanged()
     */
    public boolean hasChanged();

    /**
     * Returns the number of subscribers of this <code>Publisher</code> object.
     * 
     * @return the number of subscribers of this object.
     */
    public int countSubscribers();

    /**
     * Marks this <code>Publisher</code> object as having been changed; the <code>hasChanged</code> method will now
     * return <tt>true</tt>.
     */
    public void setChanged();

    /**
     * Indicates that this object has no longer changed, or that it has already notified all of its subscribers of its
     * most recent change, so that the <code>hasChanged</code> method will now return <code>false</code>. This method is
     * called automatically by the <code>notifySubscribers</code> methods.
     * 
     * @see Publisher#notifySubscribers()
     * @see Publisher#notifySubscribers(java.lang.Object)
     */
    public void clearChanged();

}
