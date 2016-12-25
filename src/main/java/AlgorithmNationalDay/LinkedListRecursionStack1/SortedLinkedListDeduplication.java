package AlgorithmNationalDay.LinkedListRecursionStack1;

import MyCommons.UtilsJava;

import java.util.LinkedList;

/**
 * Created by Administrator on 2016/10/10.
 * 排序链表中去重
 */
public class SortedLinkedListDeduplication {
    public static void main(String[] args) {
        int[] data = {2, 2, 2, 2, 3, 3, 5, 7, 8, 8, 8, 9, 9, 30};
        LinkedList<Integer> pHead = new LinkedList<>();
        for (int i : data) {
            pHead.add(i);
        }
        UtilsJava.MyPrint.print(pHead);
        deleteDeduplicationNode3(pHead);
        UtilsJava.MyPrint.print(pHead);
    }

    /**
     * 排序链表中去重(重复元素保留一个)
     * 如果2个值相同,删除后面的值
     */
    private static void deleteDeduplicationNode(LinkedList<Integer> pHead) {
        Integer pPre = pHead.peekFirst();
        Integer pCur;
        int currIndex = 1;//相当于指针,
        while (pPre != null) {
            try {
                pCur = pHead.get(currIndex);//获取当前指针的值
            } catch (Exception e) {
                pCur = null;
            }
            //判断当前值与前一个值是否相等,相等则删除该节点;不相等则指针向后移一位并且把当前值赋值给前一个值
            if (pCur != null && pCur == pPre) {
                pHead.remove(currIndex);
            } else {
                currIndex++;
                pPre = pCur;
            }
        }
    }

    /**
     * 排序链表中去重(重复元素保留一个)
     * 如果2个值相同,删除前面的值
     */
    private static void deleteDeduplicationNode2(LinkedList<Integer> pHead) {
        if (pHead == null || pHead.size() < 2) {
            return;
        }
        Integer pPre = pHead.peekFirst();
        Integer pCur = pHead.get(1);
        //去重 角标为 0和1 的值
        do {
            if (pPre == pCur) {
                pHead.removeFirst();
                pPre = pHead.peekFirst();
                pCur = pHead.get(1);
            } else {
                break;
            }
        } while (pPre != null && pCur != null);


        Integer pNext;
        int currIndex = 1;//相当于指针,
        //去重 角标为 1之后 的值
        while (pCur != null) {
            try {
                pNext = pHead.get(currIndex + 1);//获取当前指针的值
            } catch (Exception e) {
                pNext = null;
            }
            //判断当前值与下一个值是否相等,相等则删除该节点并继续循环;不相等则指针向后移一位并且把当前值赋值给前一个值,下一个值赋值给当前值
            while (pNext != null && pCur == pNext) {
                pHead.remove(currIndex);
                pCur = pHead.get(currIndex);
                pNext = pHead.get(currIndex + 1);
            }
            pCur = pNext;
            currIndex++;
        }
    }

    /**
     * 排序链表中去重(重复元素全部删除)
     * 如果2个值相同,删除后面的值
     */
    private static void deleteDeduplicationNode3(LinkedList<Integer> pHead) {
        Integer pPre = pHead.peekFirst();
        Integer pCur;
        int currIndex = 1;//相当于指针,
        boolean bDup = false;
        while (pPre != null) {
            try {
                pCur = pHead.get(currIndex);//获取当前指针的值
            } catch (Exception e) {//已经越过角标了
                pCur = null;
            }
            //判断当前值与前一个值是否相等,相等则删除该节点;不相等则指针向后移一位并且把当前值赋值给前一个值
            if (pCur != null && pCur == pPre) {
                bDup = true;
                pHead.remove(currIndex);
            } else {
                if (bDup) {
                    bDup = false;
                    pHead.remove(currIndex - 1);
                    try {
                        pPre = pHead.get(currIndex - 1);
                    } catch (Exception e) {//已经越过角标了
                        break;
                    }
                } else {
                    currIndex++;
                    pPre = pCur;
                }
            }
        }
    }

    /**
     * 排序链表中去重(重复元素全部删除)
     * 如果2个值相同,删除前面的值
     */
    private static void deleteDeduplicationNode4(LinkedList<Integer> pHead) {
        if (pHead == null || pHead.size() < 2) {
            return;
        }
        boolean bDup = false;
        Integer pPre = pHead.peekFirst();
        Integer pCur = pHead.get(1);
        //去重 角标为 0和1 的值
        do {
            if (pPre == pCur) {
                bDup = true;
                pHead.removeFirst();
                pPre = pHead.peekFirst();
                pCur = pHead.get(1);
            } else {
                //这里进行判断是否有删除值,如果有删除则删除第一个值,并继续判断后面的值是否相等.
                if (bDup) {
                    bDup = false;
                    pHead.removeFirst();

                    pPre = pHead.peekFirst();
                    pCur = pHead.get(1);
                    //这里如果不加判断,则[2,2,3,3]最后只删掉2,没有删掉3.
                    if (pPre != pCur) {
                        break;
                    }
                } else {
                    break;
                }
            }
        } while (pPre != null && pCur != null);

        Integer pNext;
        int currIndex = 1;//相当于指针,
        //去重 角标为 1之后 的值
        while (pCur != null) {
            try {
                pNext = pHead.get(currIndex + 1);//获取(当前指针+1)的值
            } catch (Exception e) {
                pNext = null;
            }
            //判断当前值与下一个值是否相等,相等则删除该节点并继续循环;不相等则指针向后移一位并且把当前值赋值给前一个值,下一个值赋值给当前值
            while (pNext != null && pCur == pNext) {
                bDup = true;
                pHead.remove(currIndex);
                pCur = pHead.get(currIndex);
                pNext = pHead.get(currIndex + 1);
            }
            //判断是否有删除过值,删除过值则继续删掉当前指针的值并获取下一指针的值,没有删除则指针往后移一位并将下个值赋值给当前值
            if (bDup) {
                bDup = false;
                pHead.remove(currIndex);
                pCur = pHead.get(currIndex);
            } else {
                pCur = pNext;
                currIndex++;
            }
        }
    }
}
