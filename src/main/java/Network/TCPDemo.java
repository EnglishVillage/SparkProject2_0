package Network;
import java.io.*;
import java.net.*;
/**
 * Created by Administrator on 2016/10/9.
 */
public class TCPDemo {
    public static void main(String[] args) throws Exception {
        String serverIP = "127.0.0.1";
        //服务器端端口号
        int port = 10000;
        //发送内容
        String data = "Hello";
        try (Socket socket = new Socket(serverIP,port);) {
            //发送数据
            OutputStream os = socket.getOutputStream();
            os.write(data.getBytes());
            //接收数据
            InputStream is = socket.getInputStream();
            byte[] b = new byte[1024];
            int n = is.read(b);
            //输出反馈数据
            System.out.println("服务器反馈：" + new String(b,0,n));
        } catch (Exception e) {}
    }
}
