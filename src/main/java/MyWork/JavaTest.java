package MyWork;

import MyCommons.UtilsJava;

import java.util.LinkedList;

/**
 * Created by Administrator on 2016/10/8.
 */
public class JavaTest {
    public static void main(String[] args) {
        System.out.println(9 >> 2);
        LinkedList linkedList = new LinkedList();
        linkedList.add(null);
        linkedList.add(3);
        linkedList.add(null);
        UtilsJava.MyPrint.print(linkedList);
    }
}
