package MyTest;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/10/31.
 */
public class Test {
    public static void main(String[] args) {
//        testNull();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(i);
        }
    }

    /**
     * 作者: 王坤造
     * 日期: 2016/11/1 17:59
     * 名称：测试null是否可调用静态方法
     * 备注：https://zhidao.baidu.com/question/415660914.html
     */
    public static void testNull() {
        System.out.println(((Test)null));
        ((Test)null).test();
    }

    public static void test(){
        System.out.println("null static fun");
    }
}
