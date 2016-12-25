package Threads;

/**
 * Created by Administrator on 2016/10/9.
 */
class MyThread extends Thread {
    public MyThread(String name) {
        super(name);
    }

    static int num = 0;

    public void run() {
        String name = Thread.currentThread().getName();
        for (; num < 50; num++) {
            System.out.println(name + num);
        }
    }
}

public class ThreadDemo1 {
    public static void main(String[] args) {
        // 开启线程第1种方式
        new MyThread("wahaha").start();
        String name = Thread.currentThread().getName();
        for (int i = 0; i < 50; i++) {
            System.out.println(name + i);
        }


//		System.out.println(Thread.currentThread().getName());//main
//		System.out.println(new Thread().getName());//Thread-0

    }
}
