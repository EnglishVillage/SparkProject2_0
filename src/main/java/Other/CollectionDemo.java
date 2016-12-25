package Other;

import MyCommons.UtilsJava;

import java.util.*;
import java.util.Map.Entry;

/**
 * Created by Administrator on 2016/10/9.
 */
public class CollectionDemo {
    public static void main(String[] args) {
        HashMap<String, String> hs = new HashMap<>();//默认大小16
        ArrayList<String> list = new ArrayList<>();//默认大小10

        test3();
    }

    static void test3(){
        List<Integer> integers = new ArrayList<Integer>(){{add(1);add(2);add(3);}};
        System.out.println(integers.toArray(new Integer[]{}));



        UtilsJava.MyPrint.print(UtilsJava.CollectionTo.Collection2Array(integers));
        Set<Integer> set = new HashSet<Integer>(){{add(11);add(22);add(33);}};
        UtilsJava.MyPrint.print(UtilsJava.CollectionTo.Collection2Array(set));
    }

    static void test1() {
        Stack s = new Stack();

        // java没有协变
        TestGenericType(new LinkedList<Integer>());

        List<Integer> list = new ArrayList<Integer>();
        List<Integer> list2 = null;
        list.add(0);
        list.add(1);
        list2 = new ArrayList<Integer>(list);// 这个是深度复制
        // list2 = new ArrayList<Integer>(Arrays.asList(new
        // Integer[list.size()]));//Arrays.asList返回的集合长度不可变
        // Collections.copy(list2, list);// 这个是深度复制
        System.out.println(list);
        System.out.println(list2);
        list.set(0, 100);
        list.add(2);
        list.set(1, 200);
        // list2.set(0, 100);
        // list2.add(2);
        // list2.set(1,200);
        System.out.println(list);
        System.out.println(list2);

//         Set<Object> set = new HashSet<Object>();
//         set.add(null);
//         System.out.println(set);
////          TreeSet<T>//添加null会报错
////          set=new TreeSet<Object>();
////          set.add(null);
////          System.out.println(set);

        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        //map.values()只获取一次而已.
        for (Object o : map.values()) {
            System.out.println(o);
        }

//         Set<Integer> keys=map.keySet();
//         for (Integer key : keys) {
//         System.out.println(map.get(key));
//         }
//         map.put(2, "bb");
//         Iterator<Integer> its=keys.iterator();
//         while(its.hasNext()){
//         System.out.println(map.get(its.next()));
//         }

        Set<Entry<Integer, Object>> maps = map.entrySet();
        Iterator<Entry<Integer, Object>> itsMaps = maps.iterator();
        while (itsMaps.hasNext()) {
            Entry<Integer, Object> m = itsMaps.next();
            System.out.println(m.getKey() + "," + m.getValue());
        }


//         Properties p = new Properties();
//         InputStream inStream =Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
//         InputStream inStream =CollectionDemo.class.getClassLoader().getResourceAsStream("db.properties");
//         try {
//         // 从配置文件中加载
//         p.load(inStream);
//         } catch (Exception e) {
//         e.printStackTrace();
//         }
//         System.out.println(p);
    }

    public static void TestGenericType(Collection<? extends Number> c) {

    }

    /**
    * @Author 王坤造
    * @Date 2016/10/17 16:34
    * 名称：并交差集
    * 备注：
    */
    static void test2() {
        Set<Integer> setaa = new HashSet<Integer>() {{{add(1);add(3);add(5);}}};
        System.out.println(setaa.containsAll(new HashSet<Integer>(){{add(1);add(3);add(6);}}));
        System.out.println(setaa.containsAll(new HashSet<Integer>(){{add(1);add(3);add(5);}}));
        System.out.println(setaa.containsAll(new HashSet<Integer>(){{add(1);add(3);}}));



        Set<Integer> result = new HashSet<Integer>();
        Set<Integer> set1 = new HashSet<Integer>() {
            {
                add(1);
                add(3);
                add(5);
            }
        };

        Set<Integer> set2 = new HashSet<Integer>() {
            {
                add(1);
                add(2);
                add(3);
            }
        };


        result.clear();
        result.addAll(set1);
        //boolean b = result.containsAll(set2);
        //boolean b = result.containsAll(set1);
        boolean b = result.containsAll(new HashSet<Integer>(){{add(1);add(3);}});
        System.out.println("containsAll：" + b);


        result.clear();
        result.addAll(set1);
        result.retainAll(set2);
        System.out.println("交集：" + result);


        result.clear();
        result.addAll(set1);
        result.removeAll(set2);
        System.out.println("差集：" + result);

        result.clear();
        result.addAll(set1);
        result.addAll(set2);
        System.out.println("并集：" + result);
    }
}
