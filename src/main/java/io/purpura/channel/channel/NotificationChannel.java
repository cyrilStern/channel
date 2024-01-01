package io.purpura.channel.channel;

import io.purpura.channel.message.Message;
import io.purpura.channel.subscriber.Subscriber;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * multi message type channel
 * subscribers are notify from their type equal to message type add
 */
public class NotificationChannel implements Channel {
    public final BlockingQueue<Message> BLOCKING_QUEUE = new LinkedBlockingQueue<>();
    private final ArrayList<Subscriber> subscribers = new ArrayList();
    @Override
    public  boolean send(Message message) {
        var adding =  BLOCKING_QUEUE.add(message);
        notification(message);
        return adding;
    }

    @Override
    public void notification(Message<?> message) {
        subscribers.stream()
                .filter(subscriber ->
                        Arrays.stream(((ParameterizedType) Arrays.stream(subscriber.getClass().getGenericInterfaces())
                                        .findFirst()
                                        .orElseThrow()).getActualTypeArguments()).findFirst().orElseThrow()
                                .equals(Arrays.stream(((ParameterizedType) Arrays.stream(message.getClass().getGenericInterfaces())
                                        .findFirst()
                                        .orElseThrow()).getActualTypeArguments()).findFirst().orElseThrow()))
                .forEach(Subscriber::inform);
    }

    @Override
    public void clear() {
        BLOCKING_QUEUE.clear();
    }

    @Override
    public boolean subscribe(Subscriber subscriber) {
        return subscribers.add(subscriber);
    }
}
