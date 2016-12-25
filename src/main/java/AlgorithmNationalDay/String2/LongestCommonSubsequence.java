package AlgorithmNationalDay.String2;

/**
 * Created by Administrator on 2016/10/19.
 * 名称：最长公共子序列(LCSCoreWKZ)
 * 备注：区别最长公共子串(Longest Common Substring),这个要求连续
 * 参考：
 */
public class LongestCommonSubsequence {
    public static void main(String[] args) {
        LCSWKZ("abcbdab", "bdcaba");
        LCSWKZ("13455", "245576");
        LCSWKZ("acdfg", "adfc");

        LCS("abcbdab", "bdcaba");
        LCS("13455", "245576");
        LCS("acdfg", "adfc");
        //testTime();
    }

    private static void testTime() {
        long timeBegin = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            //LCSWKZ("abcbdab", "bdcaba");//18ms
            LCS("abcbdab", "bdcaba");//5ms
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println(timeEnd - timeBegin + "ms");
    }

    /**
     * @Author 王坤造
     * @Date 2016/10/20 13:47
     * 名称：最长公共子序列(非递归版本)
     * 备注：Imgs/最长公共子序列.jpg
     * //LCSWKZ(Xm,Yn)={LCSWKZ(X[m-1],Y[n-1])+xm 当xm=yn;  max{LCSWKZ(X[m-1],Yn),LCSWKZ(Xm,Y[n-1])}}
     */
    public static void LCS(String str1, String str2) {
        int size1;
        int size2;
        if (str1 != null && str2 != null && (size1 = str1.length()) > 0 && (size2 = str2.length()) > 0) {
            int[][] chess = new int[size1 + 1][size2 + 1];
            int i, j;
            for (i = 0; i < size1; i++) {
                for (j = 0; j < size2; j++) {
                    //相同字符,则从左上方的值+1
                    if (str1.charAt(i) == str2.charAt(j)) {
                        chess[i + 1][j + 1] = chess[i][j] + 1;
                    } else {//不相同字符,则从上方或者左方获取最大值
                        chess[i + 1][j + 1] = Math.max(chess[i + 1][j], chess[i][j + 1]);
                    }
                }
            }
            System.out.println(chess[size1][size2]);




            i = size1 - 1;
            j = size2 - 1;
            StringBuilder sb = new StringBuilder();
            while (i >= 0 && j >= 0) {
                if (str1.charAt(i) == str2.charAt(j)) {
                    sb.append(str1.charAt(i));
                    i--;
                    j--;
                } else {
                    if (chess[i + 1][j] > chess[i][j + 1]) {
                        j--;
                    } else {
                        i--;
                    }
                }
            }
            System.out.println(sb.length()+":"+sb.reverse().toString());
        }
    }

    /**
     * @Author 王坤造
     * @Date 2016/10/20 13:47
     * 名称：最长公共子序列(递归版本)
     * 备注：
     */
    public static void LCSWKZ(String str1, String str2) {
//        str1 = UtilsJava.MyString.reverse(str1);
//        str2 = UtilsJava.MyString.reverse(str2);
        int length = LCSCoreWKZ(str1, str2);
        System.out.println(length);
    }

    /**
     * @Author 王坤造
     * @Date 2016/10/20 10:28
     * 名称：最长公共子序列(递归版本)
     * 备注：LCSWKZ(Xm,Yn)={LCSWKZ(X[m-1],Y[n-1])+xm 当xm=yn;  max{LCSWKZ(X[m-1],Yn),LCSWKZ(Xm,Y[n-1])}}
     */
    private static int LCSCoreWKZ(String str1, String str2) {
        int childSize = 0;
        int length1;
        int length2;
        if (str1 != null && str2 != null && (length1 = str1.length()) > 0 && (length2 = str2.length()) > 0) {
            int minLength = Math.min(length1, length2);
            String str1Substring = str1.substring(1, length1);
            String str2Substring = str2.substring(1, length2);
            if (str1.charAt(0) == str2.charAt(0)) {
                if (minLength > 1) {
                    childSize += LCSCoreWKZ(str1Substring, str2Substring) + 1;
                } else {
                    childSize += 1;
                }
            } else {
                int childSize1 = LCSCoreWKZ(str1Substring, str2);
                int childSize2 = LCSCoreWKZ(str1, str2Substring);
                childSize += Math.max(childSize1, childSize2);
            }
        }
        return childSize;
    }
}
