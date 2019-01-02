package com.hansque.event;

import net.dv8tion.jda.core.events.Event;

/**
 * Interface for a future event listener. A future event is an event that has not
 * yet occurred yet, but <i>might</i> occur in the future. This interface captures
 * the behaviour what should happen when such event occurs.
 */
public interface FutureEventListener {

    /**
     * Check whether the event matches the description of when this listener should
     * handle the event. Return true if it matches and false if it does not match.
     * <p>
     * This check blocks the event pipeline, therefore the check must be lightweight
     * and preferably not depend on long lasting processes.
     *
     * @param event Generic event
     * @return True if the event should be handled by this listener, false otherwise
     */
    public boolean check(Event event);

    /**
     * This method indicates whether the event should be consumed, such that no other listener
     * can handle this event. This holds for all future event listeners <i>and</i> all normal event
     * listeners. Please be very aware of the risks when this returns true.
     * <p>
     * This method is only executed if {@code check()} returned true.
     *
     * @return True if the event should be consumed, false otherwise
     */
    public boolean consumes();

    /**
     * Indicates if the listener is done listening. A listener might capture more events
     * or it may stop listening without having handled events (e.g. because of a timeout)
     *
     * @return True if the listener is done listening, false otherwise.
     */
    public boolean isDone();

    /**
     * Method to handle the event. This method is only fired if {@code check()} returned false.
     *
     * @param event Event
     */
    public void handle(Event event);

}
