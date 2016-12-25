package AlgorithmNationalDay.LinkedListRecursionStack1;

import MyCommons.UtilsJava;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/10/10.
 * 链表相加
 * A链表的个位+B链表的个位,十位+十位,百位+百位,有进位的时候也要加上.
 *
 * 推广:大数据计算
 *
 */
public class LinkedListAdd {

    static Random r = new Random();

    public static void main(String[] args) {
        //链表存储6位数字,依次个十百千万...
        LinkedList<Integer> pHead1 = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            pHead1.add(r.nextInt(10));
        }
        //链表存储9位数字,依次个十百千万...
        LinkedList<Integer> pHead2 = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            pHead2.add(r.nextInt(10));
        }
        LinkedList<Integer> pHead3 = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            pHead3.add(r.nextInt(10));
        }


        UtilsJava.MyPrint.print(pHead1);
        UtilsJava.MyPrint.print(pHead2);
        UtilsJava.MyPrint.print(pHead3);
        //将2个链表进行相加
        LinkedList<Integer> pSum = addMore(pHead1, pHead2,pHead3);
        UtilsJava.MyPrint.print(pSum);
    }

    public static LinkedList<Integer> addMore(List<LinkedList<Integer>> pHead) {
        if(pHead==null || pHead.size()<1){
            return new LinkedList<>();
        }else if(pHead.size()==1){
            return pHead.get(0);
        }
        LinkedList<Integer> pSum=pHead.get(0);
        for(int i=1;i<pHead.size();i++){
            pSum=add(pSum,pHead.get(i));
        }
        return pSum;
    }

    public static LinkedList<Integer> addMore(LinkedList<Integer>... pHead) {
        if(pHead==null || pHead.length<1){
            return new LinkedList<>();
        }else if(pHead.length==1){
            return pHead[0];
        }
        LinkedList<Integer> pSum=pHead[0];
        for(int i=1;i<pHead.length;i++){
            pSum=add(pSum,pHead[i]);
        }
        return pSum;
    }

    /**
     * 链表相加,pHead1和pHead2中的元素会被删除
     * */
    static LinkedList<Integer> add(LinkedList<Integer> pHead1, LinkedList<Integer> pHead2) {
        LinkedList<Integer> pSum = new LinkedList<>();
        if (pHead1 == null && pHead2 == null) {
            return pSum;
        }
        //从链表A和链表B中获取值,值会被删除
        Integer p1 = pHead1.poll();//poll是轮询的意思,如果列表为空,则返回null;pop是弹栈的意思,如果列表为空,则抛异常NoSuchElementException
        Integer p2 = pHead2.poll();
        int carry = 0;//进位
        int value;
        while (p1 != null && p2 != null) {
            value = p1 + p2 + carry;
            carry = value / 10;
            value = value % 10;
            pSum.add(value);
            p1 = pHead1.poll();
            p2 = pHead2.poll();
        }
        Integer p;//存储较长链表中取出来的值
        LinkedList<Integer> pLonger;//用来指向较长的链表
        if (p1 == null) {
            p = p2;
            pLonger = pHead2;
        } else {
            p = p1;
            pLonger = pHead1;
        }
        while (p != null) {
            value = p + carry;
            carry = value / 10;
            value = value % 10;
            pSum.add(value);
            p = pLonger.poll();
        }
        if (carry > 0) {
            pSum.add(carry);
        }
        return pSum;
    }
}
