package io.purpura.channel.channel;

import io.purpura.channel.message.Message;
import io.purpura.channel.subscriber.Subscriber;
import org.jboss.weld.exceptions.IllegalStateException;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * simple channel with blocking subscription
 * only one subscriber is allowed
 */
public class SimpleBufferedChannel<T> implements Channel {
    private final ArrayList<Subscriber<T>> subscribers = new ArrayList<>(1);
    private final ReentrantLock reentrantLock = new ReentrantLock();
    BlockingQueue<Message<T>> blockingQueue;

    public SimpleBufferedChannel(int buffer) {
        this.blockingQueue = new LinkedBlockingQueue<>(buffer);
    }

    @Override
    public boolean send(Message message) throws InterruptedException {
        reentrantLock.lock();
        if(blockingQueue.remainingCapacity() == blockingQueue.size()){
            throw new IllegalStateException("could not add a new message to the channel");
        }
        reentrantLock.unlock();
        return blockingQueue.offer(message);
    }

    @Override
    public void notification(Message<?> message) throws Exception {
        throw new Exception("could not be used notification on simple channel");
    }

    @Override
    public void clear() {
        blockingQueue.clear();
    }

    /**
     * @param subscriber to add to subscriber list
     * @return boolean subscription result
     * @exception IllegalStateException if more than one subscriber
     */
    @Override
    public boolean subscribe(Subscriber subscriber) throws Exception {
        reentrantLock.lock();
        if(subscribers.size() == 1) throw new IllegalStateException("could not be used notification on simple channel");
        reentrantLock.unlock();
        return subscribers.add(subscriber);
    }

    /**
     * Blocking thread method
     * message publisher
     * working as publisher subscriber.
     * ttl is reset every time subscriber get a message
     */
    public void pollMessages(Duration timeout) {
        Instant starTime = Instant.now();
        Instant endDate = starTime.plus(timeout);
        while (!blockingQueue.isEmpty() && Instant.now().isBefore(endDate)){
            Message<T> message = blockingQueue.poll();
            endDate =   endDate.plusMillis(Instant.now().toEpochMilli() - starTime.toEpochMilli());
            subscribers.forEach(subscriber -> subscriber.push(message));
        }
    }

    /**
     * Blocking thread method
     * wait indefinitely until a message is provided or timeout is reach
     */
    public Message<T> getFirstMessage(long timeout, TimeUnit unit) throws InterruptedException {
        return blockingQueue.poll(timeout, unit);
    }

    /**
     * Blocking thread method
     * wait indefinitely until a message is provided
     */
    public Message<T> getFirstMessage() throws InterruptedException {
        return blockingQueue.take();
    }
}
