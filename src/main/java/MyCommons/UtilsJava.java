package MyCommons;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by Administrator on 2016/10/8.
 */
public class UtilsJava {
	public static class MyPrint {

		/**
		 * 输出数组
		 */
		public static void print(boolean[] arr) {
			System.out.println(Arrays.toString(arr));
		}

		public static void print(byte[] arr) {
			System.out.println(Arrays.toString(arr));
		}

		public static void print(char[] arr) {
			System.out.println(Arrays.toString(arr));
		}

		public static void print(double[] arr) {
			System.out.println(Arrays.toString(arr));
		}

		public static void print(float[] arr) {
			System.out.println(Arrays.toString(arr));
		}

		public static void print(int[] arr) {
			System.out.println(Arrays.toString(arr));
		}

		public static void print(long[] arr) {
			System.out.println(Arrays.toString(arr));
		}

		public static void print(short[] arr) {
			System.out.println(Arrays.toString(arr));
		}

		public static void print(Object[] arr) {
			System.out.println(Arrays.toString(arr));
		}

		/**
		 * 输出实现Collection接口的集合
		 */
		public static void print(Collection<? extends Object> coll) {
			System.out.println(Arrays.toString(coll.toArray()));
		}
	}

	public static class MyString {
		/**
		 * @Author 王坤造
		 * @Date 2016/10/19 16:33
		 * 名称：反转字符串
		 * 备注：测试100k次,22ms
		 */
		public static String reverseString(String s) {
			if (StringUtils.isNotEmpty(s)) {
				s = reverseString(s, 0, s.length() - 1);
			}
			return s;
		}

		/**
		 * @author: 王坤造
		 * @date: 2017/4/14 0:57
		 * @comment: 反转字符串[from, to]
		 * @return:
		 * @notes: 测试100k次, 22ms
		 * reverseString("abcdef",0,1)=>"bacdef"
		 */
		public static String reverseString(String s, int from, int to) {
			if (s != null) {
				char[] arr = s.toCharArray();
				while (from < to) {
					char c = arr[from];
					arr[from++] = arr[to];
					arr[to--] = c;
				}
				s = new String(arr);
			}
			return s;
		}

		/**
		 * @Author 王坤造
		 * @Date 2016/10/19 16:33
		 * 名称：反转字符串[from,to]
		 * 备注：测试100k次,80ms
		 */
		private static String reverseString2(String s, int from, int to) {
			if (s == null) {
				return s;
			} else {
				StringBuilder sb = new StringBuilder(s);
				while (from < to) {
					char c = sb.charAt(from);
					sb.replace(from, ++from, sb.charAt(to) + "");
					sb.replace(to, to + 1, c + "");
					to--;
				}
				return sb.toString();
			}
		}

		/**
		 * @Author 王坤造
		 * @Date 2016/10/19 16:33
		 * 名称：反转字符串
		 * 备注：
		 */
		public static String reverse(String str) {
			return str == null ? null : (new StringBuilder(str)).reverse().toString();
		}
	}

	public static class CodeUtils {
		/**
		 * @author: 王坤造
		 * @date: 2017/4/25 17:50
		 * @comment: unicode转化中文
		 * @return:
		 * @notes:
		 */
		public static String unicode2String(final String str) {
			final int len = str.length();
			final StringBuffer outBuffer = new StringBuffer(len);
			char aChar;
			for (int x = 0; x < len; ) {
				aChar = str.charAt(x++);
				if (aChar == '\\') {
					aChar = str.charAt(x++);
					if (aChar == 'u') {
						int value = 0;
						for (int i = 0; i < 4; i++) {
							aChar = str.charAt(x++);
							switch (aChar) {
								case '0':
								case '1':
								case '2':
								case '3':
								case '4':
								case '5':
								case '6':
								case '7':
								case '8':
								case '9':
									value = (value << 4) + aChar - '0';
									break;
								case 'a':
								case 'b':
								case 'c':
								case 'd':
								case 'e':
								case 'f':
									value = (value << 4) + 10 + aChar - 'a';
									break;
								case 'A':
								case 'B':
								case 'C':
								case 'D':
								case 'E':
								case 'F':
									value = (value << 4) + 10 + aChar - 'A';
									break;
								default:
									throw new IllegalArgumentException(
											"Malformed   \\uxxxx   encoding.");
							}
						}
						outBuffer.append((char) value);
					} else {
						if (aChar == 't') {
							aChar = '\t';
						} else if (aChar == 'r') {
							aChar = '\r';
						} else if (aChar == 'n') {
							aChar = '\n';
						} else if (aChar == 'f') {
							aChar = '\f';
						}
						outBuffer.append(aChar);
					}
				} else {
					outBuffer.append(aChar);
				}
			}
			return outBuffer.toString();
		}
	}

	/**
	 * 作者: 王坤造
	 * 日期: 2016/10/23 15:50
	 * 名称：项目resources的目录
	 * 备注：
	 */
	public static final String resourcesPath = "D:/MyDocument/Study/Java/IdeaProjects/SparkProject2_0/src/main/resources/";
}
