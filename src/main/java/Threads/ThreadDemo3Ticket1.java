package Threads;

/**
 * Created by Administrator on 2016/10/9.
 */
class Ticket1 extends Thread {
    int num = 50;

    public Ticket1(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (; num > 0; num--) {
            System.out.println(Thread.currentThread().getName() + "第" + num + "张票");
        }
    }
}

public class ThreadDemo3Ticket1 {
    public static void main(String[] args) {
        new Ticket1("t1").start();
        new Ticket1("t2").start();
        new Ticket1("t3").start();
    }
}
