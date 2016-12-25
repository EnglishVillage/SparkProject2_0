package Threads;

/**
 * Created by Administrator on 2016/10/9.
 */
class Ticket2 implements Runnable {
    int num = 50;

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        for (; num > 0; num--) {
            System.out.println(name + "第" + num + "张票");
        }
    }
}

public class ThreadDemo3Ticket2 {
    public static void main(String[] args) {
        Runnable myRun = new Ticket2();
        new Thread(myRun, "t1").start();
        new Thread(myRun, "t2").start();
        new Thread(myRun, "t3").start();
    }
}
