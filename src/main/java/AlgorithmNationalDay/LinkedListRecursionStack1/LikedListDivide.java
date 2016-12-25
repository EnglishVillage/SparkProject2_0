package AlgorithmNationalDay.LinkedListRecursionStack1;

import MyCommons.UtilsJava;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Administrator on 2016/10/11.
 * 链表划分
 * 将链表划分成2部分,小于X的在前,其余在后,并且保持原链表中值出现顺序
 */
public class LikedListDivide {
    static Random r = new Random();

    public static void main(String[] args) {
        LinkedList<Integer> pHead = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            pHead.add(r.nextInt(100));
        }
        UtilsJava.MyPrint.print(pHead);
        divide(pHead, 50);
        UtilsJava.MyPrint.print(pHead);
    }

    private static void divide(LinkedList<Integer> pHead, int pivotKey) {
        LinkedList<Integer> pLeftHead = new LinkedList<Integer>();
        LinkedList<Integer> pRightHead = new LinkedList<Integer>();
        Integer p = pHead.poll();
        while (p != null) {
            if (p < pivotKey) {
                pLeftHead.add(p);
            } else {
                pRightHead.add(p);
            }
            p=pHead.poll();
        }
        pLeftHead.addAll(pRightHead);
        pHead.addAll(pLeftHead);
    }
}
