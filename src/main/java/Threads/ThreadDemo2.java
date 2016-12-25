package Threads;

/**
 * Created by Administrator on 2016/10/9.
 */
class MyThread2 implements Runnable {
    static int num = 0;

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        for (; num < 50; num++) {
            System.out.println(name + num);
        }
    }
}

public class ThreadDemo2 {
    public static void main(String[] args) {
        // 开启线程第2种方式
        new Thread(new MyThread2(), "haha").start();
        String name = Thread.currentThread().getName();
        for (int i = 0; i < 50; i++) {
            System.out.println(name + i);
        }
        //使用匿名方式开启线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                for (int num = 0; num < 50; num++) {
                    System.out.println(name + num);
                }
            }
        },"Anonymous").start();
    }
}
