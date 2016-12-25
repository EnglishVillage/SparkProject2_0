package ProducerConsumer;

public class Main {
	    public static void main(String[] args) {
	        QueueBuffer q = new QueueBuffer(); 
	        new Producer(q); 
	        new Consumer(q); 
	        System.out.println("Press Control-C to stop."); 
	    }
}
