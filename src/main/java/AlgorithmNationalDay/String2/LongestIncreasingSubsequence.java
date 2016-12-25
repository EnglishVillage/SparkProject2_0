package AlgorithmNationalDay.String2;

import MyCommons.UtilsJava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * 作者: 王坤造
 * 日期: 2016/11/9 1:54
 * 名称：最长递增子序列(LIS)
 * 备注：
 */
public class LongestIncreasingSubsequence {
    public static void main(String[] args) {
        Integer[] arr={5,6,7,7,1,2,8,8};
        useLCS(arr);
    }

    /**
     * 作者: 王坤造
     * 日期: 2016/11/9 1:56
     * 名称：使用LCS方式求解LIS
     * 备注：5,6,7,7,1,2,8,8   排序后:  1,2,5,6,7,8   ==>     5,6,7,8
     */
    private static void useLCS(Integer[] arr) {
        StringBuilder sb = new StringBuilder();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if(!list.contains(arr[i])){//过滤掉数组中相同的数字
                list.add(arr[i]);
            }
        }
        Collections.sort(list);//排序
        StringBuilder sbSort = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sbSort.append(list.get(i));
        }
        LongestCommonSubsequence.LCS(sb.toString(),sbSort.toString());
    }
}
