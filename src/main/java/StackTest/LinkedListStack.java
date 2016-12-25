package StackTest;

/**
 * Created by Administrator on 2016/10/8.
 * 栈单链表实现：
 * 没有长度限制，并且出栈和入栈速度都很快
 * 数据项入栈和出栈的时间复杂度都为常数O(1)
 */
public class LinkedListStack {
    public static void main(String[] args) throws Exception{
        LinkedListStack lls = new LinkedListStack();
        lls.push(1);
        lls.push(2);
        lls.push(3);
        lls.display();
        System.out.println(lls.pop());
        lls.display();
    }

    private  LinkedList ll=new LinkedList();

    public void push(Object obj){
        ll.insertFirst(obj);
    }

    public Object pop() throws Exception{
        return ll.deleteFirst();
    }

    public void display(){
        ll.display();
    }
}

class LinkedList {
    private class Data{
        private Object obj;
        private Data next = null;

        Data(Object obj){
            this.obj = obj;
        }
    }

    private Data first = null;

    public void insertFirst(Object obj){
        Data data = new Data(obj);
        data.next = first;
        first = data;
    }

    public Object deleteFirst() throws Exception{
        if(first == null)
            throw new Exception("empty!");
        Data temp = first;
        first = first.next;
        return temp.obj;
    }

    public void display(){
        if(first == null)
            System.out.println("empty");
        System.out.print("top -> bottom : | ");
        Data cur = first;
        while(cur != null){
            System.out.print(cur.obj.toString() + " | ");
            cur = cur.next;
        }
        System.out.print("\n");
    }
}
