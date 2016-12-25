package LinkedListTest;

/**
 * Created by Administrator on 2016/10/8.
 * JAVA实现单链表(简易版,对NULL处理有BUG)
 * 结构:First指针->结点1->结点2->结点3(First指针引用结点1,结点1引用结点2,结点2引用结点3)
 * 参考链接:http://blog.csdn.net/a19881029/article/details/22695289
 */
public class SingleLinkedList<T> {
    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {
        SingleLinkedList<Integer> sll = new SingleLinkedList<Integer>();
        sll.insertFirst(4);
        sll.display();
        sll.insertFirst(3);
        sll.insertFirst(2);
        sll.insertFirst(3);
        //sll.insertFirst(null);
        sll.insertFirst(1);
        //sll.insertFirst(null);
        sll.display();
        sll.deleteFirst();
        sll.deleteFirst();
        sll.deleteFirst();
        sll.deleteFirst();
        sll.deleteFirst();

        sll.display();
//        sll.remove(2);
//        System.out.println(sll.remove(1));
//        sll.display();
//        System.out.println(sll.find(1));
//        System.out.println(sll.find(4));
    }

    /**
     * 链表结点
     */
    private static class Data<T> {
        private T obj;
        private Data<T> next = null;

        Data(T t) {
            this.obj = t;
        }
    }

    /**
     * 存储元素数量
     */
    private int size = 0;
    /**
     * 头指针(第一个节点的引用)
     */
    private Data<T> first = null;

    /**
     * 添加节点到First指针后面
     * 在表头插入一个新的链接点，时间复杂度为O(1)
     */
    public void insertFirst(T t) {
        Data<T> data = new Data<T>(t);
        data.next = first;
        first = data;
        size++;
    }

    /**
     * 删除First指针引用的节点
     * 删除表头的链接点，时间复杂度为O(1)
     */
    public Object deleteFirst() throws Exception {
        if (isEmpty()) {
            throw new Exception("empty!");
        }
        Data<T> remove = first;
        first = first.next;
        size--;
        return remove.obj;
    }

    /**
     * 删除包含指定关键字的第一个链接点，由于需要遍历查找，平均需要查找N/2次，即O(N)
     */
    public Object remove(T t) throws Exception {
        if (isEmpty()) {
            throw new Exception("LinkedList is empty!");
        }
        Object remove = null;
        if (first.obj.equals(t)) {
            remove = first.obj;
            first = first.next;
            size--;
        } else {
            Data<T> pre = first;
            Data<T> cur = first.next;
            while (cur != null) {
                if (cur.obj.equals(t)) {
                    remove = cur.obj;
                    pre.next = cur.next;
                    size--;
                    break;//如果加注释,则删除查找到的节点,但是上面也要修改
                }
                pre = cur;
                cur = cur.next;
            }
        }
        return remove;
    }

    /**
     * 清空单链表
     */
    public void clear() {
        first = null;
        size = 0;
    }

    /**
     * 查找包含指定关键字的链接点，由于需要遍历查找，平均需要查找N/2次，即O(N)
     */
    public Object find(T t) throws Exception {
        if (isEmpty()) {
            throw new Exception("LinkedList is empty!");
        }
        Data<T> cur = this.first;
        while (cur != null) {
            if (cur.obj.equals(t)) {
                return cur.obj;
            }
            cur = cur.next;
        }
        return null;
    }

    /**
     * 打印输出
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("empty");
        } else {
            System.out.print("Size:" + size + "\tData<T>:" + first.obj);
            Data<T> cur = first.next;
            while (cur != null) {
                System.out.print("->" + cur.obj);
                cur = cur.next;
            }
            System.out.println();
        }
    }

    /**
     * 判断是否为空链表
     */
    public boolean isEmpty() {
        return first == null || size<1;
    }

}
