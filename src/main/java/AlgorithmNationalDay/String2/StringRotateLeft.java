package AlgorithmNationalDay.String2;

/**
 * Created by Administrator on 2016/10/19.
 * 名称：字符串循环左移
 * 备注：【(X'Y')'=YX   =>  (bafedc)'=cdefab】时间复杂度O(N),空间复杂度O(1)
 * 参考：
 */
public class StringRotateLeft {
    public static void main(String[] args) {
        //"abcdef"=>"cdefab"    循环左移2位
        //testTime();
        rotateLeft("abcdef", 2);
        rotateRight("abcdef", 4);
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
     * @Author 王坤造
     * @Date 2016/10/19 15:41
     * 名称：字符串循环左移
     * 备注：【(X'Y')'=YX   =>  (bafedc)'=cdefab】时间复杂度O(N),空间复杂度O(1)
     */
    static void rotateLeft(String s, int n) {
        int length = s.length() - 1;
        s = reverseString(s, 0, n - 1);//得到X'
        s = reverseString(s, n, length);//得到Y'
        s = reverseString(s, 0, length);//得到(X'Y')'
    }

    /**
    * @Author 王坤造
    * @Date 2016/10/19 16:46
    * 名称：字符串循环右移
    * 备注：本质上就是循环左移,转化为==>   左移长度=总长度-右移长度
    */
    static void rotateRight(String s, int n) {
        int length = s.length() - 1;
        n = length - n + 1;
        s = reverseString(s, 0, n - 1);//得到X'
        s = reverseString(s, n, length);//得到Y'
        s = reverseString(s, 0, length);//得到(X'Y')'
    }

    private static String reverseString(String s, int from, int to) {
        char[] arr = s.toCharArray();
        while (from < to) {
            char c = arr[from];
            arr[from++] = arr[to];
            arr[to--] = c;
        }
        s = new String(arr);
        System.out.println(s);
        return s;
    }





    private static void rotateLeft2(String s, int n) {
        int length = s.length() - 1;
        s = reverseString2(s, 0, n - 1);//得到X'
        s = reverseString2(s, n, length);//得到Y'
        s = reverseString2(s, 0, length);//得到(X'Y')'
    }

    private static String reverseString2(String s, int from, int to) {
        StringBuilder sb = new StringBuilder(s);
        while (from < to) {
            char c = sb.charAt(from);
            sb.replace(from, ++from, sb.charAt(to) + "");
            sb.replace(to, to + 1, c + "");
            to--;
        }
        s = sb.toString();
        //System.out.println(s);
        return s;
    }


}
