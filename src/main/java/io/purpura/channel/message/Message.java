package io.purpura.channel.message;

public interface Message<T> {

    enum MESSAGE {
        ERROR,
         RESULT
    }
    T get() throws InterruptedException;
    void set(T result);
}
