package Network;
import java.net.*;
/**
 * Created by Administrator on 2016/10/9.
 */
public class URLDemo {
    public static void main(String[] args) throws Exception {
        URL baidu = new URL("http://www.baidu.com");
        URL url = new URL(baidu, "/index.html?username=tom#test");
        String Protocol=url.getProtocol();//协议	http
        String Host=url.getHost();//URL主机名	www.baidu.com
        int Port=url.getPort();//URL端口号	-1
        String Path=url.getPath();
        String File=url.getFile();
        String Ref=url.getRef();
        String Query=url.getQuery();
        System.out.println(Protocol+"@"+Host+"@"+Port+"@"+Path+"@"+File+"@"+Ref+"@"+Query);
    }
}
