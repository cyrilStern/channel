package io.purpura.channel.channel;

import io.purpura.channel.message.Message;
import io.purpura.channel.subscriber.Subscriber;

public interface Channel {
    boolean send(Message<?> message) throws InterruptedException;

    void notification(Message<?> message) throws Exception;

    void clear();

    boolean subscribe(Subscriber<?> subscriber) throws Exception;
}
