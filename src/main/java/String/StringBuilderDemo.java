package String;

public class StringBuilderDemo {

	public static void main(String[] args) {
		StringBuffer sb1 = new StringBuffer();
		sb1.append("aaaadfsfsaaaadfsfsaaaadfsfs");
		sb1.setLength(100);
		System.out.println(sb1.toString()+sb1.length()+";"+sb1.capacity());
		sb1.trimToSize();
		System.out.println(sb1.toString()+sb1.length()+";"+sb1.capacity());
		StringBuilder sb2 = new StringBuilder();
		sb2.append("aaa");
		String str = "abc";
		System.out.println(reverse2(str));

	}

	public static String reverse2(String s) {
		int length = s.length();
		String reverse = "";
		for (int i = 0; i < length; i++)
			reverse = s.charAt(i) + reverse;
		return reverse;
	}
}
