package com.hansque.event;

import net.dv8tion.jda.core.events.Event;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Implementation of a single use listener.
 */
public class SingleUseListener implements FutureEventListener {

    private final Predicate<Event> check;
    private final Consumer<Event> handle;
    private final boolean consumes;

    public SingleUseListener(Predicate<Event> check, Consumer<Event> handle, boolean consumes) {
        this.check = check;
        this.handle = handle;
        this.consumes = consumes;
    }

    /**
     * Constructor such that consumes is true.
     *
     * @param handle Handle
     */
    public SingleUseListener(Predicate<Event> check, Consumer<Event> handle) {
        this(check, handle, true);
    }

    @Override
    public boolean check(Event event) {
        return check.test(event);
    }

    @Override
    public boolean consumes() {
        return consumes;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public void handle(Event event) {
        handle.accept(event);
    }
}
