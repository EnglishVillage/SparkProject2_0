package JavaBean;
import java.beans.*;
import java.lang.reflect.*;

import org.junit.Test;
/**
 * Created by Administrator on 2016/10/9.
 */
public class OperateDemo {
    // 第1种方式:必须有读和写方法,必须一个有返回值一个没有,setter和getter名称要符合规则,(可以没有私有字段)
    @Test
    public void test1() throws Exception {
        Student stu = new Student();
        Method m;
        // 1.通过构造器来创建PropertyDescriptor对象
        PropertyDescriptor pd = new PropertyDescriptor("xxx", Student.class);
        // 2.通过该对象来获得写方法
        m = pd.getWriteMethod();
        // 3.执行写操作
        m.invoke(stu, "DaWang");
        System.out.println(stu.getName());
        // 4.通过对象获得读方法
        m = pd.getReadMethod();
        // 5.执行读操作
        String name = m.invoke(stu).toString();
        System.out.println(name);
    }

    @Test
    public void test2() throws Exception {
        Student stu = new Student();
        Method m;
        BeanInfo bean = Introspector.getBeanInfo(Student.class);
        PropertyDescriptor[] pds = bean.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            System.out.println(pd.getName());
            System.out.println(pd.getPropertyType());
            if (pd.getName().equals("xxx")) {
                m = pd.getWriteMethod();
                m.invoke(stu, "English");
                System.out.println(stu.getXxx());
                m = pd.getReadMethod();
                String name = m.invoke(stu).toString();
                System.out.println(name);
            }
        }

    }
}

class Student {

    private String name;// 字段
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getXxx() {// 读方法
        return "xx";
    }
    public void setXxx(String a) {// 写方法
        //return "xx";
    }
}
