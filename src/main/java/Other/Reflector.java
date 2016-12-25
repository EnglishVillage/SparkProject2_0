package Other;

import java.lang.reflect.Modifier;

/**
 * Created by Administrator on 2016/10/9.
 */

interface I1 {
}

interface I2 {
}

class Father {
    public class A {
    }

    public class B {
    }
}

final class Child extends Father {
}

public class Reflector implements I1,I2 {
    public static void main(String[] args) {
        Class<Father> clz = (Class<Father>) new Father().getClass();
        Class<?>[] arrClzs = clz.getClasses();//公开的内部类或者接口等
        for (Class<?> item : arrClzs) {
            // class Other.Father$A
            // class Other.Father$B
            System.out.println(item);
        }
        int iii = new Child().getClass().getModifiers();//返回此类或接口以整数编码的 Java 语言修饰符。
        System.out.println(Modifier.toString(iii));


//        //基本类型获取字节码对象
//        System.out.println(int.class);//int
//        System.out.println(Integer.class);//class java.lang.Integer
//        System.out.println(Integer.TYPE);//int
//        System.out.println(Object.class);//class java.lang.Object
//        //获取数组类型的字节码对象
//        Object[] arr = {3, "a"};
//        System.out.println(arr.getClass());//class [Ljava.lang.Object;
//        System.out.println(arr.getClass() == Object[].class);//true

        Class<Reflector> c = Reflector.class;
        Class<?>[] arrs = c.getInterfaces();
        for (Class<?> i : arrs) {
            //interface Other.I1
            //interface Other.I2
            System.out.println(i);
        }
        System.out.println(c.getSuperclass());//class java.lang.Object		class Other.Father


    }
}
