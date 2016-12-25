package LinkedListTest;

/**
 * Created by Administrator on 2016/10/8.
 * JAVA实现有序链表[升序](简易版,对NULL处理有BUG)
 * 结构:First指针->结点1->结点2->结点3(First指针引用结点1,结点1引用结点2,结点2引用结点3)
 * 参考链接:http://blog.csdn.net/a19881029/article/details/22695289
 *
 * 表的插入和删除平均需要比较N/2次，即O(N)，但是获取最小数据项只需O(1)，因为其始终处于表头，
 * 对频繁操作最小数据项的应用，可以考虑使用有序链表实现，如：优先级队列
 */
public class SortedLinkedList {
    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {
        SortedLinkedList sll = new SortedLinkedList();
        sll.insert(80);
        sll.insert(2);
        sll.insert(100);
        sll.display();
        System.out.println(sll.deleteFirst());
        sll.insert(33);
        sll.display();
        sll.insert(99);
        sll.display();
    }

    /**
     * 链表结点
     */
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
     * 升序插入
     */
    public void insert(Object obj) {
        Data data = new Data(obj);
        Data pre = null;
        Data cur = this.first;
        //判断要插入的数据是否大于比较的数,大于则继续比较下一个
        while (cur != null && Integer.valueOf(data.obj.toString()) > Integer.valueOf(cur.obj.toString())) {
            pre = cur;
            cur = cur.next;
        }
        //如果pre为空,则说明链表为空
        if (pre == null) {
            first = data;
        } else {
            pre.next = data;
        }
        data.next = cur;
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
