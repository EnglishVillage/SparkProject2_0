package StackTest;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/10/8.
 * 栈数组实现二
 * 参考链接:http://blog.csdn.net/a19881029/article/details/22579759
 * 优点：无长度限制，
 * 缺点：入栈慢
 * 由于该栈是由数组实现的，数组的长度是固定的，当栈空间不足时，
 * 必须将原数组数据复制到一个更长的数组中，考虑到入栈时或许需要进行数组复制，
 * 平均需要复制N/2个数据项，故入栈的时间复杂度为O(N)，出栈的时间复杂度依然为O(1)
 */
public class UnboundedArrayStack {

    public static void main(String[] args) throws Exception{
        UnboundedArrayStack us = new UnboundedArrayStack(2);
        us.push(1);
        us.push(2);
        us.dispaly();
        System.out.println(us.pop());
        us.dispaly();
        us.push(99);
        us.dispaly();
        us.push(99);
        us.dispaly();
    }

    private int top = -1;
    private Object[] objs;

    public UnboundedArrayStack() throws Exception {
        this(10);
    }

    public UnboundedArrayStack(int capacity) throws Exception {
        if (capacity < 0) {
            throw new Exception("Illegal capacity:" + capacity);
        }
        objs = new Object[capacity];
    }

    public void push(Object obj){
        if(top == objs.length - 1){
            this.enlarge();
        }
        objs[++top] = obj;
    }

    private void enlarge() {
        int num = objs.length / 3;
        if(num==0){
            num=1;
        }
        objs=Arrays.copyOf(objs,objs.length+num);
    }

    public Object pop() throws Exception{
        if(top == -1)
            throw new Exception("ArrayStack is empty!");
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
