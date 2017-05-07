package ArtConcurrentBook.chapter03;

/**
 * 名称：王坤造
 * 时间：2017/5/7.
 * 名称：
 * 备注：
 */
class SynchronizedExample {
	int     a    = 0;
	boolean flag = false;

	public synchronized void writer() { //获取锁
		a = 1;
		flag = true;
	} //释放锁

	public synchronized void reader() { //获取锁
		if (flag) {
			int i = a;
			//……
		} //释放锁
	}
}
