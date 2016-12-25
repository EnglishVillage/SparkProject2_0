package Other;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/9.
 */
public class CalendarDemo {
    public static void main(String[] args) {
        Calendar c=Calendar.getInstance();
        c.add(Calendar.DATE,-1);//时间相加减
        System.out.println(c.getTime());
        System.out.println(new Date());
        System.out.println();
        System.out.println(c.get(Calendar.YEAR));
        System.out.println(c.get(Calendar.MONTH)+1);
        System.out.println(c.get(Calendar.DATE));
        System.out.println(c.get(Calendar.HOUR_OF_DAY));
        System.out.println(c.get(Calendar.MINUTE));
        System.out.println(c.get(Calendar.SECOND));
    }
}
