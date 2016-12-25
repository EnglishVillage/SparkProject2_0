package Network;
import java.net.*;
/**
 * Created by Administrator on 2016/10/9.
 */
public class InetAddressDemo {
    public static void main(String[] args) throws Exception {
        // 使用域名创建对象
        InetAddress inet1 = InetAddress.getByName("www.163.com");
        System.out.println(inet1); // www.163.com/218.6.111.42
        // 使用IP创建对象
        InetAddress inet2 = InetAddress.getByName("127.0.0.1");
        System.out.println(inet2); // /127.0.0.1
        // 获得本机地址对象
        InetAddress inet3 = InetAddress.getLocalHost();
        System.out.println(inet3); // PC-20150519BTXH/192.168.1.7

        InetAddress inet4 = inet1;
        // 获得对象中存储的域名
        String host = inet4.getHostName();
        System.out.println("域名：" + host);//域名：www.163.com
        // 获得对象中存储的IP
        String ip = inet4.getHostAddress();//IP:218.6.111.42
        System.out.println("IP:" + ip);
        //获取字节数组形式的IP地址,以点分隔的四部分
        byte[] bytes =inet4.getAddress();
        for (byte b : bytes) {
            System.out.println(b);
        }
        // IP 地址的完全限定域名
        //System.out.println("IP 地址的完全限定域名:" + inet4.getCanonicalHostName());//IP 地址的完全限定域名:218.6.111.42
    }
}
