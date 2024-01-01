package io.purpura.channel;

import io.purpura.channel.message.Message;

public class TestMessage implements Message<String> {
    String hello;
    public TestMessage(String hello) {
        this.hello=hello;
    }

    @Override
    public String get() throws InterruptedException {
        return hello;
    }

    @Override
    public void set(String result) {

    }
}
