package LinkedListTest;

/**
 * Created by Administrator on 2016/10/8.
 * JAVA实现双端链表(简易版,对NULL处理有BUG)
 * 结构:First指针->结点1->结点2->结点3...[Last指针]->最后一个结点
 * (First指针引用结点1,结点1引用结点2,结点2引用结点3,以此类推,最后有Last指针引用最后一个节点)
 * 参考链接:http://blog.csdn.net/a19881029/article/details/22695289
 */
public class FirstLastLinkedList {
    /**
     * 测试
     */
    public static void main(String[] args) throws Exception{
        FirstLastLinkedList flll = new FirstLastLinkedList();
        flll.insertFirst(2);
        flll.insertFirst(1);
        flll.display();
        flll.insertLast(3);
        flll.display();
        flll.deleteFirst();
        flll.display();
        flll.deleteLast();
        flll.display();
    }

    /**
     * 链表结点
     * */
    private class Data {
        private Object obj;
        private Data next = null;

        Data(Object obj) {
            this.obj = obj;
        }
    }

    /**
     * 存储元素数量
     */
    private int size = 0;
    /**
     * 头指针(第一个节点的引用)
     */
    private Data first = null;
    /**
     * 最后一个节点的指针(引用)
     */
    private Data last = null;

    /**
     * 添加节点到First指针后面
     * 在表头插入一个新的链接点，时间复杂度为O(1)
     */
    public void insertFirst(Object obj) {
        Data data = new Data(obj);
        data.next = first;
        first = data;
        size++;

        //这里判断last指针是否为空,为空则指向新添加的节点
        if (last == null) {
            last = data;
        }
    }

    /**
     * 添加节点到Last指针后面
     * 在表尾插入一个新的链接点，时间复杂度O(1)
     */
    public void insertLast(Object obj) {
        Data data = new Data(obj);
        if (first == null) {
            first = data;
        } else {
            last.next = data;
        }
        last = data;
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
        Data remove = first;
        first = first.next;
        size--;

        //这里判断First指针是否为空,为空则Last指针也为空
        if (first == null) {
            last = null;
        }
        return remove.obj;
    }

    /**
     * 删除表尾的链接点，由于只保存了表尾的链接点，而没有保存表尾的前一个链接点(这里就体现出双向链表的优势了)，
     * 所以在删除表尾链接点时需要遍历以找到表尾链接点的前一个链接点，需查找N-1次，也就是O(N)
     * */
    public Object deleteLast() throws Exception {
        if (isEmpty()) {
            throw new Exception("empty!");
        }
        Data remove = last;
        if (first.next == null) {
            first = null;
            last = null;
            size--;
        } else {
            Data temp = first;
            while (temp.next != null) {
                if (temp.next == last) {
                    last = temp;
                    last.next = null;
                    size--;
                    break;
                }
                temp = temp.next;
            }
        }
        return remove.obj;
    }

    /**
     * 打印输出
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("empty");
        }
        System.out.print("Size:" + size + "\tData:" + first.obj);
        Data cur = first.next;
        while (cur != null) {
            System.out.print("->" + cur.obj);
            cur = cur.next;
        }
        System.out.println();
    }

    /**
     * 判断是否为空链表
     */
    public boolean isEmpty() {
        return first == null;
    }
}
