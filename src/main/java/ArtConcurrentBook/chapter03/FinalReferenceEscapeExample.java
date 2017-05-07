package ArtConcurrentBook.chapter03;

/**
 * 名称：王坤造
 * 时间：2017/5/7.
 * 名称：
 * 备注：
 */
public class FinalReferenceEscapeExample {

	final int                          i;
	static FinalReferenceEscapeExample obj;

	public FinalReferenceEscapeExample() {
		i = 1; //1写final域
		obj = this; //2 this引用在此“逸出”
	}

	public static void writer() {
		new FinalReferenceEscapeExample();
	}

	public static void reader() {
		if (obj != null) { //3
			int temp = obj.i; //4
		}
	}
}
