package io.purpura.channel;

import io.purpura.channel.message.Message;
import io.purpura.channel.subscriber.Subscriber;

public class TestSubscriber<T> implements Subscriber<T> {
    boolean isInform;
    @Override
    public void inform() {
        isInform=true;
    }

    @Override
    public void push(Message<T> message) {

    }

    public boolean isInformed() {
        return isInform;
    }
}
