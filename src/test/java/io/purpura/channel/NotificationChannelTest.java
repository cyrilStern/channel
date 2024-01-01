package io.purpura.channel;

import io.purpura.channel.channel.NotificationChannel;
import io.purpura.channel.message.Message;
import io.purpura.channel.subscriber.Subscriber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationChannelTest {


        // Message is added to the blocking queue
        @Test
        public void test_message_added_to_blocking_queue() {
            NotificationChannel channel = new NotificationChannel();
            Message<String> message = new Message<String>() {
                @Override
                public String get() throws InterruptedException {
                    return null;
                }

                @Override
                public void set(String result) {

                }
            };
            boolean result = channel.send(message);
            assertTrue(result);
            assertEquals(1, channel.BLOCKING_QUEUE.size());
            assertEquals(message, channel.BLOCKING_QUEUE.peek());
        }

        // Notification is sent to subscribers
        @Test
        public void test_notification_sent_to_subscribers() {
            NotificationChannel channel = new NotificationChannel();
            final boolean[] hasBeenInform = {false};
            Subscriber<String> subscriber = new Subscriber<String>() {
                @Override
                public void inform() {
                    hasBeenInform[0] = true;
                }

                @Override
                public void push(Message<String> message) {

                }
            };
            channel.subscribe(subscriber);
            Message<String> message = new Message<String>() {
                @Override
                public String get() throws InterruptedException {
                    return "hello";
                }

                @Override
                public void set(String result) {

                }
            };
            channel.send(message);
            // Assert that the inform method of the subscriber is called
            Assertions.assertTrue(hasBeenInform[0]);
        }

        // Method returns true
        @Test
        public void test_method_returns_true() {
            NotificationChannel channel = new NotificationChannel();
            Message<String> message = new Message<String>() {
                @Override
                public String get() throws InterruptedException {
                    return null;
                }

                @Override
                public void set(String result) {

                }
            };
            boolean result = channel.send(message);
            assertTrue(result);
        }

        // Blocking queue is full and message cannot be added
        @Test
        public void test_blocking_queue_full() {
            NotificationChannel channel = new NotificationChannel();
            // Fill up the blocking queue
            // Try to add a message to the blocking queue
            // Assert that the method returns false
        }

        // Message is null
        @Test
        public void test_message_is_null() {
            NotificationChannel channel = new NotificationChannel();
            Message<String> message = null;
            boolean result = channel.send(message);
            assertFalse(result);
        }

        // No subscribers are registered
        @Test
        public void test_no_subscribers_registered() {
            NotificationChannel channel = new NotificationChannel();
            Message<String> message = new Message<String>() {
                @Override
                public String get() throws InterruptedException {
                    return null;
                }

                @Override
                public void set(String result) {

                }
            };
            boolean result = channel.send(message);
            assertTrue(result);
        }
}
