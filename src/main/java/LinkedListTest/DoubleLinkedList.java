package LinkedListTest;

/**
 * Created by Administrator on 2016/10/9.
 * JAVA实现双向链表(简易版,对NULL处理有BUG)
 */
public class DoubleLinkedList<T> {
    public static void main(String[] args) {
//        int_test();        // 演示向双向链表操作“int数据”。
        string_test();    // 演示向双向链表操作“字符串数据”。
//        object_test();    // 演示向双向链表操作“对象”。
    }

    // 双向链表操作int数据
    private static void int_test() {
        int[] iarr = {10, 20, 30, 40};
        System.out.println("\n----int_test----");
        // 创建双向链表
        DoubleLinkedList<Integer> dlink = new DoubleLinkedList<Integer>();
        dlink.insertFirst(11);
        dlink.insert(0, 20);    // 将 20 插入到第一个位置
        dlink.insertLast(10);    // 将 10 追加到链表末尾
        dlink.insertFirst(30);    // 将 30 插入到第一个位置
        // 双向链表是否为空
        System.out.printf("isEmpty()=%b\n", dlink.isEmpty());
        // 双向链表的大小
        System.out.printf("size()=%d\n", dlink.size);
        // 打印出全部的节点
        for (int i = 0; i < dlink.size; i++) {
            System.out.println("dlink(" + i + ")=" + dlink.get(i));
        }
    }

    private static void string_test() {
        String[] sarr = {"ten", "twenty", "thirty", "forty"};
        System.out.println("\n----string_test----");
        // 创建双向链表
        DoubleLinkedList<String> dlink = new DoubleLinkedList<String>();
        dlink.insert(0, sarr[1]);    // 将 sarr中第2个元素 插入到第一个位置
        dlink.insertLast(sarr[0]);    // 将 sarr中第1个元素 追加到链表末尾
        dlink.insertFirst(sarr[2]);    // 将 sarr中第3个元素 插入到第一个位置
        // 双向链表是否为空
        System.out.printf("isEmpty()=%b\n", dlink.isEmpty());
        // 双向链表的大小
        System.out.printf("size()=%d\n", dlink.size);
        // 打印出全部的节点
        for (int i = 0; i < dlink.size; i++)
            System.out.println("dlink(" + i + ")=" + dlink.get(i));
    }

    private static void object_test() {
        System.out.println("\n----object_test----");
        // 创建双向链表
        DoubleLinkedList<Student> dlink = new DoubleLinkedList<Student>();
        dlink.insert(0, students[1]);    // 将 students中第2个元素 插入到第一个位置
        dlink.insertLast(students[0]);    // 将 students中第1个元素 追加到链表末尾
        dlink.insertFirst(students[2]);    // 将 students中第3个元素 插入到第一个位置
        // 双向链表是否为空
        System.out.printf("isEmpty()=%b\n", dlink.isEmpty());
        // 双向链表的大小
        System.out.printf("size()=%d\n", dlink.size);
        // 打印出全部的节点
        for (int i = 0; i < dlink.size; i++) {
            System.out.println("dlink(" + i + ")=" + dlink.get(i));
        }
    }

    // 内部类
    private static class Student {
        private int id;
        private String name;

        public Student(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "[" + id + ", " + name + "]";
        }
    }

    private static Student[] students = new Student[]{
            new Student(10, "sky"),
            new Student(20, "jody"),
            new Student(30, "vic"),
            new Student(40, "dan"),
    };

    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////


    /**
     * 链表结点
     */
    private class DNode<T> {
        public DNode<T> prev;
        public DNode<T> next;
        public T value;

        public DNode(T value, DNode<T> prev, DNode<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    // 表头
    private DNode<T> mHead;
    // 节点个数
    public int size;

    // 构造函数
    public DoubleLinkedList() {
        // 创建“表头”。注意：表头没有存储数据！
        mHead = new DNode<T>(null, null, null);
        mHead.prev = mHead.next = mHead;
        // 初始化“节点个数”为0
        size = 0;
    }

    // 获取第index位置的节点
    private DNode<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        //正向查找
        if (index <= size / 2) {
            DNode<T> node = mHead.next;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        }

        //反向查找
        DNode<T> rnode = mHead.prev;
        int rindex = size - index - 1;
        for (int j = 0; j < rindex; j++) {
            rnode = rnode.prev;
        }
        return rnode;
    }

    // 获取第index位置的节点的值
    public T get(int index) {
        return getNode(index).value;
    }

    // 获取第1个节点的值
    public T getFirst() {
        return getNode(0).value;
    }

    // 获取最后一个节点的值
    public T getLast() {
        return getNode(size - 1).value;
    }

    // 将节点插入到第index位置之前
    public void insert(int index, T t) {
        DNode<T> inode;
        DNode<T> node;
        if (index == 0) {
            inode = mHead;
        } else {
            inode = getNode(index);
        }
        node = new DNode<>(t, inode, inode.next);
        inode.next.prev = node;
        inode.next = node;
        size++;
    }

    // 将节点插入第一个节点处。
    public void insertFirst(T t) {
        insert(0, t);
    }

    // 将节点追加到链表的末尾
    public void insertLast(T t) {
        DNode<T> node = new DNode<T>(t, mHead.prev, mHead);
        mHead.prev.next = node;
        mHead.prev = node;
        size++;
    }

    // 删除index位置的节点
    public void del(int index) {
        DNode<T> inode = getNode(index);
        inode.prev.next = inode.next;
        inode.next.prev = inode.prev;
        inode = null;
        size--;
    }

    // 删除第一个节点
    public void deleteFirst() {
        del(0);
    }

    // 删除最后一个节点
    public void deleteLast() {
        del(size - 1);
    }

    /**
     * 判断是否为空链表
     */
    public boolean isEmpty() {
        return size == 0;
    }
}
