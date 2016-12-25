package Other;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by Administrator on 2016/10/9.
 */
public class ScannerDemo {
    public static void main(String[] args) {

    }

    static void ReadFileString() throws Exception{
        InputStream in = new FileInputStream(new File("C:\\AutoSubmit.java"));
        Scanner s = new Scanner(in);
        while(s.hasNextLine()){
            System.out.println(s.nextLine());
        }
    }

    //分隔字符串(默认是空格)
    static void SplitString() {
        Scanner s = new Scanner("123 asdf sd 45 789 sdf asdfl,sdf.sdfl,asdf    ......asdfkl    las");
        //s.useDelimiter("l");	//设置分隔符
        while (s.hasNext()) {
            System.out.println(s.next());
        }
    }

    // 获取输入的字符串
    static void GetInputString() {
        Scanner s = new Scanner(System.in);
        System.out.println("请输入字符串：");
        while (true) {
            String line = s.nextLine();
            if (line.equals("exit"))
                break;
            System.out.println(">>>" + line);
        }
    }
}
