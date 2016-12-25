package StackTest;

/**
 * Created by Administrator on 2016/10/8.
 * 栈数组实现一
 * 参考链接:http://blog.csdn.net/a19881029/article/details/22579759
 * 优点：入栈和出栈速度快，数据项入栈和出栈的时间复杂度都为常数O(1)
 * 缺点：长度有限(有时候这也不能算是个缺点)
 */
public class ArrayStack {

    public static void main(String[] args) throws Exception {
        ArrayStack s = new ArrayStack(2);
        s.push(1);
        s.push(2);
        s.dispaly();
        System.out.println(s.pop());
        s.dispaly();
        s.push(99);
        s.dispaly();
        s.push(9999);
    }

    /**
     * 当前栈存储的角标
     */
    private int top = -1;
    /**
     * 存储数据的数组
     */
    private Object[] objs;

    /**
     * 初始化固定长度的栈
     */
    public ArrayStack(int capacity) throws Exception {
        if (capacity < 0)
            throw new Exception("Illegal capacity:" + capacity);
        objs = new Object[capacity];
    }

    /**
     * 入栈
     */
    public void push(Object obj) throws Exception {
        if (top == objs.length - 1) {
            throw new Exception("ArrayStack is full!");
        }
        //top先自增,再存储
        objs[++top] = obj;
    }

    /**
     * 出栈
     */
    public Object pop() throws Exception {
        if (top == -1) {
            throw new Exception("ArrayStack is empty!");
        }
        //先获取数据,再自减
        return objs[top--];
    }

    /**
     * 打印输出
     */
    public void dispaly() {
        if (top > -1) {
            System.out.print("bottom -> top: | ");
            for (int i = 0; i <= top; i++) {
                System.out.print(objs[i] + " | ");
            }
            System.out.println();
        }
    }
}
