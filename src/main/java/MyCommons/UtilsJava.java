package MyCommons;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by Administrator on 2016/10/8.
 */
public class UtilsJava {
    public static class MyPrint {

        /**
         * 输出数组
         */
        public static void print(boolean[] arr) {
            System.out.println(Arrays.toString(arr));
        }

        public static void print(byte[] arr) {
            System.out.println(Arrays.toString(arr));
        }

        public static void print(char[] arr) {
            System.out.println(Arrays.toString(arr));
        }

        public static void print(double[] arr) {
            System.out.println(Arrays.toString(arr));
        }

        public static void print(float[] arr) {
            System.out.println(Arrays.toString(arr));
        }

        public static void print(int[] arr) {
            System.out.println(Arrays.toString(arr));
        }

        public static void print(long[] arr) {
            System.out.println(Arrays.toString(arr));
        }

        public static void print(Object[] arr) {
            System.out.println(Arrays.toString(arr));
        }

        public static void print(short[] arr) {
            System.out.println(Arrays.toString(arr));
        }

        /**
         * 输出实现Collection接口的集合
         */
        public static void print(Collection<? extends Object> coll) {
            System.out.println(Arrays.toString(coll.toArray()));
        }
    }

    public static class ArrayTo {

        /**
         * @Author 王坤造
         * @Date 2016/10/18 15:23
         * 名称：Array转化为List
         * 备注：
         * TODO 单纯的asList()返回的list无法add(),remove(),clear()等一些影响集合个数的操作，
         * 因为Arrays$ArrayList和java.util.ArrayList一样，都是继承Abstraclist，
         * 但是Arrays$ArrayList没有override这些方法，而java.util.ArrayList实现了。
         * TODO 建议使用List的子类做返回，而不是Arrays$ArrayList。根据需要吧。如下行注释:
         * List<T> list = new ArrayList<T>(Arrays.asList(arr));
         */
        public static <T extends Object> List<T> Array2List(T[] arr) {
            List<T> list = Arrays.asList(arr);
            return list;
        }

        /**
         * @Author 王坤造
         * @Date 2016/10/18 15:23
         * 名称：Array转化为ArrayList
         * 备注：
         */
        public static <T extends Object> ArrayList<T> Array2ArrayList(T[] arr) {
            ArrayList<T> list = new ArrayList<T>(Arrays.asList(arr));
            return list;
        }

        /**
         * @Author 王坤造
         * @Date 2016/10/18 15:23
         * 名称：Array转化为LinkedList
         * 备注：
         */
        public static <T extends Object> LinkedList<T> Array2LinkedList(T[] arr) {
            LinkedList<T> list = new LinkedList<T>(Arrays.asList(arr));
            return list;
        }

        /**
         * @Author 王坤造
         * @Date 2016/10/18 15:25
         * 名称：Array转化为HashSet
         * 备注：
         */
        public static <T extends Object> HashSet<T> Array2HashSet(T[] arr) {
            HashSet<T> tSet = new HashSet<T>(Arrays.asList(arr));
            // TODO 没有一步到位的方法，根据具体的作用，选择合适的Set的子类来转换。
            return tSet;
        }

        /**
         * @Author 王坤造
         * @Date 2016/10/18 15:25
         * 名称：Array转化为TreeSet
         * 备注：
         */
        public static <T extends Object> TreeSet<T> Array2TreeSet(T[] arr) {
            TreeSet<T> tSet = new TreeSet<T>(Arrays.asList(arr));
            return tSet;
        }
    }

    public static class CollectionTo {
        /**
         * @Author 王坤造
         * @Date 2016/10/18 17:58
         * 名称：Collection转化为Array
         * 备注：
         */
        public static <T extends Object> Object[] Collection2Array(Collection<T> coll) {
            Object[] oArray = coll.toArray();
            return oArray;
        }

        /**
         * @Author 王坤造
         * @Date 2016/10/18 17:58
         * 名称：Collection转化为Array
         * 备注：tTypeArr:T类型的空数组,e.g.new String[]{}
         */
        public static <T extends Object> T[] Collection2Array(Collection<T> coll, T[] tTypeArr) {
            T[] arr = coll.toArray(tTypeArr);
            return arr;
        }

        /**
         * @Author 王坤造
         * @Date 2016/10/18 15:35
         * 名称：Collection转化为ArrayList
         * 备注：
         */
        public static <T extends Object> List<T> Collection2List(Collection<T> coll) {
            ArrayList<T> list = new ArrayList<T>(coll);
            return list;
        }

        /**
         * @Author 王坤造
         * @Date 2016/10/18 15:35
         * 名称：Collection转化为ArrayList
         * 备注：
         */
        public static <T extends Object> ArrayList<T> Collection2ArrayList(Collection<T> coll) {
            ArrayList<T> list = new ArrayList<T>(coll);
            return list;
        }

        /**
         * @Author 王坤造
         * @Date 2016/10/18 15:35
         * 名称：Collection转化为LinkedList
         * 备注：
         */
        public static <T extends Object> LinkedList<T> Collection2LinkedList(Collection<T> coll) {
            LinkedList<T> list = new LinkedList<T>(coll);
            return list;
        }

        /**
         * @Author 王坤造
         * @Date 2016/10/18 15:38
         * 名称：Collection转化为HashSet
         * 备注：
         */
        public static <T extends Object> Set<T> Collection2Set(Collection<T> coll) {
            HashSet<T> tSet = new HashSet<T>(coll);
            return tSet;
        }

        /**
         * @Author 王坤造
         * @Date 2016/10/18 15:38
         * 名称：Collection转化为HashSet
         * 备注：
         */
        public static <T extends Object> HashSet<T> Collection2HashSet(Collection<T> coll) {
            HashSet<T> tSet = new HashSet<T>(coll);
            return tSet;
        }

        /**
         * @Author 王坤造
         * @Date 2016/10/18 15:38
         * 名称：Collection转化为TreeSet
         * 备注：
         */
        public static <T extends Object> TreeSet<T> Collection2TreeSet(Collection<T> coll) {
            TreeSet<T> tSet = new TreeSet<T>(coll);
            return tSet;
        }
    }

    public static class MyString {
        /**
         * @Author 王坤造
         * @Date 2016/10/19 16:33
         * 名称：反转字符串
         * 备注：测试100k次,22ms
         */
        public static String reverseString(String s) {
            if (StringUtils.isNotEmpty(s)) {
                s= reverseString(s, 0, s.length() - 1);
            }
            return s;
        }

        /**
         * @author: 王坤造
         * @date: 2017/4/14 0:57
         * @comment: 反转字符串[from,to]
         * @return:
         * @notes: 测试100k次,22ms
         * reverseString("abcdef",0,1)=>"bacdef"
         */
        public static String reverseString(String s, int from, int to) {
            if (s != null) {
                char[] arr = s.toCharArray();
                while (from < to) {
                    char c = arr[from];
                    arr[from++] = arr[to];
                    arr[to--] = c;
                }
                s = new String(arr);
            }
            return s;
        }

        /**
         * @Author 王坤造
         * @Date 2016/10/19 16:33
         * 名称：反转字符串[from,to]
         * 备注：测试100k次,80ms
         */
        private static String reverseString2(String s, int from, int to) {
            if (s == null) {
                return s;
            } else {
                StringBuilder sb = new StringBuilder(s);
                while (from < to) {
                    char c = sb.charAt(from);
                    sb.replace(from, ++from, sb.charAt(to) + "");
                    sb.replace(to, to + 1, c + "");
                    to--;
                }
                return sb.toString();
            }
        }

        /**
         * @Author 王坤造
         * @Date 2016/10/19 16:33
         * 名称：反转字符串
         * 备注：
         */
        public static String reverse(String str) {
            return str == null ? null : (new StringBuilder(str)).reverse().toString();
        }
    }

    public static class CollectionUtils {
        /**
         * 作者: 王坤造
         * 日期: 2017/1/3 23:20
         * 名称：判断是否为空
         * 备注：为空返回true
         */
        public static <T extends Object> boolean isEmpty(Collection<T> coll) {
            return coll == null || coll.size() < 1;
        }

        /**
         * 作者: 王坤造
         * 日期: 2017/1/3 23:20
         * 名称：判断是否不为空
         * 备注：不为空返回true
         */
        public static <T extends Object> boolean isNotEmpty(Collection<T> coll) {
            return coll != null && coll.size() > 0;
        }
    }

    public static class ArrayUtils {
        /**
         * 作者: 王坤造
         * 日期: 2017/1/3 23:20
         * 名称：判断是否为空
         * 备注：为空返回true
         */
        public static <T extends Object> boolean isEmpty(T[] arr) {
            return arr == null || arr.length < 1;
        }

        /**
         * 作者: 王坤造
         * 日期: 2017/1/3 23:20
         * 名称：判断是否不为空
         * 备注：不为空返回true
         */
        public static <T extends Object> boolean isNotEmpty(T[] arr) {
            return arr != null && arr.length > 0;
        }
    }


    /**
     * 作者: 王坤造
     * 日期: 2016/10/23 15:50
     * 名称：项目resources的目录
     * 备注：
     */
    public static final String resourcesPath = "D:/MyDocument/Study/Java/IdeaProjects/SparkProject2_0/src/main/resources/";
}
