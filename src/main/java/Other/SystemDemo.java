package Other;

/**
 * Created by Administrator on 2016/10/9.
 */
public class SystemDemo {
    public static void main(String[] args) {
        try{
            System.out.println(11);
            return;//打印
            //退出java虚拟机
            //System.exit(0);//不打印123
            //System.exit(3);//不打印123
        }finally{
            System.out.println(123);

        }
    }
}
