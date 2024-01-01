package io.purpura.channel.channel;

import io.purpura.channel.message.Message;
import io.purpura.channel.subscriber.Subscriber;
import org.jboss.weld.exceptions.IllegalStateException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * simple channel with blocking subscription
 * only one message
 * only one subscriber is allowed
 */
public class DefaultChannel<T> implements Channel {
    private final ReentrantLock reentrantLock = new ReentrantLock();
    private final BlockingQueue<Message<T>> blockingQueue = new LinkedBlockingQueue<>(1);

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
        throw new Exception("could not be used notification on default channel");
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
         throw new Exception("could not be used on default channel");
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
