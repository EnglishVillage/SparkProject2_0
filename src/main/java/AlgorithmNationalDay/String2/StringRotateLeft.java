package AlgorithmNationalDay.String2;

import MyCommons.UtilsJava;

/**
 * @author: 王坤造
 * @date: 2017/4/14 1:28
 * @comment: 字符串循环左移(完美洗牌)
 * @return: 【XY=>(X'Y')'=YX   ==>  abcdef=>(bafedc)'=cdefab】时间复杂度O(N),空间复杂度O(1)
 * @notes:
 * 1.暴力移位法:每次循环左移1位,调用k次即可.时间复杂度O(kN),空间复杂度O(k).
 * 2.三次拷贝:时间复杂度O(N),空间复杂度O(k).
 *  S[0...k]->T[0...k]【从S数组拷贝k个到T数组】
 *  S[k+1...N-1]->S[0...N-k-1]【将S数组后面(N-k)们左移k位】
 *  T[0...k]->S[N-k-1...N-1]【从T数组拷贝k个到S数组】
 */
public class StringRotateLeft {
    public static void main(String[] args) {
        //"abcdef"=>"cdefab"    循环左移2位
        //testTime();
        rotateLeft("abcdef", 2);    //cdefab
        //rotateRight("abcdef", 4);
    }

    static void testTime() {
        long timeBegin = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            rotateLeft("abcdef", 2);//22ms
            //rotateLeft2("abcdef", 2);//80ms
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println(timeEnd - timeBegin + "ms");
    }

    /**
     * @author: 王坤造
     * @date: 2017/4/14 0:37
     * @comment: 字符串循环左移
     * @return:
     * @notes:
     * 【(X'Y')'=YX   =>  (bafedc)'=cdefab】时间复杂度O(N),空间复杂度O(1)
     *
     */
    public static String rotateLeft(String s, int n) {
        int length = s.length() - 1;
        s = UtilsJava.MyString.reverseString(s, 0, n - 1);//得到X'Y=>bacdef
        s = UtilsJava.MyString.reverseString(s, n, length);//得到X'Y'=>bafedc
        s = UtilsJava.MyString.reverseString(s, 0, length);//得到(X'Y')'(等价于YX)=>cdefab
        return s;
    }

    /**
    * @Author 王坤造
    * @Date 2016/10/19 16:46
    * 名称：字符串循环右移
    * 备注：本质上就是循环左移,转化为==>   左移长度=总长度-右移长度
    */
    public static void rotateRight(String s, int n) {
        int length = s.length() - 1;
        n = length - n + 1;
        s = UtilsJava.MyString.reverseString(s, 0, n - 1);//得到X'
        s = UtilsJava.MyString.reverseString(s, n, length);//得到Y'
        s = UtilsJava.MyString.reverseString(s, 0, length);//得到(X'Y')'
    }
}
