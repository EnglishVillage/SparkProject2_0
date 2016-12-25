package Threads;

/**
 * Created by Administrator on 2016/10/9.
 */
public class ThreadTest {
    public static void main(String[] args) {
        Model m=new Model();
        for (int i = 0; i < 2; i++) {
            new Thread(new Add(m)).start();
            new Thread(new Subtract(m)).start();
        }
    }
}

//要同步的对象
class Model{
    int value=0;
    public synchronized void add(){
        value++;
        System.out.println(Thread.currentThread().getName()+"add:"+value);
    }

    public synchronized void subtract(){
        value--;
        System.out.println(Thread.currentThread().getName()+"subtract:"+value);
    }
}

class Add implements Runnable{
    Model model;
    public Add(Model m){
        model=m;
    }

    @Override
    public void run() {
        model.add();
    }
}
class Subtract implements Runnable{
    Model model;
    public Subtract(Model m){
        model=m;
    }

    @Override
    public void run() {
        model.subtract();
    }
}
