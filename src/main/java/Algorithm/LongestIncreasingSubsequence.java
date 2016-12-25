package Algorithm;

import MyCommons.UtilsJava;

import java.util.TreeSet;

/**
 * Created by Administrator on 2016/9/30.
 * 最长递增子序列 o(N*N)/O(NlogN)算法
 * http://www.cnblogs.com/NoraLi/p/5483913.html
 */
public class LongestIncreasingSubsequence {
    public static void main(String[] args) {
        int[] arr = {1, -1, 2, -3, 4, -5, 6, -7};
        System.out.println(lengthOfLIS(arr));
    }

    /**
     * 复杂度为o(nlgn)
     */
    public static int lengthOfLIS(int[] nums) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int i : nums) {
            //返回set中 大于或等于i 的最小元素,如果不存在这样的元素返回null
            Integer ceil = set.ceiling(i);
            if (null != ceil) {
                set.remove(ceil);
            }
            set.add(i);
        }
        UtilsJava.MyPrint.print(set);
        return set.size();
    }

    /**
     * 复杂度为o(N*N)
     */
    public static int lengthOfLIS1(int[] nums) {
        int len = nums.length;
        if (len == 0) return 0;
        int[] max = new int[len];
        max[0] = 1;
        for (int i = 1; i < len; i++) {
            int maxSub = 0, maxPos = 0;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    if (max[maxPos] < max[j])
                        maxPos = j;
                    maxSub = max[maxPos];
                }
            }
            max[i] = maxSub + 1;
        }
        int maxLen = 1;
        for (int i = 1; i < len; i++) {
            if (max[i] > maxLen) {
                maxLen = max[i];
            }
        }
        return maxLen;
    }
}
