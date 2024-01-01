package io.purpura.channel.subscriber;

import io.purpura.channel.message.Message;

public interface Subscriber<T> {
    void inform();
    void push(Message<T> message);
}
