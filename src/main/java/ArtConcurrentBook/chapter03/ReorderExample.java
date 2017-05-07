package ArtConcurrentBook.chapter03;

/**
 * 名称：王坤造
 * 时间：2017/5/7.
 * 名称：
 * 备注：
 */
public class ReorderExample {
	int a = 0;
	boolean flag = false;

	public void writer() {
		a = 1; //1
		flag = true; //2
	}

	public void reader() {
		if (flag) { //3
			int i = a * a; //4
			//s……
		}
	}
}
