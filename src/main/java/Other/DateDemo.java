package Other;

import java.util.Date;

/**
 * Created by Administrator on 2016/10/9.
 */
public class DateDemo {
    public static void main(String[] args) {
        Date date=new Date();
        Date date2=new Date(1461601955805l);
        System.out.println(date.after(date2));//date比date2时间晚?
        System.out.println(date.getTime());//获取毫秒数
        System.out.println(date.toString());
    }
}
