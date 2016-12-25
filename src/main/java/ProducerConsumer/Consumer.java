package ProducerConsumer;
public class Consumer implements Runnable {
    private QueueBuffer q;
    Consumer(QueueBuffer q) {
        this.q = q;
        new Thread(this, "Consumer").start();
    }
    public void run() {
        while (true) {
            q.get();
        }
    }
}