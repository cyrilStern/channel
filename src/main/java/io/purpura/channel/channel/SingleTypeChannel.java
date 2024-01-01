package io.purpura.channel.channel;

import io.purpura.channel.message.Message;
import io.purpura.channel.subscriber.Subscriber;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * multi message type channel
 * subscribers are notify from their type equal to message type add
 */
public class SingleTypeChannel<T> implements Channel {
    final BlockingQueue<Message<T>> blockingQueue = new LinkedBlockingQueue<>();
    private final ArrayList<Subscriber<T>> subscribers = new ArrayList<>(1);
    @Override
    public boolean send(Message message) {
        var adding =  blockingQueue.add(message);
        notification(message);
        return adding;
    }

    @Override
    public void notification(Message<?> message) {
        subscribers.forEach(Subscriber::inform);

    }

    @Override
    public void clear() {
        blockingQueue.clear();
    }

    @Override
    public boolean subscribe(Subscriber subscriber) {
        if(!subscribers.isEmpty()) throw new IllegalStateException();
        return subscribers.add(subscriber);
    }
}
