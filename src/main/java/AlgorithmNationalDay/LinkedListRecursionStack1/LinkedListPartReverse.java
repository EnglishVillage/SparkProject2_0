package AlgorithmNationalDay.LinkedListRecursionStack1;

import MyCommons.UtilsJava;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Administrator on 2016/10/10.
 * 链表部分翻转
 */
public class LinkedListPartReverse {
    static Random r = new Random();

    public static void main(String[] args) {
        LinkedList<Integer> pHead = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            pHead.add(r.nextInt(10));
        }
        UtilsJava.MyPrint.print(pHead);
        reverse(pHead, 0, pHead.size()-1);
        UtilsJava.MyPrint.print(pHead);
    }

    /**
     * 反转
     * 必须满足条件:0<=begin<=end<pHead.size()
     */
    private static void reverse(LinkedList<Integer> pHead, int begin, int end) {
        if (pHead != null && begin >= 0 && end >= begin && end < pHead.size()) {
            Integer value;
            //获取begin节点之后数据,并插入到begin节点,就完成反转
            for (int i = begin + 1; i < end + 1; i++) {
                value = pHead.remove(i);
                pHead.add(begin, value);
            }
        }
    }
}
