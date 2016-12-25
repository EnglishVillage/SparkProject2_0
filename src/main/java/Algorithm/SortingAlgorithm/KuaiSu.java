package Algorithm.SortingAlgorithm;

import MyCommons.UtilsJava;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 * 快速排序
 */
public class KuaiSu {
    public static void main(String[] args) {
        Integer[] a = new Integer[]{2, 0, 7, 10, 3, 4, 5, 9, 6, 8, 11, 1, 12, 6, 3};
        System.out.println("排序之前：");
        UtilsJava.MyPrint.print(a);
        System.out.println("排序之后：");
//        quickSort(a);
//        List<Integer> integers = quickSort2(UtilsJava.ArrayTo.Array2List(a));
//        a=integers.toArray(new Integer[]{});
        UtilsJava.MyPrint.print(a);

//        test();
    }

    static void test() {
        Integer[] a = new Integer[]{2, 0, 7, 10, 3, 4, 5, 9, 6, 8, 11, 1, 12, 23, 54, 22, 5644, 324, 23, 122, 212, 4343, 21543, 322, 56};
        List<Integer> list = UtilsJava.ArrayTo.Array2List(a);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            quickSort(a);//1005
//            quickSort2(list);//5622ms
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin + "ms");
    }

    /**
     * 快速排序
     */
    public static void quickSort(Integer[] arr) {
        if (arr != null && arr.length > 0) {
            quickSortCore(arr, 0, arr.length - 1);
        }
    }

    private static void quickSortCore(Integer[] arr, int left, int right) {
        if (left < right) {//如果不加这个判断递归会无法退出导致堆栈溢出异常
            int dp = partition(arr, left, right);
            quickSortCore(arr, left, dp - 1);
            quickSortCore(arr, dp + 1, right);
        }
    }

    private static int partition(Integer[] arr, int left, int right) {
        //以left为基准
        int pivot = arr[left];
        while (left < right) {
            //找到比基准元素小的元素位置
            while (left < right && arr[right] >= pivot) {
                right--;
            }
            //小于基准元素,则替换掉基准元素所在的位置,并以旧基准元素的右边元素为新基准元素
            if (left < right) {
                arr[left++] = arr[right];//小于,则替换left角标位置
            }
            //找到比基准元素大的元素位置
            while (left < right && arr[left] <= pivot) {
                left++;
            }
            //大于基准元素,则替换掉基准元素所在的位置,并以旧基准元素的右边元素为新基准元素
            if (left < right) {
                arr[right--] = arr[left];
            }
        }
        arr[left] = pivot;
        return left;
    }

    /**
     * 作者: 王坤造
     * 日期: 2016/11/19 12:30
     * 名称：用java方式实现scala的快速排序【这效率太低了吧，是第1种1/5倍】
     * 备注：
     */
    public static List<Integer> quickSort2(List<Integer> list) {
        if (list == null || list.size() < 1) {
            return new ArrayList<Integer>();
        }
        Integer head = list.get(0);
        ArrayList<Integer> left = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) < head) {
                left.add(list.get(i));
            }
        }
        ArrayList<Integer> right = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) > head) {
                right.add(list.get(i));
            }
        }
        List<Integer> newLeft = quickSort2(left);
        List<Integer> newRight = quickSort2(right);
        newLeft.add(head);
        newLeft.addAll(newRight);
        return newLeft;
    }
}