package String;

import org.apache.commons.lang3.StringUtils;

public class StringDemo {

    public static void main(String[] args) {
        String s = new String();
        s = new String("aaa");
        s = "abc";
        System.out.println(s.charAt((2)));
        //System.out.println((int) '��' + ";" + (int) 'a');// 36820;97
        //System.out.println(s.compareToIgnoreCase("��bc"));// ���ص�1������ͬ�ַ��unicode�Ĳ� -36723
        System.out.println(s.concat("oo"));
        System.out.println(s.contains("ABC"));
        System.out.println(s.endsWith("a"));
        System.out.println(s.startsWith("a"));
        System.out.println(s.equalsIgnoreCase(new String("abc")));// true
        System.out.println();
        s = new String("");//
        System.out.println(s.intern() == "");// �Ƚϵ����ַ���е��ַ� true
        System.out.println(s.isEmpty());//length==0
        System.out.println(s == new String("abc"));// false
        System.out.println(s.subSequence(1, 2));
        s.substring(1, 2);
        // s=null;
        // System.out.println(s.isEmpty());
        // Exception in thread "main" java.lang.NullPointerException
        // at string.StringDemo.main(StringDemo.java:13)

        StringUtils.reverse("aaa");
    }

}
