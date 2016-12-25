package Other;

import org.junit.*;

/**
 * Created by Administrator on 2016/10/9.
 */
public class TestDemo {
    @Test
    public void test1() {
        System.out.println("Junit使用.");
    }

    // 如果只要执行这个,在Outline中点击方法运行即可
    @Test
    public void test2() {
        System.out.println("Junit调试.");
    }

    // 有Test方法,它就会在它之前执行
    @Before
    public void before() {
        System.out.println("Before");
    }

    // 有Test方法,它就会在它之后执行
    @After
    public void after() {
        System.out.println("after");
    }

    // 类开始之前只执行一次(要加static)
    @BeforeClass
    public static void beforeClass() {
        System.out.println("beforeClass");
    }

    // 类结束之后只执行一次(要加static)
    @AfterClass
    public static void afterClass() {
        System.out.println("afterClass");
    }
}
