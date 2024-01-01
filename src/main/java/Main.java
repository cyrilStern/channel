import io.purpura.channel.message.DefaultMessage;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Main {
    static final CountDownLatch connectedSignal = new CountDownLatch(1);
    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        Thread.sleep(15000);
        System.out.println("start main");
        System.out.println(Thread.currentThread().getName());
        for (int i = 0; i < 10000; i++) {
            final DefaultMessage<Float> message = new DefaultMessage<>();
            Thread.ofVirtual().start(() -> {
                    float calculation = ((float) 100 /34) + 4;
                    //Thread.sleep(1000);
                    System.out.println("inside Thread after 1000");
                    message.set(calculation);
                    System.out.println(Thread.currentThread().threadId());

            });
            System.out.println("after Thread");
            Thread.ofVirtual().start(() -> {
                try {
                    var result = message.get();
                    System.out.println("result is: " + result);
                    System.out.println(Thread.currentThread().threadId());

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            if(i == 10000-1){
                connectedSignal.countDown();
            }
        }

        System.out.println("after second Thread");
        connectedSignal.await();
        System.out.println("ending ....");
        System.out.println(Thread.currentThread().threadId());

    }

    private static final byte[] EMPTY = new byte[0];
}
