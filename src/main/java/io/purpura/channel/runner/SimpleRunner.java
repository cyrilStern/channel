package io.purpura.channel.runner;

import io.purpura.channel.message.DefaultMessage;
import io.purpura.channel.message.Message;

public final class SimpleRunner implements Runner{

    private SimpleRunner() {
    }

    public static <R, T>  R run(Function1<T,R> function, T param) throws InterruptedException {
        Message<R> message = new DefaultMessage<>();
        Thread.ofVirtual().start(() -> {
            var result = function.apply(param);
            message.set(result);
        });
        return message.get();
    }


    public static <R, T, U>  R run(Function2<T,U,R> function, T tParam, U uParam) throws InterruptedException {
        Message<R> message = new DefaultMessage<>();
        Thread.ofVirtual().start(() -> {
            var result = function.apply(tParam,uParam);
            message.set(result);
        });
        return message.get();
    }


    public static <R, T, U, V>  R run(Function3<T,U,V,R> function, T tParam, U uParam, V vParam) throws InterruptedException {
        Message<R> message = new DefaultMessage<>();
        Thread.ofVirtual().start(() -> {
            var result = function.apply(tParam,uParam,vParam);
            message.set(result);
        });
        return message.get();
    }


    public static <R, T, U, V,W>  R run(Function4<T,U,V,W,R> function, T tParam, U uParam, V vParam, W wParam) throws InterruptedException {
        Message<R> message = new DefaultMessage<>();
        Thread.ofVirtual().start(() -> {
            var result = function.apply(tParam,uParam,vParam, wParam);
            message.set(result);
        });
        return message.get();
    }


    public static <R, T, U, V,W,Z>  R run(Function5<T,U,V,W,Z,R> function, T tParam, U uParam, V vParam, W wParam, Z zParam) throws InterruptedException {
        Message<R> message = new DefaultMessage<>();
        Thread.ofVirtual().start(() -> {
            var result = function.apply(tParam,uParam,vParam, wParam,zParam);
            message.set(result);
        });
        return message.get();
    }

}
