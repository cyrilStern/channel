package io.purpura.channel.message;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * use a countdown latch to block thread until a value is set.
 * Thread that get the message will be waiting until message is set
 * If the set of the message take over 6s, message will release thread and rise error
 *
 * @param <T>
 */
public class TTLMessage<T> extends DefaultMessage<T> {
    private static final CountDownLatch connectedSignal = new CountDownLatch(1);
    private int duration;

    public TTLMessage(int duration, TimeUnit timeUnit) {
        this.duration = duration;
        this.timeUnit = timeUnit;
    }

    private TimeUnit timeUnit;
    private T object;
    @Override
    public T get() throws InterruptedException {
        Objects.requireNonNull(timeUnit);
        connectedSignal.await(duration, timeUnit);
        Objects.requireNonNull(object);
        return object;
    }

    @Override
    public void set(T object) {
        Objects.requireNonNull(object);
        if(connectedSignal.getCount() != 1){
            throw new IllegalStateException("could not set message as it has been already set");
        }
        this.object = object;
        connectedSignal.countDown();
    }
}
