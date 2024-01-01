package io.purpura.channel.message;

import java.util.Objects;

/**
 * simple wrapper that content object that will be share between thread.
 * @param <T>
 */
public class SimpleMessage<T> extends DefaultMessage<T> {

    private T object;
    @Override
    public T get() throws InterruptedException {
        Objects.requireNonNull(object);
        return object;
    }

    @Override
    public void set(T object) {
        Objects.requireNonNull(object);
        this.object = object;
    }
}
