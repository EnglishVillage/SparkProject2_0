package AlgorithmNationalDay.String2;

/**
 * Created by Administrator on 2016/10/19.
 * 名称：最长公共子序列(LCSCoreWKZ)
 * 备注：区别最长公共子串(Longest Common Substring),这个要求连续
 * 参考：
 */

import org.apache.commons.lang3.StringUtils;

/**
 * @author: 王坤造
 * @date: 2017/4/14 1:29
 * @comment: 最长公共子序列(LCSCoreWKZ)
 * @return:
 * @notes: 区别最长公共子串(Longest Common Substring), 这个要求连续
 * 1.暴力求解:穷举法
 */
public class LongestCommonSubsequence {
    public static void main(String[] args) {
//        LCSWKZ("abcbdab", "bdcaba");
//        LCSWKZ("13455", "245576");
//        LCSWKZ("acdfg", "adfc");
//
//        LCS("abcbdab", "bdcaba");
//        LCS("13455", "245576");
//        LCS("acdfg", "adfc");
        testTime();
    }

    private static void testTime() {
        long timeBegin = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
//            LCSWKZ("abcbdab", "bdcaba");//18ms
            LCS("abcbdab", "bdcaba");//5ms
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println(timeEnd - timeBegin + "ms");
    }

    /**
     * @Author 王坤造
     * @Date 2016/10/20 13:47
     * 名称：最长公共子序列(可获取最长公共子序列字符串)
     * 备注：Imgs/最长公共子序列.jpg
     */
    public static int LCS(String str1, String str2) {
        int size1;
        int size2;
        if (str1 != null && str2 != null && (size1 = str1.length()) > 0 && (size2 = str2.length()) > 0) {
            int[][] chess = new int[size1 + 1][size2 + 1];
            int i, j;
            for (i = 0; i < size1; i++) {
                for (j = 0; j < size2; j++) {
                    //相同字符,则从左上方的值+1
                    if (str1.charAt(i) == str2.charAt(j)) {
                        chess[1][1] = chess[0][0] + 1;
                    } else {//不相同字符,则从上方或者左方获取最大值
                        chess[i + 1][j + 1] = Math.max(chess[i + 1][j], chess[i][j + 1]);
                    }
                }
            }
            return chess[size1][size2];


//            i = size1 - 1;
//            j = size2 - 1;
//            StringBuilder sb = new StringBuilder();
//            while (i >= 0 && j >= 0) {
//                if (str1.charAt(i) == str2.charAt(j)) {
//                    sb.append(str1.charAt(i));
//                    i--;
//                    j--;
//                } else {
//                    if (chess[i + 1][j] > chess[i][j + 1]) {
//                        j--;
//                    } else {
//                        i--;
//                    }
//                }
//            }
//            System.out.println(sb.length() + ":" + sb.reverse().toString());
        }
        return 0;
    }

    /**
     * @author: 王坤造
     * @date: 2017/4/14 2:01
     * @comment: 最长公共子序列(根据动态规划公式, 递归版本)
     * @return:
     * @notes: LCSWKZ(Xm, Yn)={当xm=yn,LCSWKZ(X[m-1],Y[n-1])+xm;否则max{LCSWKZ(X[m-1],Yn),LCSWKZ(Xm,Y[n-1])}}
     */
    public static int LCSWKZ(String str1, String str2) {
        //反转就是按上面公式从尾进行匹配,不反转则从头开始匹配
        str1 = StringUtils.reverse(str1);
        str2 = StringUtils.reverse(str2);
        return LCSCoreWKZ(str1, str2);
    }

    private static int LCSCoreWKZ(String str1, String str2) {
        int lcs = 0;
        if (StringUtils.isNotEmpty(str1) && StringUtils.isNotEmpty(str2)) {
            String str1Substring = str1.substring(1, str1.length());
            String str2Substring = str2.substring(1, str2.length());
            if (str1.charAt(0) == str2.charAt(0)) {
                lcs += LCSCoreWKZ(str1Substring, str2Substring) + 1;
            } else {
                int childSize1 = LCSCoreWKZ(str1Substring, str2);
                int childSize2 = LCSCoreWKZ(str1, str2Substring);
                lcs += Math.max(childSize1, childSize2);
            }
        }
        return lcs;
    }
}
