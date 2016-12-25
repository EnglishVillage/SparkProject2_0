package AlgorithmNationalDay.LinkedListRecursionStack1;

import MyCommons.UtilsJava;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Administrator on 2016/10/10.
 * 链表相乘(需要以链表相加为基础)
 */
public class LinkedListMultiply {
    static Random r = new Random();

    public static void main(String[] args) {
        //链表存储6位数字,依次个十百千万...
        LinkedList<Integer> pHead1 = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            pHead1.add(r.nextInt(10));
        }
        //链表存储9位数字,依次个十百千万...
        LinkedList<Integer> pHead2 = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            pHead2.add(r.nextInt(10));
        }
        UtilsJava.MyPrint.print(pHead1);
        UtilsJava.MyPrint.print(pHead2);

        //将2个链表进行相乘
        LinkedList<Integer> pSum = multiply(pHead1, pHead2);
        UtilsJava.MyPrint.print(pSum);
    }

    private static LinkedList<Integer> multiply(LinkedList<Integer> pHead1, LinkedList<Integer> pHead2) {
        ArrayList<LinkedList<Integer>> list = new ArrayList<LinkedList<Integer>>();
        Integer[] pHead2Arr = pHead2.toArray(new Integer[]{});
        /**
         *   12
         *  *13
         * -----
         *
         * */
        int temp;//分别存储3和1
        int value;//分别存储12*3和12*1的个位数
        int carry = 0;//存储进位数
        for (int i = 0; i < pHead1.size(); i++) {
            temp = pHead1.get(i);
            LinkedList<Integer> pProduct = new LinkedList<>();
            addZero(pProduct, i);
            for (int j = 0; j < pHead2Arr.length; j++) {
                value = temp * pHead2Arr[j] + carry;
                carry = value / 10;
                value = value % 10;
                pProduct.add(value);
            }
            if (carry > 0) {
                pProduct.add(carry);
                carry=0;
            }
            list.add(pProduct);
        }
        return LinkedListAdd.addMore(list);
    }

    /**
     * @Author 王坤造
     * @Date 2016/10/17 16:50
     * 名称：根据i值,给链表添加几个0
     * 备注：
     */
    private static void addZero(LinkedList<Integer> pProduct, int num) {
        if (num > 0) {
            for (int i = 0; i < num; i++) {
                pProduct.add(0);
            }
        }
    }
}