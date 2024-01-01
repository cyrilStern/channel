package io.purpura.channel.channel;

import io.purpura.channel.message.Message;
import io.purpura.channel.subscriber.Subscriber;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * simple channel with blocking subscription
 * only one subscriber is allowed
 */
public class SimpleChannel implements Channel {
    final BlockingQueue<Message> BLOCKING_QUEUE = new LinkedBlockingQueue<>();
    private final ArrayList<Subscriber> subscribers = new ArrayList<>(1);

    @Override
    public boolean send(Message message) {
        return BLOCKING_QUEUE.add(message);
    }

    @Override
    public void notification(Message<?> message) throws Exception {
        throw new Exception("could not be used notification on simple channel");
    }

    @Override
    public void clear() {
        BLOCKING_QUEUE.clear();
    }

    /**
     * @param subscriber
     * @return
     */
    @Override
    public boolean subscribe(Subscriber subscriber) throws Exception {
        throw new Exception("could not be used notification on simple channel");
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
        while (!BLOCKING_QUEUE.isEmpty() && Instant.now().isBefore(endDate)){
            var message = BLOCKING_QUEUE.poll();
            endDate =   endDate.plusMillis(Instant.now().toEpochMilli() - starTime.toEpochMilli());
             subscribers.forEach(subscriber -> subscriber.push(message));
        }
    }

    /**
     * Blocking thread method
     * wait indefinitely until a message is provided or timeout is reach
     */
    public Message<?> getFirstMessage(long timeout, TimeUnit unit) throws InterruptedException {
        return BLOCKING_QUEUE.poll(timeout, unit);
    }

    /**
     * Blocking thread method
     * wait indefinitely until a message is provided
     */
    public Message<?> getFirstMessage() throws InterruptedException {
        return BLOCKING_QUEUE.take();
    }
}
